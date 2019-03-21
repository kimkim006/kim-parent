package com.kim.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.admin.entity.UserAgentEntity;
import com.kim.admin.vo.UserAgentVo;

/**
 * @author liubo
 *
 */
@Repository
public interface BaseUserAgentDao extends BaseDao<UserAgentEntity, UserAgentVo>{

	List<UserAgentEntity> listUserAgent(UserAgentVo vo);
}