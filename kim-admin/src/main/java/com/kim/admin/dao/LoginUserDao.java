package com.kim.admin.dao;

import com.kim.admin.entity.UserEntity;
import com.kim.admin.entity.LoginUserEntity;
import com.kim.admin.vo.LoginUserVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 用户信息表数据接口类
 * 
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Repository
public interface LoginUserDao extends BaseDao<LoginUserEntity, LoginUserVo> {

	UserEntity findByUsername(String username);
	
	LoginUserEntity findForLogin(String username);

}