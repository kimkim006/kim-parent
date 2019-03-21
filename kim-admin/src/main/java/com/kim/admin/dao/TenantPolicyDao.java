package com.kim.admin.dao;

import java.util.List;

import com.kim.admin.entity.TenantPolicyEntity;
import com.kim.admin.vo.TenantPolicyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

 /**
 * 租户策略表数据接口类
 * @date 2018-8-1 14:10:22
 * @author bo.liu01
 */
@Repository
public interface TenantPolicyDao extends BaseDao<TenantPolicyEntity, TenantPolicyVo>{

	TenantPolicyEntity findByUsername(@Param("username")String username);

	List<TenantPolicyEntity> listByUsername(@Param("username")String username);

	int clearCurrent(@Param("username")String username);

	int setCurrent(@Param("username")String username, @Param("tenantId")String tenantId);

}