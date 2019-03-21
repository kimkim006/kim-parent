package com.kim.admin.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.admin.entity.UserRoleEntity;
import com.kim.admin.vo.UserRoleVo;

 /**
 * 用户角色表数据接口类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Repository
public interface UserRoleDao extends BaseDao<UserRoleEntity, UserRoleVo>{



}