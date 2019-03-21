package com.kim.schedule.job.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.schedule.job.entity.TaskJobEntity;
import com.kim.schedule.job.vo.TaskJobVo;

 /**
 * 定时调度任务表数据接口类
 * @date 2018-8-21 10:39:53
 * @author bo.liu01
 */
@Repository
public interface TaskJobDao extends BaseDao<TaskJobEntity, TaskJobVo>{

}