package com.kim.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kim.admin.entity.UserOrgEntity;
import com.kim.admin.vo.UserOrgVo;

/**
 * 公共的用户组织关系基础操作
 * 
 * @author bo.liu01
 *
 */
@Repository
public interface BaseUserOrgDao {

	/**
	 * 获取用户组织关系
	 * @param code
	 * @return
	 */
	List<UserOrgEntity> listUserOrg(UserOrgVo vo);
	

}
