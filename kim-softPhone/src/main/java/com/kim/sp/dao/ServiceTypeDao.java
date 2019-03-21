package com.kim.sp.dao;

import com.kim.sp.entity.ServiceTypeEntity;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.sp.vo.ServiceTypeVo;

 /**
 * 弹屏服务类型表数据接口类
 * @date 2019-3-14 20:02:22
 * @author liubo
 */
@Repository
public interface ServiceTypeDao extends BaseDao<ServiceTypeEntity, ServiceTypeVo>{

}