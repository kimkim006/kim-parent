package com.kim.sp.service;

import com.kim.sp.dao.ServiceTypeDao;
import com.kim.sp.entity.ServiceTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.sp.vo.ServiceTypeVo;
import com.kim.common.util.TokenUtil;

/**
 * 弹屏服务类型表服务实现类
 * @date 2019-3-14 20:02:22
 * @author liubo
 */
@Service
public class ServiceTypeService extends BaseService {
	
	@Autowired
	private ServiceTypeDao serviceTypeDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	public ServiceTypeEntity findById(String id) {
	
		return serviceTypeDao.find(new ServiceTypeVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	public ServiceTypeEntity find(ServiceTypeVo vo) {
	
		return serviceTypeDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	public PageRes<ServiceTypeEntity> listByPage(ServiceTypeVo vo) {
		
		return serviceTypeDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	public List<ServiceTypeEntity> list(ServiceTypeVo vo) {
		
		return serviceTypeDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public ServiceTypeEntity insert(ServiceTypeEntity entity) {
		
		int n = serviceTypeDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(ServiceTypeEntity entity) {

		return serviceTypeDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-14 20:02:22
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(ServiceTypeVo vo) {

		return serviceTypeDao.deleteLogic(vo);
	}

}
