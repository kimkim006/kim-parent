package com.kim.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kim.admin.dao.OperLogDao;
import com.kim.admin.vo.OperLogVo;
import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;
import com.kim.log.entity.OperateLogsEntity;

/**
 * 操作日志表服务实现类
 * @date 2018-7-12 10:48:45
 * @author bo.liu01
 */
@Service
public class OperLogService extends BaseService {
	
	@Autowired
	private OperLogDao operLogDao;

	/**
	 * 单条记录查询
	 * @param operLogVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-7-12 10:48:45
	 * @author bo.liu01
	 */
	public OperateLogsEntity find(OperLogVo operLogVo) {
		
		return operLogDao.find(operLogVo);
	}
	
	/**
	 * 带分页的查询
	 * @param operLogVo vo查询对象
	 * @return PageRes分页对象
	 * @date 2018-7-12 10:48:45
	 * @author bo.liu01
	 */
	public PageRes<OperateLogsEntity> listByPage(OperLogVo operLogVo) {
		
		return operLogDao.listByPage(operLogVo, operLogVo.getPage());
	}
	
}
