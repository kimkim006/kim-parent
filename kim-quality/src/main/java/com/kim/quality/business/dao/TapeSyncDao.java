package com.kim.quality.business.dao;

import com.kim.quality.business.entity.TapeSyncEntity;
import com.kim.quality.business.vo.TapeSyncVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 录音转储记录表数据接口类
 * @date 2018-8-21 14:48:54
 * @author bo.liu01
 */
@Repository
public interface TapeSyncDao extends BaseDao<TapeSyncEntity, TapeSyncVo>{

}