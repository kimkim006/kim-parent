package com.kim.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kim.admin.entity.GroupUserEntity;
import com.kim.admin.vo.GroupUserVo;

/**
 * 公共的用户组基础操作
 * 
 * @author bo.liu01
 *
 */
@Repository
public interface BaseUserGroupDao {

	/**
	 * 获取小组组织关系
	 * @param code
	 * @return
	 */
	List<GroupUserEntity> listGroupByUser(GroupUserVo vo);
	
	

}
