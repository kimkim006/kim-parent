package com.kim.schedule.task.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kim.quality.business.entity.TapeEntity;

 /**
 * 录音池表数据接口类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
@Repository
public interface CiscoTapeDao{
	
	/**
	 * 查询思科录音
	 * @return
	 */
	List<TapeEntity> listCiscoRecord(Map<String, Object> map);

}