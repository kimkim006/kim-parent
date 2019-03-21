package com.kim.schedule.mq.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.business.entity.SummaryEntity;
import com.kim.quality.business.vo.SummaryVo;

 /**
 * 小结表数据接口类
 * @date 2018-11-14 16:19:30
 * @author bo.liu01
 */
@Repository
public interface SummaryDao extends BaseDao<SummaryEntity, SummaryVo>{

}