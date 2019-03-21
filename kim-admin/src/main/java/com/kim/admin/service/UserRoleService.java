package com.kim.admin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kim.admin.dao.RoleDao;
import com.kim.admin.dao.UserRoleDao;
import com.kim.admin.entity.RoleEntity;
import com.kim.admin.entity.UserRoleEntity;
import com.kim.admin.vo.RoleVo;
import com.kim.admin.vo.UserRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.common.base.BaseService;
import com.kim.common.util.BatchUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;

/**
 * 用户角色表服务实现类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Service
public class UserRoleService extends BaseService {
	
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private RoleDao roleDao;

	/**
	 * 单条记录查询
	 * @param userRoleVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public UserRoleEntity find(UserRoleVo userRoleVo) {
		
		return userRoleDao.find(userRoleVo);
	}
	
	/**
	 * 带分页的查询
	 * @param userRoleVo vo查询对象
	 * @return PageRes分页对象
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public Map<String, Object> listRole(UserRoleVo userRoleVo) {

		RoleVo roleVo = new RoleVo();
		roleVo.setTenantId(TokenUtil.getTenantId());
		List<RoleEntity> roleList = roleDao.list(roleVo);
		Map<String, Object> map = new HashMap<>();
		map.put("roles", CollectionUtil.javaList2MapList(roleList, "code", "name"));

		List<UserRoleEntity> list = userRoleDao.list(userRoleVo);
		List<String> roleCodeList = CollectionUtil.getPropertyList(list, "roleCode");
		map.put("roleCodes", roleCodeList);
		return map;
	}
	
	/**
	 * 不带带分页的查询
	 * @param userRoleVo vo查询对象
	 * @return 
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public List<UserRoleEntity> list(UserRoleVo userRoleVo) {
		
		return userRoleDao.list(userRoleVo);
	}

	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int insert(UserRoleVo userRoleVo) {

		int n = 0;
		if(StringUtil.isNotBlank(userRoleVo.getDelRole())){
			String[] delRoleArr = userRoleVo.getDelRole().split(",");
			userRoleVo.setDelRoleList(Arrays.asList(delRoleArr));
			n += userRoleDao.deleteLogic(userRoleVo);
		}

		if(StringUtil.isNotBlank(userRoleVo.getAddRole())){
			UserRoleEntity userRole;
			List<UserRoleEntity> userRoleList = new ArrayList<>();
			String[] addRoleArr = userRoleVo.getAddRole().split(",");
			for(String addRole:addRoleArr){
				userRole = new UserRoleEntity();
				userRole.setUsername(userRoleVo.getUsername());
				userRole.setRoleCode(addRole);
				userRole.setOperName(userRoleVo.getOperName());
				userRole.setOperUser(userRoleVo.getOperUser());
				userRole.setOperTime(userRoleVo.getOperTime());
				userRole.setTenantId(userRoleVo.getTenantId());
				userRoleList.add(userRole);
			}
			n += BatchUtil.batchInsert(userRoleDao, userRoleList);
		}

		return n;
	}
	
	@Transactional(readOnly=false)
	public int addUser(UserRoleVo vo) {
		
		String[] usernames = vo.getUsernames().split(",");
		UserRoleEntity userRole = new UserRoleEntity();
		userRole.setOperName(TokenUtil.getCurrentName());
		userRole.setOperUser(TokenUtil.getUsername());
		userRole.setTenantId(TokenUtil.getTenantId());
		userRole.setRoleCode(vo.getRoleCode());
		int n = 0;
		for (String username : usernames) {
			userRole.setUsername(username);
			n += userRoleDao.insert(userRole);
		}
		return n;
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(UserRoleVo userRoleVo) {

		String[] usernames = userRoleVo.getUsernames().split(",");
		int n = 0;
		for (String username : usernames) {
			userRoleVo.setUsername(username);
			n += userRoleDao.deleteLogic(userRoleVo);
		}
		return n;
	}

	public UserRoleEntity insert(UserRoleEntity userRole) {

		int n = userRoleDao.insert(userRole);
		return n > 0? userRole:null;
	}

}
