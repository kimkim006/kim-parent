package com.kim.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kim.admin.entity.DepartmentUserEntity;
import com.kim.admin.vo.DepartmentUserVo;

/**
 * 公共的用户机构基础操作
 * 
 * @author bo.liu01
 *
 */
@Repository
public interface BaseUserDepartDao {

	/**
	 * 获取用户的机构信息
	 * @param code
	 * @return
	 */
	List<DepartmentUserEntity> listDepartByUser(DepartmentUserVo vo);
	
	

}
