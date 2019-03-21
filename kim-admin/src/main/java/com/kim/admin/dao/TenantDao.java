package com.kim.admin.dao;

import java.util.List;
import java.util.Map;

import com.kim.admin.vo.TenantVo;
import org.springframework.stereotype.Repository;

import com.kim.admin.entity.Tenant2MenuEntity;
import com.kim.admin.entity.TenantEntity;
import com.kim.common.base.BaseDao;

 /**
 * 租户表数据接口类
 * @date 2018-7-5 13:05:21
 * @author bo.liu01
 */
@Repository
public interface TenantDao extends BaseDao<TenantEntity, TenantVo>{
	
	String checkUnique(TenantVo tenantVo);

	int batchInsertTenantMenu(List<Tenant2MenuEntity> list);

	int deleteTenantMenuLogic(TenantVo vo);

	TenantEntity findById(String id);
	
	List<String> listTenantTables(List<String> ignoreList);
	
	String checkTenantTableData(Map<String, String> param);

}