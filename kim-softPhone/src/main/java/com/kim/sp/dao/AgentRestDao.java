package com.kim.sp.dao;

import com.kim.sp.entity.AgentRestEntity;
import com.kim.sp.vo.AgentRestVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 坐席小休记录表数据接口类
 * @date 2019-3-13 10:14:40
 * @author liubo
 */
@Repository
public interface AgentRestDao extends BaseDao<AgentRestEntity, AgentRestVo>{
	
	AgentRestEntity findByUsername(AgentRestVo vo);

}