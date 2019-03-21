package com.kim.quality.common;

import com.kim.common.constant.CommonConstants;

public class CommonConstant extends CommonConstants {
	
	/** 质检初始得分 数据字典key */
	public static final String DICT_ORIG_SCORE_KEY = "quality.origScore";
	/** 质检最大得分 数据字典key */
	public static final String DICT_MAX_SCORE_KEY = "quality.maxScore";
	/** 质检最小得分 数据字典key */
	public static final String DICT_MIN_SCORE_KEY = "quality.minScore";
	
	/** 质检默认初始得分 */
	public static final double DICT_ORIG_SCORE_DEFAULT = 100D;
	/** 质检最大默认得分 */
	public static final double DICT_MAX_SCORE_DEFAULT = 120D;
	/** 质检最小默认得分 */
	public static final double DICT_MIN_SCORE_DEFAULT = 0D;
	
	
	/** 质检申诉最大次数 数据字典key */
	public static final String PARAM_MAX_APPEAL_COUNT_KEY = "quality.appealCount";
	/** 质检申诉最大次数 ,若数据字典中未配置，则使用此默认值 */
	public static final int MAX_APPEAL_COUNT = 3;
	
	/** 质检任务在redis中的时长, 单位s, key */
	public static final String TASK_REDIS_EXPIRE_KEY = "task.redis.expire";
	/** 质检任务在redis中的默认时长: 7天, 单位s */
	public static final long TASK_REDIS_EXPIRE_DEFAULT = 3600L*24*7;
	
	/** 抽检时同步锁在redis中的超时时长: 10分钟, 单位s */
	public static final long SAMPLE_ORIG_REDIS_LOCK_EXPIRE_TIME = 60L * 10;
	
	/** 特殊业务线, 呼出录音  */
	public static final String BUSINESS_CALL_OUT_NAME = "呼出录音";
	/** 特殊业务线, 呼出录音  */
	public static final String BUSINESS_CALL_OUT = "call_out";
	
	/** 批量抽检一次最多抽检的天数  */
	public static final int SAMPLE_MAX_LIMIT_DEFAULT = 30;
	/** 批量抽检一次最多抽检的天数, 参数配置key  */
	public static final String SAMPLE_LIMIT_MAX_KEY = "sample.limit.max";
	
	/** 申诉上传附件文件允许最大值的参数配置key, 单位:M */
	public static final String APPEAL_ATTACHMENT_FILE_MAX_KEY = "appeal.attachment.file.max";
	/** 申诉上传附件文件允许上传的文件类型的参数配置key */
	public static final String APPEAL_ATTACHMENT_ALLOW_TYPE_KEY = "appeal.attachment.allow.type";
	/** 申诉上传附件文件允许的默认最大值, 单位:M */
	public static final int APPEAL_ATTACHMENT_FILE_MAX_DEFAULT = 5;
	/** 申诉上传附件文件允许上传的文件类型的参数配置key */
	public static final String APPEAL_ATTACHMENT_ALLOW_TYPE_DEFAULT = ".bmp,.jpg,.jpeg,.png";
	/** 申诉上传附件文件允许的最多附件个数  参数配置key */
	public static final String APPEAL_ATTACHMENT_LIMIT_KEY = "appeal.attachment.limit";
	/** 申诉上传附件文件允许的最多默认附件个数 */
	public static final int APPEAL_ATTACHMENT_LIMIT_DEFAULT = 4;
	
	
	/** 分数调整允许的最小值 参数配置key */
	public static final String ADJUST_SCORE_LIMIT_MIN_KEY = "adjustScore.limit.min";
	/** 分数调整允许的最大值 参数配置key */
	public static final String ADJUST_SCORE_LIMIT_MAX_KEY = "adjustScore.limit.max";
	/** 分数调整允许的最大次数 参数配置key */
	public static final String ADJUST_SCORE_LIMIT_MAX_COUNT_KEY = "adjustScore.limit.max.count";
	
	/** 分数调整允许的最小默认值 */
	public static final double ADJUST_SCORE_LIMIT_MIN_DEFAULT = -20;
	/** 分数调整允许的最大默认值 */
	public static final double ADJUST_SCORE_LIMIT_MAX_DEFAULT = 20;
	/** 分数调整允许的最大默认次数 */
	public static final int ADJUST_SCORE_LIMIT_MAX_COUNT_DEFAULT = 2;
	
}
