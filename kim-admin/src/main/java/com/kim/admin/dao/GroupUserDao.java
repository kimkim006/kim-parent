package com.kim.admin.dao;

import com.kim.admin.vo.GroupUserVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.admin.entity.GroupUserEntity;

/**
 * 质检小组人员表数据接口类
 * @date 2018-8-21 10:28:48
 * @author jianming.chen
 */
@Repository
public interface GroupUserDao extends BaseDao<GroupUserEntity, GroupUserVo>{

     int insertGroupUser(GroupUserVo groupUserVo);
 }