package com.kim.admin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kim.admin.dao.AuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.admin.entity.AuthorityEntity;
import com.kim.admin.entity.MenuEntity;
import com.kim.admin.vo.AuthorityVo;
import com.kim.common.base.BaseService;
import com.kim.common.constant.CommonConstants;
import com.kim.common.util.BatchUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;

/**
 * 权限表服务实现类
 * @date 2017-11-6 14:06:19
 * @author bo.liu01
 */
@Service
public class AuthorityService extends BaseService {
	
	@Autowired
	private AuthorityDao authorityDao;
	@Autowired
	private MenuService menuService;
	@Autowired
	private TenantService tenantService;

	/**
	 * 单条记录查询
	 * @param authorityVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2017-11-6 14:06:19
	 * @author bo.liu01
	 */
	public AuthorityEntity find(AuthorityVo authorityVo) {
		
		return authorityDao.find(authorityVo);
	}
	
	public Map<String, Object> listTree(AuthorityVo authorityVo) {

		List<AuthorityEntity> list;
		Set<String> ownedMenuCodeSet = new HashSet<>();
		if(authorityVo.getOwnerType() == AuthorityEntity.OWNER_TYPE_USER){
			list = authorityDao.listUserRoleAuth(authorityVo);
			ownedMenuCodeSet = CollectionUtil.getPropertySet(list, "menuCode");
		}
		Map<String, Object> map = new HashMap<>();
		List<MenuEntity> menuList = menuService.getAllMenu();
		if(!CommonConstants.DEFAULT_TENANT_ID.equals(TokenUtil.getTenantId())){
			List<MenuEntity> ownedList = tenantService.listTenantOwnedMenu();
			menuList = calcList(ownedList, menuList);
		}
		menuList = MenuTreeUtil.buildTree(menuList, ownedMenuCodeSet);
		
		List<String> openKeys = new ArrayList<>();
		for (MenuEntity entity : menuList) {
			if(NumberUtil.equals(entity.getType(), MenuEntity.TYPE_MENU_DIR)){
				openKeys.add(entity.getCode());
			}
		}
		map.put("openKeys", openKeys);
		map.put("menu", MenuTreeUtil.treeFilter(menuList));

		list = authorityDao.list(authorityVo);
		Set<String> selfOwnedCodeSet = new HashSet<>();
		for (AuthorityEntity entity : list) {
			ownedMenuCodeSet.add(entity.getMenuCode());
			selfOwnedCodeSet.add(entity.getMenuCode());
		}
		map.put("owned", ownedMenuCodeSet);
		map.put("selfOwned", selfOwnedCodeSet);
		return map;
	}
	
	private List<MenuEntity> calcList(List<MenuEntity> ownedList, List<MenuEntity> allList){
		List<MenuEntity> menuList = new ArrayList<>();
		if(CollectionUtil.isNotEmpty(ownedList)){
			Set<String> btnCodeSet = new HashSet<>();
			Map<String, MenuEntity> allMenuMap = CollectionUtil.getMapByProperty(allList, "code");
			Set<String> menuCodeSet = new HashSet<>();
			for(MenuEntity entity : ownedList){
				//过滤重复的菜单和按钮项
				if(btnCodeSet.contains(entity.getCode())){
					continue;
				}
				btnCodeSet.add(entity.getCode());
				menuList.add(entity);
				//获取所有有权限的菜单
				MenuTreeUtil.getMenus(entity.getParentCode(), menuList, allMenuMap, menuCodeSet);
			}
		}
		return menuList;
	}
	
	/**
	 * 不带带分页的查询
	 * @param authorityVo vo查询对象
	 * @return 
	 * @date 2017-11-6 14:06:19
	 * @author bo.liu01
	 */
	public List<AuthorityEntity> list(AuthorityVo authorityVo) {
		
		return authorityDao.list(authorityVo);
	}

	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:19
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int insert(AuthorityVo authorityVo) {

		int n = 0;
		if(StringUtil.isNotBlank(authorityVo.getAddAuth())){
			String[] addAuths = authorityVo.getAddAuth().split(",");
			List<AuthorityEntity> list = new ArrayList<>(addAuths.length);
			for(String menuCode : addAuths){
				AuthorityEntity entity = new AuthorityEntity();
				entity.setOwnerCode(authorityVo.getOwnerCode());
				entity.setOwnerType(authorityVo.getOwnerType());
				entity.setMenuCode(menuCode);
				entity.setTenantId(authorityVo.getTenantId());
				list.add(entity);
			}
			n += BatchUtil.batchInsert(authorityDao, list);
		}

		if(StringUtil.isNotBlank(authorityVo.getDelAuth())){
			String[] delAuths = authorityVo.getDelAuth().split(",");
			authorityVo.setDelAuthList(Arrays.asList(delAuths));
			n += authorityDao.deleteLogic(authorityVo);
		}
		return n;
	}

	/**
	 * 修改记录
	 * @date 2017-11-6 14:06:19
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(AuthorityEntity authorityEntity) {

		return authorityDao.update(authorityEntity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2017-11-6 14:06:19
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(AuthorityVo authorityVo) {

		return authorityDao.deleteLogic(authorityVo);
	}

}
