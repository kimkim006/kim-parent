package com.kim.sp.dao;

import com.kim.sp.vo.PlatformVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.sp.entity.PlatformEntity;

/**
 * 话务平台信息表数据接口类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
@Repository
public interface PlatformDao extends BaseDao<PlatformEntity, PlatformVo>{

}