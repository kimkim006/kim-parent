package com.kim.sp.dao;

import com.kim.sp.entity.ServiceTypeRecordEntity;
import com.kim.sp.vo.ServiceTypeRecordVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 服务与服务类型关联表数据接口类
 * @date 2019-3-14 20:02:22
 * @author liubo
 */
@Repository
public interface ServiceTypeRecordDao extends BaseDao<ServiceTypeRecordEntity, ServiceTypeRecordVo>{

}