package com.kim.schedule.task.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.util.DateUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.schedule.common.CommonConstant;
import com.kim.schedule.job.annotation.TaskTarget;
import com.kim.schedule.task.dao.MainTaskDao;
import com.kim.schedule.task.feign.MainTaskFeignService;

@TaskTarget
@Service
public class MainTaskService extends BaseTaskService{
	
	@Autowired
	private MainTaskDao mainTaskDao;
	@Autowired
	private MainTaskFeignService mainTaskFeignService;
	
	private static final int DEFAULT_DAYS = 3;
	
	@TaskTarget
	public int updateTaskConfirm(Map<String, String> param) {
		
		dealDate(param);
		return execute(param, (tmpParam, tenantId)->updateTaskConfirmSingle(tmpParam, tenantId));
	}
	
	@Transactional(readOnly=false)
	public int updateTaskConfirmSingle(Map<String, String> param, String tenantId){
		String date = param.get("date");
		if (StringUtil.isBlank(date)){
			//三天前的日期
			String daysStr = BaseCacheUtil.getParam(CommonConstant.CONFIRM_DAYS, tenantId);
			int day = DEFAULT_DAYS;
			if(StringUtil.isNotBlank(daysStr) && NumberUtil.isNumber(daysStr)){
				day = Integer.parseInt(daysStr); 
			}
			date = DateUtil.formatDate(new Date(System.currentTimeMillis() - 86400*1000 * day));
		}
		param.put("date", date);
		param.put("tenantId", tenantId);
		return mainTaskDao.updateTaskConfirm(param);
	}
	
	@TaskTarget
	public int calcItemLast(Map<String, String> param){
		
		dealDate(param);
		return execute(param, (tmpParam, tenantId)->calc(tmpParam, tenantId));
	}
	
	private int calc(Map<String, String> param, String tenantId) {
		param.put("tenantId", tenantId);
		String itemType = param.get("itemType");
		if(StringUtil.isBlank(itemType) || StringUtil.equals("appeal", itemType)){
			logger.info("执行参数-申诉:{}", JSONObject.toJSONString(param));
			Object result = mainTaskFeignService.appealCalcLast(param);
			logger.info("执行结果-申诉:{}", JSONObject.toJSONString(result));
		}
		if(StringUtil.isBlank(itemType) || StringUtil.equals("approval", itemType)){
			logger.info("执行参数-审核:{}", JSONObject.toJSONString(param));
			Object result = mainTaskFeignService.approvalCalcLast(param);
			logger.info("执行结果-审核:{}", JSONObject.toJSONString(result));
		}
		if(StringUtil.isBlank(itemType) || StringUtil.equals("evaluation", itemType)){
			logger.info("执行参数-评分:{}", JSONObject.toJSONString(param));
			Object result = mainTaskFeignService.evaluationCalcLast(param);
			logger.info("执行结果-评分:{}", JSONObject.toJSONString(result));
		}
		return 1;
	}

}
