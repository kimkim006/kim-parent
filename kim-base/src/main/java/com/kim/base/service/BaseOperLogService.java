package com.kim.base.service;

import com.kim.base.config.BaseBeanConfig;
import com.kim.base.dao.BaseOperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kim.common.base.BaseService;
import com.kim.common.util.TokenUtil;
import com.kim.log.entity.OperateLogsEntity;
import com.kim.log.service.OperateLogsService;

@Service
public class BaseOperLogService extends BaseService implements OperateLogsService<OperateLogsEntity> {
	
	@Autowired
	private BaseOperDao baseOperDao;
	@Autowired
	private BaseBeanConfig baseBeanConfig;

	@Override
	public int insert(OperateLogsEntity t) {
		t.setServiceId(baseBeanConfig.getServiceId());
		t.setTenantId(TokenUtil.getTenantId());
		return baseOperDao.insertOperLog(t);
	}

}
