package com.kim.quality.common;

import com.kim.log.operatelog.Operation;

/**
 * Created by bo.liu01 on 2017/11/1.
 */
public class QualityOperation extends Operation {

    //自定义操作名称
    public static final String QUALITY_EXECUTE = "抽取数据";
    public static final String QUALITY_EXECUTE_BY_SYSTEM = "系统抽取数据";
                  
    public static final String QUERY_BUSINESS_COM = "查询业务线列表";
                  
    public static final String ALLOCATE_EXE = "任务分配";
    public static final String RECYCLE_EXE = "任务回收";
                  
    public static final String QUERY_RECYCLE_USER = "查询可回收用户";
                  
    public static final String MAIN_TASK_INFO = "任务详情";
                  
    public static final String MAIN_TASK_STATUS = "可用任务状态查询";
                  
    public static final String MAIN_TASK_RESULT = "结果查询";
    
    public static final String SAMPLE_DETAIL = "抽检数据查询";
    public static final String DELETE_DETAIL = "抽检数据删除";
    
    public static final String DOWNLOAD_TASK = "下载任务";
                  
    public static final String GET_AUDIO = "获取录音文件流";
                  
    public static final String QUERY_CUR_APPEAL = "当前用户申诉单";
    
    public static final String CALC_LAST = "计算最后一次记录";
    
    public static final String FIND_ATTACHMENT_CONFIG = "上传附件配置查询";
    public static final String ADD_ATTACHMENT = "上传附件";
    
    public static final String FIND_ADJUST_SCORE_OPT = "查询分数调整参数";
    public static final String ADD_ADJUST_SCORE = "分数调整";

}
