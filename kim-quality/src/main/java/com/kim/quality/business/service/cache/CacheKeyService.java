package com.kim.quality.business.service.cache;

import org.springframework.stereotype.Service;

import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.common.CommonConstant;

@Service
public class CacheKeyService extends BaseService{
	
	/**
	 * 任务在redis中的缓存时间，单位s
	 * @return
	 */
	public long getTaskExpire(String tenantId){
		String expire = BaseCacheUtil.getParam(CommonConstant.TASK_REDIS_EXPIRE_KEY, tenantId);
		if(StringUtil.isBlank(expire) || !NumberUtil.isNumber(expire)){
			return CommonConstant.TASK_REDIS_EXPIRE_DEFAULT;
		}
		return Long.parseLong(expire);
	}

}
