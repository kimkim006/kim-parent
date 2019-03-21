package com.kim.quality.resolver.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.kim.quality.business.entity.SampleTapeEntity;
import com.kim.quality.business.entity.TapeEntity;
import com.kim.quality.business.vo.TapeVo;
import com.kim.quality.setting.entity.SampleRuleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.constant.CommonConstants;
import com.kim.common.exception.BusinessException;
import com.kim.common.util.CollectionUtil;
import com.kim.quality.business.entity.SampleEntity;
import com.kim.quality.business.service.TapeService;
import com.kim.quality.common.CommonConstant;
import com.kim.quality.resolver.RuleResolverConstant;
import com.kim.quality.resolver.entity.TapeTimeLongParam;
import com.kim.quality.setting.entity.SampleRuleDetailEntity;
import com.kim.quality.setting.service.DarkSettingService;

@Component
public class SystemSampleResolver extends AbstractRuleResolver {
	
	@Autowired
	private TapeService tapeService;
	@Autowired
	protected DarkSettingService darkSettingService;

	@Override
	public String getName() {
		return RuleResolverConstant.SYSTEM_SAMPLE_NAME;
	}

	@Override
	public String getCode() {
		return RuleResolverConstant.SYSTEM_SAMPLE;
	}

	@Override
	public List<SampleTapeEntity> extract(SampleEntity sample, SampleRuleEntity rule, SampleRuleDetailEntity ruleDetail) {
		
		TapeVo vo = new TapeVo().tenantId(sample.getTenantId());
		vo.setPlatform(CommonConstants.PLATFORM_CISCO);
		if(CommonConstant.BUSINESS_CALL_OUT.equals(sample.getBusinessCode())){
			vo.setType(TapeEntity.TYPE_OUT);
		}else{
			vo.setServiceNo(sample.getBusinessCode());
		}
		vo.setStartTime(sample.getExtractDate() + " 00:00:00");
		vo.setEndTime(sample.getExtractDate() + " 23:59:59");
		vo.setDarkList(darkSettingService.listDarkList(sample.getExtractDate(), sample.getTenantId()));
		
		List<String> agentList = tapeService.listOnline(vo);
		if(CollectionUtil.isEmpty(agentList)){
			logger.info("系统抽检, 未查询到当天在线坐席, platform:{}, date:{}, busiCode:{}",
					vo.getPlatform(),sample.getExtractDate(), sample.getBusinessCode());
			return new ArrayList<>();
		}
		
		if(rule.getPersonAvg() == null || rule.getPersonAvg() < 1){
			logger.error("系统抽检规则未配置人均抽取数量，ruleId:{},ruleName:{},busiCode:{}", rule.getId(), rule.getName(), rule.getBusinessCode());
			throw new BusinessException("系统抽检规则未配置人均抽取数量，请检查抽检规则!");
		}
		
		List<SampleTapeEntity> totalList = new ArrayList<>();
		Map<String, Integer> totalAgentMap = new HashMap<>(agentList.size());
		List<SampleTapeEntity> remainingList = new LinkedList<>();
		
		int limit = rule.getPersonAvg();
		List<SampleTapeEntity> tmpList ;
		Iterator<SampleTapeEntity> it = null;
		for (SampleRuleDetailEntity detail : rule.getRuleDetail()) {
			//抽取录音
			tmpList = extractByDetail(sample, rule, vo, detail, rule.getPersonAvg());
			if(CollectionUtil.isEmpty(tmpList)){
				continue;
			}
			//每人limit个，过滤多余的
			filterTape(tmpList, new HashMap<>(), limit);
			
			//计算合并
			int num = calcNum(agentList.size(), limit, detail.getPercent());
			if(tmpList.size() <= num){
				totalList.addAll(filterTape(tmpList, totalAgentMap, limit));
				int d = num - tmpList.size();
				if(d > 0){//如果不够则从其他条件剩下的里面补充
					it = remainingList.iterator();
					while (it.hasNext() && d > 0) {
						if(addTape(totalList, totalAgentMap, (limit -1), it.next())){
							it.remove();
							d--;
						}
					}
				}
			}else{
				int i = 0;
				it = tmpList.iterator();
				while (it.hasNext()) {
					SampleTapeEntity tape = it.next();
					if(i<num){
						if(!addTape(totalList, totalAgentMap, (limit -1), tape)){
							it.remove();
						}
					}else{//多余的放在剩余的列表中，用于补充不够的条件
						remainingList.add(tape);
					}
					i++;
				}
			}
		}
		//如果不够则从其他条件剩下的里面补充
		int planTotal = rule.getPersonAvg() * agentList.size();
		if(totalList.size() < planTotal){
			int d = planTotal - totalList.size();
			it = remainingList.iterator();
			while (it.hasNext() && d > 0) {
				if(addTape(totalList, totalAgentMap, (limit -1), it.next())){
					d--;
				}
			}
		}
		return totalList;
	}

	private boolean addTape(List<SampleTapeEntity> totalList, Map<String, Integer> totalAgentMap,
			int limit, SampleTapeEntity tape) {
		Integer tmpAgentCount = totalAgentMap.get(tape.getAgentId());
		if(tmpAgentCount == null){
			totalAgentMap.put(tape.getAgentId(), 1);
			totalList.add(tape);
			return true;
		}else if(tmpAgentCount > limit){
			return false;
		}else{
			totalAgentMap.put(tape.getAgentId(), ++tmpAgentCount);
			totalList.add(tape);
			return true;
		}
	}
	
	private List<SampleTapeEntity> filterTape(List<SampleTapeEntity> tmpList, Map<String, Integer> tmpAgentMap, int limit){
		Iterator<SampleTapeEntity> it = tmpList.iterator();
		limit = limit -1;
		Integer tmpAgentCount = null;
		while (it.hasNext()) {
			SampleTapeEntity tapeEntity = it.next();
			tmpAgentCount = tmpAgentMap.get(tapeEntity.getAgentId());
			if(tmpAgentCount == null){
				tmpAgentMap.put(tapeEntity.getAgentId(), 1);
			}else if(tmpAgentCount > limit){
				it.remove();
			}else{
				tmpAgentMap.put(tapeEntity.getAgentId(), ++tmpAgentCount);
			}
		}
		return tmpList;
	}
	
	private int calcNum(int total, int personAvg, int percent){
		return total * personAvg * percent / 100;
	}

	private List<SampleTapeEntity> extractByDetail(SampleEntity sample, SampleRuleEntity rule, TapeVo vo,
			SampleRuleDetailEntity detail, int limit) {
		TapeTimeLongParam param = parseParam(rule, detail, TapeTimeLongParam.class);
		vo.setRecordTimeStart(param.getStart());
		vo.setRecordTimeEnd(param.getEnd());
		vo.setLimit(limit);
		List<SampleTapeEntity> list = tapeService.listByAgent(vo);
		if(CollectionUtil.isEmpty(list)){
			logger.debug("系统抽检规则未查询到录音数据, date:{}, busiCode:{}, param:{}",
					sample.getExtractDate(), sample.getBusinessCode(), JSONObject.toJSONString(param));
			return new ArrayList<>();
		}
		for (SampleTapeEntity entity : list) {
			entity.setResolver(detail.getResolver());
			entity.setBatchNo(sample.getBatchNo());
			if(CommonConstant.BUSINESS_CALL_OUT.equals(sample.getBusinessCode())){
				entity.setServiceNo(sample.getBusinessCode());
			}
		}
		return list;
	}
	
}
