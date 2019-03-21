package com.kim.quality.business.dao;

import com.kim.quality.business.entity.AllocateDetailEntity;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.business.vo.AllocateDetailVo;

 /**
 * 任务分配明细表数据接口类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
@Repository
public interface AllocateDetailDao extends BaseDao<AllocateDetailEntity, AllocateDetailVo>{

}