package com.kim.sp.dao;

import com.kim.sp.entity.RecordPlatformEntity;
import com.kim.sp.vo.RecordPlatformVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 录音平台信息表数据接口类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
@Repository
public interface RecordPlatformDao extends BaseDao<RecordPlatformEntity, RecordPlatformVo>{

}