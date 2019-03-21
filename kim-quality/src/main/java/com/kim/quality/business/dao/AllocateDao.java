package com.kim.quality.business.dao;

import com.kim.quality.business.entity.AllocateEntity;
import com.kim.quality.business.vo.AllocateVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 质检任务分配记录表数据接口类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
@Repository
public interface AllocateDao extends BaseDao<AllocateEntity, AllocateVo>{

}