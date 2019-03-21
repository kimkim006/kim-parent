package com.kim.admin.dao;

import com.kim.admin.entity.DepartmentUserEntity;
import com.kim.admin.vo.DepartmentUserVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 部门用户表数据接口类
 * @date 2017-11-16 14:28:31
 * @author bo.liu01
 */
@Repository
public interface DepartmentUserDao extends BaseDao<DepartmentUserEntity, DepartmentUserVo>{
	
	int deleteLogicByUser(DepartmentUserVo vo);
	
	String checkExist(DepartmentUserVo vo);

}