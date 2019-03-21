package com.kim.admin.dao;

import java.util.List;

import com.kim.admin.entity.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kim.admin.vo.UserVo;
import com.kim.common.base.BaseDao;
import com.kim.common.page.PageRes;
import com.kim.common.page.PageVo;

/**
 * 用户表数据接口类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Repository
public interface UserDao extends BaseDao<UserEntity, UserVo>{

  PageRes<UserEntity> listByDepartment(@Param("obj")UserVo userVo, @Param("page")PageVo page);
  
  PageRes<UserEntity> listByDepartment_(@Param("obj")UserVo userVo, @Param("page")PageVo page);

  PageRes<UserEntity> listByRole(@Param("obj")UserVo userVo, @Param("page")PageVo page);

  PageRes<UserEntity> listByRole_(@Param("obj")UserVo userVo, @Param("page")PageVo page);
  
  PageRes<UserEntity> listByGroup(@Param("obj")UserVo userVo, @Param("page")PageVo page);

  PageRes<UserEntity> listByGroup_(@Param("obj")UserVo userVo, @Param("page")PageVo page);
  
  List<UserEntity> listAll();
  
  int updatePortrait(UserEntity e);

  


}