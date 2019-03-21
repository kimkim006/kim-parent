package com.kim.quality.resolver.impl;

import java.util.ArrayList;
import java.util.List;

import com.kim.quality.business.entity.SampleTapeEntity;
import com.kim.quality.business.entity.TapeEntity;
import com.kim.quality.business.service.TapeService;
import com.kim.quality.business.vo.TapeVo;
import com.kim.quality.common.CommonConstant;
import com.kim.quality.resolver.entity.ResolverParam;
import com.kim.quality.setting.entity.SampleRuleDetailEntity;
import com.kim.quality.setting.entity.SampleRuleEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.constant.CommonConstants;
import com.kim.quality.business.entity.SampleEntity;
import com.kim.quality.setting.service.DarkSettingService;

/**
 * 根据规则明细进行抽取数据
 * @author bo.liu01
 *
 */
public abstract class RuleDetailResolver<T extends ResolverParam> extends AbstractRuleResolver{
	
	@Autowired
	protected TapeService tapeService;
	@Autowired
	protected DarkSettingService darkSettingService;
	
	@Override
	public List<SampleTapeEntity> extract(SampleEntity sample, SampleRuleEntity rule, SampleRuleDetailEntity detail) {
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
		
		ResolverParam param = parseParam(rule, detail, getParamClass());
		setParam(vo, param, rule, detail);
		int count = tapeService.listCount(vo);
		if(count == 0){
			logger.debug("根据[{}]未查询到录音数据, date:{}, busiCode:{}, param:{}",
					getName(), sample.getExtractDate(), sample.getBusinessCode(), JSONObject.toJSONString(param));
			return new ArrayList<>();
		}
		List<SampleTapeEntity> list = tapeService.listForSample(vo.limit(count, detail.getPercent()));
		for (SampleTapeEntity entity : list) {
			entity.setResolver(detail.getResolver());
			entity.setBatchNo(sample.getBatchNo());
			if(CommonConstant.BUSINESS_CALL_OUT.equals(sample.getBusinessCode())){
				entity.setServiceNo(sample.getBusinessCode());
			}
		}
		return list;
	}

	protected abstract Class<T> getParamClass();

	protected abstract void setParam(TapeVo vo, ResolverParam param, SampleRuleEntity rule, SampleRuleDetailEntity detail);

}
