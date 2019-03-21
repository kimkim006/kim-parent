package com.kim.sp.dao;

import com.kim.sp.entity.RestSettingEntity;
import com.kim.sp.vo.RestSettingVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 小休配置表数据接口类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
@Repository
public interface RestSettingDao extends BaseDao<RestSettingEntity, RestSettingVo>{

}