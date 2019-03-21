package com.kim.schedule.task.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.business.entity.TapeEntity;
import com.kim.quality.business.entity.TapeSyncEntity;
import com.kim.quality.business.vo.TapeVo;

 /**
 * 录音池表数据接口类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
@Repository
public interface TapeDao extends BaseDao<TapeEntity, TapeVo>{
	
	int insertSync(TapeSyncEntity entity);
	
	int updateSync(TapeSyncEntity entity);

}