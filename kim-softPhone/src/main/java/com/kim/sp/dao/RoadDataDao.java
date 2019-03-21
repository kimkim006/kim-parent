package com.kim.sp.dao;

import com.kim.sp.entity.RoadDataEntity;
import com.kim.sp.vo.RoadDataVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 随路数据表数据接口类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
@Repository
public interface RoadDataDao extends BaseDao<RoadDataEntity, RoadDataVo>{

}