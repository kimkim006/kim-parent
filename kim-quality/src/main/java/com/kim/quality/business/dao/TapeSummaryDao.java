package com.kim.quality.business.dao;

import com.kim.quality.business.entity.TapeSummaryEntity;
import com.kim.quality.business.vo.TapeSummaryVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 录音小结关联表数据接口类
 * @date 2018-11-16 15:55:08
 * @author bo.liu01
 */
@Repository
public interface TapeSummaryDao extends BaseDao<TapeSummaryEntity, TapeSummaryVo>{

}