package com.kim.admin.dao;

import java.util.List;

import com.kim.admin.entity.DepartmentEntity;
import com.kim.admin.vo.DepartmentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 部门表数据接口类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Repository
public interface DepartmentDao extends BaseDao<DepartmentEntity, DepartmentVo>{

	List<DepartmentEntity> listByTenantId(@Param("tenantId")String tenantId);

}