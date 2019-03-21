package com.kim.admin.service;

import java.util.List;

import com.kim.admin.dao.RoleDao;
import com.kim.admin.dao.RoleTypeDao;
import com.kim.admin.entity.RoleEntity;
import com.kim.admin.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.admin.entity.RoleTypeEntity;
import com.kim.admin.vo.RoleTypeVo;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;

/**
 * 角色类型服务实现类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Service
public class RoleTypeService extends BaseService {
	
	@Autowired
	private RoleTypeDao roleTypeDao;
	@Autowired
	private RoleDao roleDao;

	/**
	 * 单条记录查询
	 * @param roleTypeVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public RoleTypeEntity find(RoleTypeVo roleTypeVo) {
		
		return roleTypeDao.find(roleTypeVo);
	}
	
	public Object checkUnique(RoleTypeVo roleTypeVo) {
		if(StringUtil.isBlank(roleTypeVo.getId())){
			return roleTypeDao.find(roleTypeVo);
		}
		return roleTypeDao.checkUnique(roleTypeVo);
	}
	
	/**
	 * 带分页的查询
	 * @param roleTypeVo vo查询对象
	 * @return PageRes分页对象
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public PageRes<RoleTypeEntity> listByPage(RoleTypeVo roleTypeVo) {
		
		return roleTypeDao.listByPage(roleTypeVo, roleTypeVo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param roleTypeVo vo查询对象
	 * @return 
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public List<RoleTypeEntity> list(RoleTypeVo roleTypeVo) {
		
		return roleTypeDao.list(roleTypeVo);
	}

	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public RoleTypeEntity insert(RoleTypeEntity roleTypeEntity) {
		
		int n = roleTypeDao.insert(roleTypeEntity);
		return n > 0 ? roleTypeEntity : null;
	}

	/**
	 * 修改记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(RoleTypeEntity roleTypeEntity) {

		return roleTypeDao.update(roleTypeEntity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(RoleTypeVo roleTypeVo) {

		RoleVo v = new RoleVo();
		v.setRoleTypeId(roleTypeVo.getId());
		v.setTenantId(roleTypeVo.getTenantId());
		List<RoleEntity> list = roleDao.list(v);
		if(CollectionUtil.isNotEmpty(list)){
			logger.error("该角色类型正在被使用，不可删除, 角色个数:{}, roleTypeId:{}", list.size(), roleTypeVo.getId());
			throw new BusinessException("该角色类型正在被使用，不可删除");
		}
		return roleTypeDao.deleteLogic(roleTypeVo);
	}

}
