package com.kim.sp.dao;

import com.kim.sp.vo.AgentNotReadyVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.sp.entity.AgentNotReadyEntity;

/**
 * 挂机不就绪记录表数据接口类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
@Repository
public interface AgentNotReadyDao extends BaseDao<AgentNotReadyEntity, AgentNotReadyVo>{

}