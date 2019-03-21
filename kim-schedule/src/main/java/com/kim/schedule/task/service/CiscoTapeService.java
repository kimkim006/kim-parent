package com.kim.schedule.task.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kim.schedule.task.dao.CiscoTapeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kim.common.base.BaseService;
import com.kim.mybatis.dynamic.DataSourceType;
import com.kim.mybatis.dynamic.annotation.TargetDataSource;
import com.kim.quality.business.entity.TapeEntity;

/**
 * 录音池表服务实现类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
@Service
public class CiscoTapeService extends BaseService {
	
	@Autowired
	private CiscoTapeDao ciscoTapeDao;
	
	@TargetDataSource(DataSourceType.SQL_SERVER)
	@Transactional(readOnly=true, propagation=Propagation.REQUIRES_NEW)
	public List<TapeEntity> listCiscoCallInRecord(String date, List<String> serviceNos) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("serviceNos", serviceNos);
		map.put("direction", TapeEntity.TYPE_NAME_IN);
		map.put("startTime", date + " 00:00:00");
		map.put("endTime", date + " 23:59:59");
		return ciscoTapeDao.listCiscoRecord(map);
	}
	
	@TargetDataSource(DataSourceType.SQL_SERVER)
	@Transactional(readOnly=true, propagation=Propagation.REQUIRES_NEW)
	public List<TapeEntity> listCiscoCallOutRecord(String date, List<String> agentNos) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("agentNos", agentNos);
		map.put("direction", TapeEntity.TYPE_NAME_OUT);
		map.put("startTime", date + " 00:00:00");
		map.put("endTime", date + " 23:59:59");
		return ciscoTapeDao.listCiscoRecord(map);
	}

}
