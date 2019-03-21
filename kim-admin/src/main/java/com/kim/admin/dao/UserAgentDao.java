package com.kim.admin.dao;

import com.kim.admin.entity.UserAgentEntity;
import com.kim.admin.vo.UserAgentVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 坐席工号表数据接口类
 * @date 2018-9-7 15:33:14
 * @author yonghui.wu
 */
@Repository
public interface UserAgentDao extends BaseDao<UserAgentEntity, UserAgentVo>{
    boolean checkAgentNoUnique(UserAgentEntity vo);
}