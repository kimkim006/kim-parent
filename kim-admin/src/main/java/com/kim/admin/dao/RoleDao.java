package com.kim.admin.dao;

import com.kim.admin.entity.RoleEntity;
import com.kim.admin.vo.RoleVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

import java.util.List;

/**
 * 角色表数据接口类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Repository
public interface RoleDao extends BaseDao<RoleEntity, RoleVo>{

 List<String> listByUsername(RoleVo roleVo);

}