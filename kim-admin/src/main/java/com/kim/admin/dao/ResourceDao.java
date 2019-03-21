package com.kim.admin.dao;

import org.springframework.stereotype.Repository;

import com.kim.admin.vo.ResourceVo;
import com.kim.base.entity.ResourceEntity;
import com.kim.common.base.BaseDao;

 /**
 * 资源表数据接口类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Repository
public interface ResourceDao extends BaseDao<ResourceEntity, ResourceVo>{

}