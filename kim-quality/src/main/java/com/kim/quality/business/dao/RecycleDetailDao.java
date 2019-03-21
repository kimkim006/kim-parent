package com.kim.quality.business.dao;

import com.kim.quality.business.entity.RecycleDetailEntity;
import com.kim.quality.business.vo.RecycleDetailVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 任务回收明细表数据接口类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
@Repository
public interface RecycleDetailDao extends BaseDao<RecycleDetailEntity, RecycleDetailVo>{

}