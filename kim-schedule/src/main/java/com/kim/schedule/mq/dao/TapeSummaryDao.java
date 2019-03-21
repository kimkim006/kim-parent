package com.kim.schedule.mq.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.business.entity.TapeSummaryEntity;
import com.kim.quality.business.vo.TapeSummaryVo;

 /**
 * 录音小结关联表数据接口类
 * @date 2018-11-14 16:19:30
 * @author bo.liu01
 */
@Repository
public interface TapeSummaryDao extends BaseDao<TapeSummaryEntity, TapeSummaryVo>{

}