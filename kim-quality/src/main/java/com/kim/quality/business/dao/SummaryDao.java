package com.kim.quality.business.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.business.entity.SummaryEntity;
import com.kim.quality.business.vo.SummaryVo;

 /**
 * 小结表数据接口类
 * @date 2018-11-16 15:55:08
 * @author bo.liu01
 */
@Repository
public interface SummaryDao extends BaseDao<SummaryEntity, SummaryVo>{

}