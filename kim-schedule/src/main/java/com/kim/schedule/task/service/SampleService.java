package com.kim.schedule.task.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.schedule.job.annotation.TaskTarget;
import com.kim.schedule.task.feign.SampleFeignService;

@TaskTarget
@Service
public class SampleService extends BaseTaskService{

	@Autowired
	private SampleFeignService sampleFeignService;
	
	@TaskTarget
	public int sample(Map<String, String> param) {
		
		String date = dealDate(param);
		if (StringUtil.isBlank(date)){
			//昨天的日期
			date = DateUtil.getDurationDateStr(-1);
		}
		param.put("extractDate", date);
		
		return execute(param, (tmpParam, tenantId)->executeSample(tmpParam, tenantId));
	}

	private int executeSample(Map<String, String> param, String tenantId) {
		param.put("tenantId", tenantId);
		logger.info("执行参数:{}", JSONObject.toJSONString(param));
		Object result = sampleFeignService.extractBySystem(param);
		logger.info("执行结果:{}", JSONObject.toJSONString(result));
		return 1;
	}
	
}
