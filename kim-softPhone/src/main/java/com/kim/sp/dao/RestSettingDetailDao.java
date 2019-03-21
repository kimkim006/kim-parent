package com.kim.sp.dao;

import com.kim.sp.vo.RestSettingDetailVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.sp.entity.RestSettingDetailEntity;

/**
 * 小休配置明细表数据接口类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
@Repository
public interface RestSettingDetailDao extends BaseDao<RestSettingDetailEntity, RestSettingDetailVo>{

}