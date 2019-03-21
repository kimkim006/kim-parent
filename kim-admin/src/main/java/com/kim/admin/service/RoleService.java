package com.kim.admin.service;

import java.util.List;

import com.kim.admin.dao.RoleDao;
import com.kim.admin.dao.UserRoleDao;
import com.kim.admin.entity.RoleEntity;
import com.kim.admin.entity.UserRoleEntity;
import com.kim.admin.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.admin.dao.AuthorityDao;
import com.kim.admin.entity.AuthorityEntity;
import com.kim.admin.vo.AuthorityVo;
import com.kim.admin.vo.UserRoleVo;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;

/**
 * 角色表服务实现类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Service
public class RoleService extends BaseService {
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private AuthorityDao authorityDao;

	/**
	 * 单条记录查询
	 * @param roleVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public RoleEntity find(RoleVo roleVo) {
		
		return roleDao.find(roleVo);
	}
	
	/**
	 * 带分页的查询
	 * @param roleVo vo查询对象
	 * @return PageRes分页对象
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public PageRes<RoleEntity> listByPage(RoleVo roleVo) {
		
		return roleDao.listByPage(roleVo, roleVo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param roleVo vo查询对象
	 * @return 
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public List<RoleEntity> list(RoleVo roleVo) {
		
		return roleDao.list(roleVo);
	}

	/**
	 * 根据用户名查询角色
	 * @param username
	 * @return
	 */
	public List<String> listByUsername(String username) {

		return roleDao.listByUsername(new RoleVo().setUsername(username));
	}

	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public RoleEntity insert(RoleEntity roleEntity) {
		
		int n = roleDao.insert(roleEntity);
		return n > 0 ? roleEntity : null;
	}

	/**
	 * 修改记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(RoleEntity roleEntity) {

		return roleDao.update(roleEntity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(RoleVo roleVo) {

		
		UserRoleVo vo = new UserRoleVo();
		vo.setTenantId(roleVo.getTenantId());
		vo.setRoleCode(roleVo.getCode());
		List<UserRoleEntity> list = userRoleDao.list(vo);
		if(CollectionUtil.isNotEmpty(list)){
			logger.error("该角色正在被使用，不可删除，roleCode:{}", roleVo.getCode());
			throw new BusinessException("该角色正在被使用，不可删除");
		}
		int n = roleDao.deleteLogic(roleVo);
		if(n > 0){
			AuthorityVo v = new AuthorityVo();
			v.setOwnerCode(roleVo.getCode());
			v.setOwnerType(AuthorityEntity.OWNER_TYPE_ROLE);
			v.setTenantId(roleVo.getTenantId());
			authorityDao.deleteLogic(v);
		}
		return n;
	}

}
