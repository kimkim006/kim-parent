package com.kim.sp.service;

import com.kim.sp.vo.ServiceTypeRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.sp.dao.ServiceTypeRecordDao;
import com.kim.sp.entity.ServiceTypeRecordEntity;
import com.kim.common.util.TokenUtil;

/**
 * 服务与服务类型关联表服务实现类
 * @date 2019-3-14 20:02:22
 * @author liubo
 */
@Service
public class ServiceTypeRecordService extends BaseService {
	
	@Autowired
	private ServiceTypeRecordDao serviceTypeRecordDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	public ServiceTypeRecordEntity findById(String id) {
	
		return serviceTypeRecordDao.find(new ServiceTypeRecordVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	public ServiceTypeRecordEntity find(ServiceTypeRecordVo vo) {
	
		return serviceTypeRecordDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	public PageRes<ServiceTypeRecordEntity> listByPage(ServiceTypeRecordVo vo) {
		
		return serviceTypeRecordDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	public List<ServiceTypeRecordEntity> list(ServiceTypeRecordVo vo) {
		
		return serviceTypeRecordDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public ServiceTypeRecordEntity insert(ServiceTypeRecordEntity entity) {
		
		int n = serviceTypeRecordDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(ServiceTypeRecordEntity entity) {

		return serviceTypeRecordDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(ServiceTypeRecordVo vo) {

		return serviceTypeRecordDao.deleteLogic(vo);
	}

}
