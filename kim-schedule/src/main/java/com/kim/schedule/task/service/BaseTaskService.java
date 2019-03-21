package com.kim.schedule.task.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.kim.common.base.BaseService;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.common.validation.CommonReg;
import com.kim.schedule.common.CommonConstant;
import com.kim.schedule.task.dao.UserDao;

public class BaseTaskService extends BaseService {
	
	@Autowired
	protected UserDao userDao;
	
	protected String dealDate(Map<String, String> param){
		String date = null;
		if(CollectionUtil.isNotEmpty(param)){
			String dt = param.get("date");
			if(StringUtil.isNotBlank(dt) && dt.matches(CommonReg.DATE_DATETIME_STR_REG)){
				try {
					//将时间字符串转成时间，再转回来，为了获取标准的年月日格式的时间
					Date d = DateUtil.parseDate(dt, DateUtil.PATTERN_YYYY_MM_DD, DateUtil.DEFAULT_PATTERN);
					date = DateUtil.formatDate(d, DateUtil.PATTERN_YYYY_MM_DD);
				} catch (ParseException e) {
					logger.error("日期格式错误，将使用默认的时间!");
				}
			}
			param.put("date", date);
		}
		return date;
	}
	
	protected int execute(Map<String, String> param, TaskRunner taskRunner){
		String tenantId = TokenUtil.getTenantId();
		if(StringUtil.isNotBlank(tenantId)){
			param.put("operName", TokenUtil.getCurrentName());
			param.put("operUser", TokenUtil.getUsername());
			return taskRunner.run(param, tenantId);
		}else{
			param.put("operName", CommonConstant.TIMER_OPER_NAME);
			param.put("operUser", CommonConstant.TIMER_OPER_USER);
			List<String> tenantIds = userDao.listAllTenant();
			int n = 0;
			if(CollectionUtil.isNotEmpty(tenantIds)){
				for (String tenantIdTmp : tenantIds) {
					n += exeRunner(param, tenantIdTmp, taskRunner);
				}
			}
			return n;
		}
	}
	
	private int exeRunner(Map<String, String> param, String tenantId, TaskRunner taskRunner){
		try {
			return taskRunner.run(param, tenantId);
		} catch (Exception e) {
			logger.error("任务执行失败, tenantId:"+tenantId, e);
			return 0;
		}
	}
	
//	protected class SyncInvoke{
//		@Transactional(readOnly=false)
//		public int run(TapeSyncEntity sync) {
//			return syncTapeData(sync);
//		}
//	}

}
