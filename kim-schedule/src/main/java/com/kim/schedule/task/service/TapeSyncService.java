package com.kim.schedule.task.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.business.entity.TapeEntity;
import com.kim.schedule.job.annotation.TaskTarget;

@TaskTarget
@Service
public class TapeSyncService extends BaseTaskService {
	
	@Autowired
	private TapeService tapeService;
	
	@TaskTarget
	public int syncCallIn(Map<String, String> param){
		
		String date = dealDate(param);
		if (StringUtil.isBlank(date)){
			//昨天的日期
			date = DateUtil.getDurationDateStr(-1);
		}
		param.put("date", date);
		return execute(param, (tmpParam, tenantId)->
			tapeService.syncSingle(tmpParam, tenantId, TapeEntity.TYPE_IN));
	}
	
	@TaskTarget
	public int syncCallOut(Map<String, String> param){
		
		String date = dealDate(param);
		if (StringUtil.isBlank(date)){
			//昨天的日期
			date = DateUtil.getDurationDateStr(-1);
		}
		param.put("date", date);
		return execute(param, (tmpParam, tenantId)->
			tapeService.syncSingle(tmpParam, tenantId, TapeEntity.TYPE_OUT));
	}

}
