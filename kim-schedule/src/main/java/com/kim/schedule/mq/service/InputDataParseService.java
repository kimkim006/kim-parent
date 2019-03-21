package com.kim.schedule.mq.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.kim.schedule.common.CommonConstant;
import com.kim.schedule.mq.dao.IvrInfoDao;
import com.kim.schedule.mq.dao.TapeSummaryDao;
import com.kim.schedule.mq.entity.InputData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.util.BatchUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.business.entity.IvrInfoEntity;
import com.kim.quality.business.entity.SummaryEntity;
import com.kim.quality.business.entity.TapeSummaryEntity;
import com.kim.quality.business.vo.IvrInfoVo;
import com.kim.quality.business.vo.SummaryVo;
import com.kim.quality.business.vo.TapeSummaryVo;
import com.kim.schedule.mq.dao.SummaryDao;

@Component
public class InputDataParseService extends BaseService {
	
	@Autowired
	private SummaryDao summaryDao;
	@Autowired
	private TapeSummaryDao tapesSummaryDao;
	@Autowired
	private IvrInfoDao ivrInfoDao;
	
	/** 新编码的格式(来源-原始编码) */
	private static final String NEW_CODE_FORMAT = "%s_%s";
	/** 操作人 */
	private static final String MQ_OPER_USER = "interface";
	/** 操作姓名 */
	private static final String MQ_OPER_NAME = "接口推送";
	
//	@Transactional(readOnly=false)
//	public void parse(Message messageObj, String source){
//		//printRes(parse(new String(messageObj.getBody()), source));
//	}
	
	@Transactional(readOnly=false)
	public ResultResponse parse(String message, String methodName){
		logger.info("接收到消息:{}", message);
		InputData data;
		try {
			data = JSON.parseObject(message, InputData.class);
		} catch (Exception e) {
			logger.error("消息解析失败", e);
			return new MsgResponse().msg("消息格式错误, 解析失败!");
		}
		if(StringUtil.isBlank(data.getSource())){
			logger.error("未指定数据来源, 数据无效, 将被丢弃!");
			return new MsgResponse().msg("未指定数据来源");
		}
		
		if(StringUtil.isBlank(data.getData())){
			logger.error("数据为空!");
			return new MsgResponse().msg("数据为空");
		}
		
		String tenantId = getTenantId(data.getSource());
		if(StringUtil.isBlank(tenantId)){
			logger.error("未配置租户-小结来源字典项，无法完成小结数据存储!");
			return new MsgResponse().msg("未配置租户-小结来源字典项!");
		}
		
		switch (methodName) {
		case CommonConstant.MQ_INTERFACE_SUMMARY:
			return parseSummary(data, tenantId); 
		case CommonConstant.MQ_INTERFACE_TAPE_SUMMARY: 
			return parseTapeSummary(data, tenantId); 
		default:
			logger.error("暂不支持的方法:{}", methodName);
			return new MsgResponse().msg("暂不支持的解析方法");
		}
	}
	
	private ResultResponse parseTapeSummary(InputData data, String tenantId) {
		
		logger.info("开始解析录音小结关联数据...");
		List<TapeSummaryEntity> list;
		try {
			list = JSON.parseArray(data.getData(), TapeSummaryEntity.class);
		} catch (Exception e) {
			logger.error("解析录音小结关联数据失败", e);
			return new MsgResponse().msg("录音小结关联数据格式错误, 解析失败!");
		}

		if(CollectionUtil.isEmpty(list)){
			logger.error("未推送任何录音小结关联数据");
			return new MsgResponse().msg("未推送任何录音小结关联数据!");
		}
		
		String date = list.get(0).getDate();
		if(StringUtil.isNotBlank(date)){
			date = date.substring(0, 10);//截取年月日
		}else{
			//昨天日期
			date = DateUtil.formatDate(new Date(System.currentTimeMillis()-86400*1000));
		}
		
		//已存在的昨天的录音小结数据
		TapeSummaryVo v = new TapeSummaryVo().tenantId(tenantId);
		v.setSource(data.getSource());
		v.setStartTime(date + " 00:00:00");
		v.setEndTime(date + " 23:59:59");
		List<TapeSummaryEntity> originTSList = tapesSummaryDao.list(v);
		Map<String, TapeSummaryEntity> originTSMap = new HashMap<>();
		if(CollectionUtil.isNotEmpty(originTSList)){
			originTSMap = CollectionUtil.getMap(originTSList, TapeSummaryEntity::getSerialNumber);
		}
		
		//已存在的昨天的IVR数据
		IvrInfoVo ivrInfoVo = new IvrInfoVo().tenantId(tenantId);
		ivrInfoVo.setStartTime(date + " 00:00:00");
		ivrInfoVo.setEndTime(date + " 23:59:59");
		List<IvrInfoEntity> originIvrList = ivrInfoDao.list(ivrInfoVo );
		Map<String, IvrInfoEntity> originIvrMap = new HashMap<>();
		if(CollectionUtil.isNotEmpty(originTSList)){
			originIvrMap = CollectionUtil.getMap(originIvrList, IvrInfoEntity::getSerialNumber);
		}
		
		List<TapeSummaryEntity> updateTSList = new ArrayList<>();
		List<TapeSummaryEntity> insertTSList = new ArrayList<>();
		
		List<IvrInfoEntity> updateIvrList = new ArrayList<>();
		List<IvrInfoEntity> insertIvrList = new ArrayList<>();
		
		TapeSummaryEntity tmpTS = null;
		IvrInfoEntity tmpIvr = null;
		for (TapeSummaryEntity tapeSummary : list) {
			if(StringUtil.isBlank(tapeSummary.getSerialNumber())){
				logger.error("解析录音小结数据，录音流水号为空，丢掉数据:[{}]", tapeSummary);
				continue;
			}
			//录音小结数据处理
			tmpTS = originTSMap.get(tapeSummary.getSerialNumber());
			//判断是否需要新增还是修改
			if(tmpTS != null){
				convertTapeSummary(tapeSummary, tmpTS, data.getSource());
				updateTSList.add(tmpTS);
			}else{
				originTSMap.put(tapeSummary.getSerialNumber(), tapeSummary);
				convertTapeSummary(tapeSummary, tapeSummary, data.getSource());
				
				tapeSummary.setDate(date);
				tapeSummary.setSource(data.getSource());
				tapeSummary.setTenantId(tenantId);
				tapeSummary.setOperName(MQ_OPER_NAME);
				tapeSummary.setOperUser(MQ_OPER_USER);
				insertTSList.add(tapeSummary);
			}
			
			//IVR验证信息处理
			tmpIvr = originIvrMap.get(tapeSummary.getSerialNumber());
			//判断是否需要新增还是修改
			if(tmpIvr != null){
				tmpIvr.setCustIdCard(tapeSummary.getCustIdCard());
				tmpIvr.setIvrVerify(tapeSummary.getIvrVerify());
				updateIvrList.add(tmpIvr);
			}else{
				tmpIvr = new IvrInfoEntity().tenantId(tenantId);
				tmpIvr.setSerialNumber(tapeSummary.getSerialNumber());
				tmpIvr.setCustIdCard(tapeSummary.getCustIdCard());
				tmpIvr.setIvrVerify(tapeSummary.getIvrVerify());
				tmpIvr.setIvrVerifyCode(tapeSummary.getIvrVerifyCode());
				tmpIvr.setDate(date);
				tmpIvr.setOperName(MQ_OPER_NAME);
				tmpIvr.setOperUser(MQ_OPER_USER);
				insertIvrList.add(tmpIvr);
				originIvrMap.put(tapeSummary.getSerialNumber(), tmpIvr);
			}
			
		}
		logger.info("开始保存录音小结关联数据, 共接收到 {}条, 新增 {}条,  修改 {}条...", list.size(), insertTSList.size(), updateTSList.size());
		BatchUtil.batchInsert(tapesSummaryDao, insertTSList);
		for (TapeSummaryEntity summary : updateTSList) {
			tapesSummaryDao.update(summary);
		}
		logger.info("保存录音小结关联数据OK!");
		
		logger.info("开始保存录音IVR数据, 共接收到 {}条, 新增 {}条,  修改 {}条...", list.size(), insertIvrList.size(), updateIvrList.size());
		BatchUtil.batchInsert(ivrInfoDao, insertIvrList);
		for (IvrInfoEntity ivr : updateIvrList) {
			ivrInfoDao.update(ivr);
		}
		logger.info("保存录音IVR数据OK!");
		return new MsgResponse().rel(true).msg("保存录音IVR数据OK!");
	}
	
	private void convertTapeSummary(TapeSummaryEntity sourceTS, TapeSummaryEntity tagertTS, String source){
		
		tagertTS.setOriginTypeCode(sourceTS.getTypeCode());
		tagertTS.setOriginFirstCode(sourceTS.getFirstCode());
		tagertTS.setOriginSecondCode(sourceTS.getSecondCode());
		tagertTS.setOriginThirdCode(sourceTS.getThirdCode());
		tagertTS.setOriginForthCode(sourceTS.getForthCode());
		tagertTS.setTypeCode(String.format(NEW_CODE_FORMAT, source, sourceTS.getTypeCode()));
		tagertTS.setFirstCode(String.format(NEW_CODE_FORMAT, source, sourceTS.getFirstCode()));
		tagertTS.setSecondCode(String.format(NEW_CODE_FORMAT, source, sourceTS.getSecondCode()));
		tagertTS.setThirdCode(String.format(NEW_CODE_FORMAT, source, sourceTS.getThirdCode()));
		tagertTS.setForthCode(String.format(NEW_CODE_FORMAT, source, sourceTS.getForthCode()));
	}

	private ResultResponse parseSummary(InputData data, String tenantId) {
		
		logger.info("开始解析小结数据...");
		List<SummaryEntity> list;
		try {
			list = JSON.parseArray(data.getData(), SummaryEntity.class);
		} catch (Exception e) {
			logger.error("解析小结数据失败", e);
			return new MsgResponse().msg("小结数据格式错误, 解析失败!");
		}
		
		//已存在的小结数据
		SummaryVo v = new SummaryVo().tenantId(tenantId);
		v.setSource(data.getSource());
		List<SummaryEntity> originList = summaryDao.list(v);
		Map<String, SummaryEntity> originMap = new HashMap<>();
		if(CollectionUtil.isNotEmpty(originList)){
			originMap = CollectionUtil.getMap(originList, SummaryEntity::getOriginCode);
		}
		
		List<SummaryEntity> updateList = new ArrayList<>();
		List<SummaryEntity> insertList = new ArrayList<>();
		
		SummaryEntity tmp = null;
		for (SummaryEntity summary : list) {
			if(StringUtil.isBlank(summary.getCode())){
				logger.error("解析小结数据，小结编码为空，将丢弃数据:[{}]", summary);
				continue;
			}
			if(StringUtil.isBlank(summary.getName())){
				logger.warn("解析小结数据，小结名称为空，将使用编码作为名称:[{}]", summary);
				summary.setName(summary.getCode());
			}
			tmp = originMap.get(summary.getCode());
			//判断是否需要新增还是修改
			if(tmp != null){
				tmp.setName(summary.getName());
				tmp.setLevel(summary.getLevel());
				tmp.setOriginParentCode(summary.getParentCode());
				tmp.setParentCode(String.format(NEW_CODE_FORMAT, data.getSource(), summary.getParentCode()));
				updateList.add(tmp);
				continue;
			}
			originMap.put(summary.getCode(), summary);
			summary.setOriginCode(summary.getCode());
			summary.setOriginParentCode(summary.getParentCode());
			//新编码加一个来源前缀
			summary.setCode(String.format(NEW_CODE_FORMAT, data.getSource(), summary.getCode()));
			summary.setParentCode(String.format(NEW_CODE_FORMAT, data.getSource(), summary.getParentCode()));
			summary.setSource(data.getSource());
			summary.setTenantId(tenantId);
			summary.setOperName(MQ_OPER_NAME);
			summary.setOperUser(MQ_OPER_USER);
			insertList.add(summary);
		}
		
		//计算层级
		Collection<SummaryEntity> summaryList = originMap.values();
		for (SummaryEntity summary : summaryList) {
			calc(summary, originMap);
		}
		
		logger.info("开始保存小结数据, 共接收小结到 {}条, 新增 {}条,  修改 {}条...", list.size(), insertList.size(), updateList.size());
		BatchUtil.batchInsert(summaryDao, insertList);
		for (SummaryEntity summary : updateList) {
			summaryDao.update(summary);
		}
		logger.info("保存小结数据OK!");
		return new MsgResponse().rel(true).msg("保存小结数据OK!");
	}
	
	private Integer calc(SummaryEntity summary, Map<String, SummaryEntity> summaryMap){
		if(summary == null){
			return null;
		}
		if(summary.getLevel() != null){
			return summary.getLevel();
		}
		if(StringUtil.isBlank(summary.getOriginParentCode())){
			summary.setLevel(0);
		}else{
			Integer level = calc(summaryMap.get(summary.getOriginParentCode()), summaryMap);
			if(level == null){
				summary.setLevel(0);
			}else{
				summary.setLevel(level + 1);
			}
		}
		return summary.getLevel();
	}
	
	private String getTenantId(String source){
		List<Map<String, String>> tenantList = BaseCacheUtil.listAllTenant();
		Map<String, String> keyTenantMap = new HashMap<>();
		String tenantId = null;
		for (Map<String, String> map : tenantList) {
			tenantId = map.get("tenantId");
			JSONObject dict = BaseCacheUtil.getDict(CommonConstant.TAPE_RELATES_DICT_CODE, tenantId);
			for (Entry<String, Object> entry : dict.entrySet()) {
				if(keyTenantMap.containsKey(entry.getKey())){
					logger.error("数据字典配置冲突, 来源({})在[{}]和[{}]两个租户下同时存在, 请检查数据字典配置, 字典编码:{}, 系统将使用默认最后一个",
							entry.getKey(), keyTenantMap.get(entry.getKey()), tenantId);
				}
				keyTenantMap.put(entry.getKey(), tenantId);
			}
		}
		if(CollectionUtil.isEmpty(keyTenantMap)){
			return null;
		}
		return keyTenantMap.get(source);
	}

//	private void printRes(ResultResponse result){
//		logger.info("MQ数据处理结果:{}", JSON.toJSON(result));
//	}

}
