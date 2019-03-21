package com.kim.quality.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.base.service.BaseCacheUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.quality.common.CommonConstant;

public class MainTaskUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(MainTaskUtil.class);
	
	/**
	 * 根据计算得分计算实际的得分 
	 * @param calScore
	 * @param tenantId
	 * @return
	 */
	public static Double getActualScore(Double calScore, String tenantId){
		Double maxScore = MainTaskUtil.getMaxScore(tenantId);
		Double minScore = MainTaskUtil.getMinScore(tenantId);
		if (NumberUtil.compare(calScore, maxScore) > 0) {//计算得分大于最大取最大
			return maxScore;
		}else if (NumberUtil.compare(calScore, minScore) < 0) {//计算得分小于最小取最小
			return minScore;
		}else{
			return calScore;
		}
	}
	
	
	public static Double getMaxScore(String tenantId) {
		String str = BaseCacheUtil.getParam(CommonConstant.DICT_MAX_SCORE_KEY, tenantId);
		if(StringUtil.isBlank(str)){
			logger.error("获取分数允许最大值时检查到最大分数未配置, 将使用默认的最大分数:{}, dict key:{}", 
					CommonConstant.DICT_MAX_SCORE_DEFAULT, CommonConstant.DICT_MAX_SCORE_KEY);
			return CommonConstant.DICT_MAX_SCORE_DEFAULT;
		}
		if(!NumberUtil.isNumber(str)){
			logger.error("获取分数允许最大值时检查到最大分数配置不是数字, 将使用默认的最大分数:{}, dict key:{}", 
					CommonConstant.DICT_MAX_SCORE_DEFAULT, CommonConstant.DICT_MAX_SCORE_KEY);
			return CommonConstant.DICT_MAX_SCORE_DEFAULT;
		}
		
		return Double.valueOf(str);
	}
	
	public static Double getMinScore(String tenantId) {
		String str = BaseCacheUtil.getParam(CommonConstant.DICT_MIN_SCORE_KEY, tenantId);
		if(StringUtil.isBlank(str)){
			logger.error("获取分数允许最小值时检查到最小分数未配置, 将使用默认的最大分数:{}, dict key:{}", 
					CommonConstant.DICT_MIN_SCORE_DEFAULT, CommonConstant.DICT_MIN_SCORE_KEY);
			return CommonConstant.DICT_MIN_SCORE_DEFAULT;
		}
		if(!NumberUtil.isNumber(str)){
			logger.error("获取分数允许最小值时检查到最小分数配置不是数字, 将使用默认的最大分数:{}, dict key:{}", 
					CommonConstant.DICT_MIN_SCORE_DEFAULT, CommonConstant.DICT_MIN_SCORE_KEY);
			return CommonConstant.DICT_MIN_SCORE_DEFAULT;
		}
		
		return Double.valueOf(str);
	}

}
