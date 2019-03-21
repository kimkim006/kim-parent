package com.kim.admin.dao;

import com.kim.admin.entity.UserOrgEntity;
import com.kim.admin.vo.UserOrgVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.common.page.PageRes;
import com.kim.common.page.PageVo;

/**
 * 组织人员关系表数据接口类
 * @date 2018-9-4 14:40:24
 * @author yonghui.wu
 */
@Repository
public interface UserOrgDao extends BaseDao<UserOrgEntity, UserOrgVo>{

 PageRes<UserOrgEntity> listByGroupPage(@Param("obj")UserOrgVo vo, @Param("page")PageVo page);
 PageRes<UserOrgEntity> listByUserPage(@Param("obj")UserOrgVo vo, @Param("page")PageVo page);
 
 int deleteLogicByLeader(UserOrgVo vo);

 }