package com.kim.sp.dao;

import com.kim.sp.entity.AgentFuncRlEntity;
import com.kim.sp.vo.AgentFuncRlVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 话务工号与功能码表关联表数据接口类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
@Repository
public interface AgentFuncRlDao extends BaseDao<AgentFuncRlEntity, AgentFuncRlVo>{

}