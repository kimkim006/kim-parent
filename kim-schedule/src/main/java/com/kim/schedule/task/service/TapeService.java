package com.kim.schedule.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseTable;
import com.kim.common.constant.CommonConstants;
import com.kim.common.context.ContextHolder;
import com.kim.common.exception.BusinessException;
import com.kim.common.util.BatchUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.business.entity.TapeEntity;
import com.kim.quality.business.entity.TapeSyncEntity;
import com.kim.quality.business.vo.TapeVo;
import com.kim.schedule.common.CommonConstant;
import com.kim.schedule.task.dao.TapeDao;
import com.kim.schedule.task.entity.UserAgentMapping;

/**
 * 录音池表服务实现类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
@Service
public class TapeService extends BaseTaskService {
	
	@Autowired
	private TapeDao tapeDao;
	@Autowired
	private CiscoTapeService ciscoTapeService;
	
	private static final String TAPE_ADDRESS_FORMAT = "http://%s\\%s%s";
	
	@Transactional(readOnly=false)
	public int syncSingle(Map<String, String> param, String tenantId, int type){
		
		String typeName = type == TapeEntity.TYPE_IN ? 
				TapeEntity.TYPE_NAME_IN:TapeEntity.TYPE_NAME_OUT;
		TapeSyncEntity sync = insertSync(param, tenantId, type);
		int n = 0;
		try {
			n = type== TapeEntity.TYPE_IN ? syncCallInTapeData(sync)
					:syncCallOutTapeData(sync);
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			sync.setStatus(TapeSyncEntity.STATUS_FAIL);
			sync.setRemark(e.getMessage());
		} catch (Exception e) {
			logger.error("同步"+typeName+"录音数据异常, traceId:"+ContextHolder.getTraceId(), e);
			sync.setStatus(TapeSyncEntity.STATUS_INTERRUPT);
			sync.setRemark("同步录音数据异常，" + e.getMessage() + ", traceId:"+ContextHolder.getTraceId());
		} finally{
			try {
				sync.setEndTime(DateUtil.getCurrentTime());
				tapeDao.updateSync(sync);
			} catch (Exception e) {
				logger.error("修改"+typeName+"录音同步记录时出现异常", e);
				logger.error("sync object:{}", sync);
			}
		}
		return n;
	}
	
	private TapeSyncEntity insertSync(Map<String, String> param, String tenantId, int type){
		
		TapeSyncEntity entity = new TapeSyncEntity();
		entity.setTimeDate(param.get("date"));
		entity.setType(type);
		entity.setStartTime(DateUtil.getCurrentTime());
		entity.setStatus(TapeSyncEntity.STATUS_GOING);
		entity.setOperTime(DateUtil.getCurrentTime());
		entity.setTenantId(tenantId);
		entity.setOperName(param.get("operName"));
		entity.setOperUser(param.get("operUser"));
		tapeDao.insertSync(entity);
		return entity;
	}
	
	/**
	 * 同步获取呼入录音记录
	 * @param sync
	 * @return
	 */
	private int syncCallInTapeData(TapeSyncEntity sync) {
		
		JSONObject dict = BaseCacheUtil.getDict(CommonConstants.DICT_BUSINESS_KEY, sync.getTenantId());
		if(CollectionUtil.isEmpty(dict)){
			logger.error("未配置业务线字典信息，无法同步呼入录音记录, 请先配置业务线信息! key:{}, tenantId:{}", CommonConstants.DICT_BUSINESS_KEY, sync.getTenantId());
			throw new BusinessException("未配置业务线字典信息，无法同步呼入录音记录");
		}
		
		List<TapeEntity> list = ciscoTapeService.listCiscoCallInRecord(sync.getTimeDate(), new ArrayList<>(dict.keySet()));
		if(CollectionUtil.isEmpty(list)){
			sync.setStatus(TapeSyncEntity.STATUS_SUCCESS);
			sync.setRemark("未获取到呼入录音数据");
			logger.info("录音转储从思科库未获取到呼入录音数据, date:{}, businessCode:{}, tenantId:{}", sync.getTimeDate(), dict.keySet(), sync.getTenantId());
			return 0;
		}
		
		logger.info("获取到[呼入]录音记录:{}条, 开始进行数据处理..., tenantId:{}", list.size(), sync.getTenantId());
		List<TapeEntity> addList = deal(list, sync, getAgentMap(sync));
		
		deleteByServiceNo(sync.getTimeDate(), new ArrayList<>(dict.keySet()), sync.getTenantId());
		saveTape(sync, list, addList, TapeEntity.TYPE_NAME_IN);
		return 1;
	}
	
	private int syncCallOutTapeData(TapeSyncEntity sync) {
		
		Map<String, UserAgentMapping> agentMap = getAgentMap(sync);
		List<String> agentNoList = new ArrayList<>(agentMap.keySet());
		
		List<TapeEntity> list = ciscoTapeService.listCiscoCallOutRecord(sync.getTimeDate(), agentNoList);
		if(CollectionUtil.isEmpty(list)){
			sync.setStatus(TapeSyncEntity.STATUS_SUCCESS);
			sync.setRemark("未获取到呼出录音数据");
			logger.info("录音转储从思科库未获取到呼出录音数据, date:{}, agentNoList:{}, tenantId:{}", sync.getTimeDate(), agentNoList, sync.getTenantId());
			return 0;
		}
		
		logger.info("获取到[呼出]录音记录:{}条, 开始进行数据处理..., tenantId:{}", list.size(), sync.getTenantId());
		List<TapeEntity> addList = deal(list, sync, agentMap);
		
		deleteByAgentNo(sync.getTimeDate(), agentNoList, sync.getTenantId());
		saveTape(sync, list, addList, TapeEntity.TYPE_NAME_OUT);
		return 1;
	}
	
	private Map<String, UserAgentMapping> getAgentMap(TapeSyncEntity sync){
		List<UserAgentMapping> agentList = userDao.listBusiGroupUser(sync.getTenantId());
		return CollectionUtil.getMapByProperty(agentList, "agentNo");
	}

	/**
	 * 保存录音
	 * @param sync
	 * @param list
	 * @param addList
	 */
	private void saveTape(TapeSyncEntity sync, List<TapeEntity> list, List<TapeEntity> addList, String typeName) {
	
		logger.info("获取到[{}]录音记录:{}条, 有效记录:{}, 无效记录:{}, 开始保存..., tenantId:{}", typeName,
				list.size(), addList.size(), list.size() - addList.size(), sync.getTenantId());
		
		BatchUtil.batchInsert(tapeDao, addList);
		sync.setTotalCount(addList.size());
		sync.setStatus(TapeSyncEntity.STATUS_SUCCESS);
		sync.setRemark(String.format("同步成功, 获取到[%s]录音记录:%s条, 有效记录:%s, 无效记录:%s", 
				typeName, list.size(), addList.size(), list.size() - addList.size()));
		
		logger.info("保存完成! [{}]录音共计:{}条, 有效记录:{}, 无效记录:{}, tenantId:{}", typeName, 
				list.size(), addList.size(), list.size() - addList.size(), sync.getTenantId());
	}

	private List<TapeEntity> deal(List<TapeEntity> list, TapeSyncEntity sync, Map<String, UserAgentMapping> agentMap) {
		
		List<TapeEntity> addList = new ArrayList<>(list.size());
		JSONObject tapeFilterDealDict = BaseCacheUtil.getDict(CommonConstant.TAPE_FILTER_DEAL_DICT_CODE, null);
		UserAgentMapping mapping;
		for (TapeEntity entity : list) {
			if(StringUtil.isBlank(entity.getAgentNo())){
				logger.error("该录音记录没有坐席思科工号, tapeSerialNumber:{}, tenantId:{}", 
						entity.getSerialNumber(), sync.getTenantId());
				continue;
			}
			mapping = agentMap.get(entity.getAgentNo());
			if(mapping == null || StringUtil.isBlank(mapping.getUsername())){
				logger.error("该思科工号未配置对应的坐席工号，agentNo:{}, tenantId:{}", 
						entity.getAgentNo(), sync.getTenantId());
				continue;
			}
			entity.createId();
			entity.setAtvFlag(BaseTable.ATV_FLAG_YES);
			entity.copyBaseField(sync);
			entity.setSyncId(sync.getId());
			entity.setPlatform(CommonConstants.PLATFORM_CISCO);
			entity.setAgentId(mapping.getUsername());
			calcAddress(entity, tapeFilterDealDict);
			addList.add(entity);
			if(StringUtil.isBlank(mapping.getGroupCode())){
				logger.error("该坐席工号未配置对应的业务小组, 根据小组维度抽检将会受此影响，username:{}, tenantId:{}", 
						mapping.getUsername(), sync.getTenantId());
				continue;
			}
			entity.setBusiGroupCode(mapping.getGroupCode());
		}
		return addList;
	}

	/**
	 * 处理录音地址链接，去掉D:,D
	 * @param entity
	 * @param tapeAddressDict 
	 */
	private void calcAddress(TapeEntity entity, JSONObject tapeFilterDealDict) {
		
		if(StringUtil.isNotBlank(entity.getLocalpath()) && StringUtil.isNotBlank(entity.getRecserverip())
				&& tapeFilterDealDict.containsKey(entity.getRecserverip().trim())){
			//'http://' + recServerIp + '\' + REPLACE(localPath, ':', '') + localFile as address,
			String localpath = entity.getLocalpath();
			entity.setAddress(String.format(TAPE_ADDRESS_FORMAT, entity.getRecserverip().trim(),
					localpath.substring(localpath.indexOf(':')+1), entity.getLocalfile()));
		}
	}

	private int deleteByServiceNo(String date, List<String> serviceNos, String tenantId) {

		TapeVo vo = new TapeVo().tenantId(tenantId);
		vo.setServiceNos(serviceNos);
		vo.setType(TapeEntity.TYPE_IN);
		vo.setStartTime(date + " 00:00:00");
		vo.setEndTime(date + " 23:59:59");
		vo.setPlatform(CommonConstants.PLATFORM_CISCO);
		return tapeDao.deleteLogic(vo);
	}
	
	private int deleteByAgentNo(String date, List<String> agentNos, String tenantId) {
		
		TapeVo vo = new TapeVo().tenantId(tenantId);
		vo.setAgentNos(agentNos);
		vo.setType(TapeEntity.TYPE_OUT);
		vo.setStartTime(date + " 00:00:00");
		vo.setEndTime(date + " 23:59:59");
		vo.setPlatform(CommonConstants.PLATFORM_CISCO);
		return tapeDao.deleteLogic(vo);
	}

}
