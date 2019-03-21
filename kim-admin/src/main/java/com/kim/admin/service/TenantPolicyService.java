package com.kim.admin.service;

import java.util.List;

import com.kim.admin.dao.TenantPolicyDao;
import com.kim.admin.entity.TenantPolicyEntity;
import com.kim.admin.vo.TenantPolicyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;

/**
 * 租户策略表服务实现类
 * @date 2018-8-1 14:10:22
 * @author bo.liu01
 */
@Service
public class TenantPolicyService extends BaseService {
	
	@Autowired
	private TenantPolicyDao tenantPolicyDao;
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-8-1 14:10:22
	 * @author bo.liu01
	 */
	public PageRes<TenantPolicyEntity> listByPage(TenantPolicyVo vo) {
		
		return tenantPolicyDao.listByPage(vo, vo.getPage());
	}
	
	public TenantPolicyEntity findByUsername(String username) {
		
		return tenantPolicyDao.findByUsername(username);
	}
	
	public List<TenantPolicyEntity> listByUsername(String username) {
		
		return tenantPolicyDao.listByUsername(username);
	}
	
	public int updateCurrent(String username, String tenantId){
		
		tenantPolicyDao.clearCurrent(username);
		return tenantPolicyDao.setCurrent(username, tenantId);
	}
	
	public int clearCurrent(String username){
		
		return tenantPolicyDao.clearCurrent(username);
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-8-1 14:10:22
	 * @author bo.liu01
	 */
	public List<TenantPolicyEntity> list(TenantPolicyVo vo) {
		
		return tenantPolicyDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-8-1 14:10:22
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public TenantPolicyEntity insert(TenantPolicyEntity entity) {
		
		int n = tenantPolicyDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-1 14:10:22
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(TenantPolicyVo vo) {

		return tenantPolicyDao.deleteLogic(vo);
	}

}
