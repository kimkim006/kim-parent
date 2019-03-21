package com.kim.quality.common;

import com.kim.common.constant.RedisConstant;

public class QualityRedisConstant extends RedisConstant {
	
	/** 抽检规则目录key, 租户id */
	private static final String RULE_DIR = "RULE_DIR:%s";
	/** 任务缓存key, 租户id,任务id */
	private static final String MAIN_TASK_KEY_FORMAT = "TASK:%s:%s";
	/** 任务关联的录音缓存key, 租户id,任务id */
	private static final String MAIN_TASK_TAPE_KEY_FORMAT = "TASK_TAPE:%s:%s";
	/** 任务摘要缓存key, 租户id,任务id */
	private static final String MAIN_TASK_DIGEST_KEY_FORMAT = "TASK_DIGEST:%s:%s";
	/**评分缓存key,租户id,评分id**/
	private static final String EVAL_KEY_FORMAT = "EVAL:%s:%s";
	/**审核缓存key, 租户id,评分id**/
	private static final String APPROVAL_KEY_FORMAT = "APPROVAL:%s:%s";
	/**申诉记录缓存key, 租户id,申诉id**/
	private static final String APPEAL_KEY_FORMAT = "APPEAL:%s:%s";
	/**调整分数记录缓存key, 租户id,行动记录id**/
	private static final String ADJUST_SCORE_KEY_FORMAT = "ADJUST_SCORE:%s:%s";
	
	/**抽检功能redis同步锁的原始key, 抽检规则id或抽检日期 **/
	private static final String SAMPLE_ORIG_REDIS_LOCK_KEY_FORMAT = "sample:%s";
	
	/**
	 * @return 抽检功能redis同步锁的原始key
	 */
	public static String getSampleOrigLockKey(String ruleIdOrDate){
		return String.format(SAMPLE_ORIG_REDIS_LOCK_KEY_FORMAT, ruleIdOrDate);
	}

	/**
	 * @return 抽检规则目录key
	 */
	public static String getRuleDirKey(String tenantId){
		return String.format(RULE_DIR, tenantId);
	}


	/**
	 * 任务审核缓存key
	 * @param tenantId 租户id
	 * @param id 审核记录id
	 * @return
	 */
	public static String getApprovalKey(String tenantId, String id){
		return String.format(APPROVAL_KEY_FORMAT, tenantId, id);
	}
	
	/**
	 * 任务申诉缓存key
	 * @param tenantId 租户id
	 * @param id 申诉记录id
	 * @return
	 */
	public static String getAppealKey(String tenantId, String id){
		return String.format(APPEAL_KEY_FORMAT, tenantId, id);
	}
	
	/**
	 * 调整分数记录缓存key
	 * @param tenantId 租户id
	 * @param id 申诉记录id
	 * @return
	 */
	public static String getAdjustScorekey(String tenantId, String id){
		return String.format(ADJUST_SCORE_KEY_FORMAT, tenantId, id);
	}
	
	/**
	 * 任务缓存key
	 * @param tenantId 租户id
	 * @param taskId 任务id
	 * @return
	 */
	public static String getMainTaskKey(String tenantId, String taskId){
		return String.format(MAIN_TASK_KEY_FORMAT, tenantId, taskId);
	}
	
	/**
	 * 任务关联的录音缓存key
	 * @param tenantId 租户id
	 * @param taskId 任务id
	 * @return
	 */
	public static String getMainTaskTapeKey(String tenantId, String taskId){
		return String.format(MAIN_TASK_TAPE_KEY_FORMAT, tenantId, taskId);
	}
	
	/**
	 * 任务摘要缓存key
	 * @param tenantId 租户id
	 * @param taskId 任务id
	 * @return
	 */
	public static String getMainTaskDigestKey(String tenantId, String taskId){
		return String.format(MAIN_TASK_DIGEST_KEY_FORMAT, tenantId, taskId);
	}

	public static String getEvalKey(String tenantId, String evalId) {
		return String.format(EVAL_KEY_FORMAT, tenantId, evalId);
	}
}
