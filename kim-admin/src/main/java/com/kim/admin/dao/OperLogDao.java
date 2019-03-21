package com.kim.admin.dao;

import com.kim.admin.vo.OperLogVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.log.entity.OperateLogsEntity;

 /**
 * 操作日志表数据接口类
 * @date 2018-7-12 10:48:45
 * @author bo.liu01
 */
@Repository
public interface OperLogDao extends BaseDao<OperateLogsEntity, OperLogVo>{

}