package com.kim.quality.business.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.business.entity.SampleEntity;
import com.kim.quality.business.vo.SampleVo;

 /**
 * 抽检批次记录表数据接口类
 * @date 2018-8-28 18:24:20
 * @author bo.liu01
 */
@Repository
public interface SampleDao extends BaseDao<SampleEntity, SampleVo>{
	
	int updateStatus(SampleEntity entity);

	int correctStatus(SampleEntity entity);

}