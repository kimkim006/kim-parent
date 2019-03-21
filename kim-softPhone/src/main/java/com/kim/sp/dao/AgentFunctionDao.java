package com.kim.sp.dao;

import com.kim.sp.entity.AgentFunctionEntity;
import com.kim.sp.vo.AgentFunctionVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 话务工号功能码表数据接口类
 * @date 2019-3-7 15:49:54
 * @author liubo
 */
@Repository
public interface AgentFunctionDao extends BaseDao<AgentFunctionEntity, AgentFunctionVo>{

}