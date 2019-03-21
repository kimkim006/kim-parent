package com.kim.admin.dao;

import com.kim.admin.entity.GroupEntity;
import com.kim.admin.vo.GroupVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 质检小组表数据接口类
 * @date 2018-8-17 18:12:05
 * @author jianming.chen
 */
@Repository
public interface GroupDao extends BaseDao<GroupEntity, GroupVo>{

}