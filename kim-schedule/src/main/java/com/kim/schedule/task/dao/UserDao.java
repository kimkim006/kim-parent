package com.kim.schedule.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kim.schedule.task.entity.UserAgentMapping;

 /**
 * 用户数据接口类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
@Repository
public interface UserDao{
	
	
	List<UserAgentMapping> listBusiGroupUser(@Param("tenantId")String tenantId);
	
	List<String> listAllTenant();
	
}