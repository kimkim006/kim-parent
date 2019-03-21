package com.kim.admin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kim.admin.common.BeanConfig;
import com.kim.admin.dao.TenantDao;
import com.kim.admin.entity.DepartmentEntity;
import com.kim.admin.entity.Tenant2MenuEntity;
import com.kim.admin.entity.TenantEntity;
import com.kim.admin.vo.TenantVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.admin.entity.MenuEntity;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;

/**
 * 租户表服务实现类
 * @date 2018-7-5 13:05:21
 * @author bo.liu01
 */
@Service
public class TenantService extends BaseService {
	
	@Autowired
	private TenantDao tenantDao;
	@Autowired
	private MenuService menuService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private BeanConfig beanConfig;
	
	/**
	 * 单条记录查询
	 * @param tenantVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	public String checkUnique(TenantVo tenantVo) {
		
		return tenantDao.checkUnique(tenantVo);
	}
	
	/**
	 * 单条记录查询
	 * @param tenantVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	public TenantEntity find(TenantVo tenantVo) {
		
		return tenantDao.find(tenantVo);
	}
	
	/**
	 * 带分页的查询
	 * @param tenantVo vo查询对象
	 * @return PageRes分页对象
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	public PageRes<TenantEntity> listByPage(TenantVo tenantVo) {
		
		return tenantDao.listByPage(tenantVo, tenantVo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param tenantVo vo查询对象
	 * @return 
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	public List<TenantEntity> list(TenantVo tenantVo) {
		
		return tenantDao.list(tenantVo);
	}

	/**
	 * 新增记录
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public TenantEntity insert(TenantEntity tenantEntity) {
		
		tenantEntity.setTenantId(TenantEntity.generatorNo());
		int n = tenantDao.insert(tenantEntity);
		if(n > 0){
			DepartmentEntity entity = new DepartmentEntity();
			entity.setName(tenantEntity.getName());
			entity.setOperName(tenantEntity.getOperName());
			entity.setOperUser(tenantEntity.getOperUser());
			entity.setOperTime(tenantEntity.getOperTime());
			entity.setTenantId(tenantEntity.getTenantId());
			departmentService.insertTenantDepart(entity );
		}
		return n > 0 ? tenantEntity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(TenantEntity tenantEntity) {

		return tenantDao.update(tenantEntity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int deleteLogic(TenantVo tenantVo) {
		
		TenantEntity entity = tenantDao.findById(tenantVo.getId());
		if(entity == null){
			logger.error("该租户不存在, id:{}", tenantVo.getId());
			return 0;
		}
		
		check(entity.getTenantId());
		
		int n = tenantDao.deleteLogic(tenantVo);
		if(n > 0){
			tenantVo.setTenantId(entity.getTenantId());
			tenantDao.deleteTenantMenuLogic(tenantVo);
		}
		return n;
	}
	
	private void check(String tenantId){
		String ignoreList = beanConfig.getTenantIgnoreTable();
		List<String> tables = tenantDao.listTenantTables(Arrays.asList(ignoreList.split(",")));
		Map<String, String> param = new HashMap<>();
		param.put("tenantId", tenantId);
		String res;
		for (String tableName : tables) {
			param.put("tableName", tableName);
			res = tenantDao.checkTenantTableData(param );
			if(res != null){
				logger.error("该租户下已经产生数据, 不可删除, tenantId:{}, tableName:{}", tenantId, tableName);
				throw new BusinessException("该租户下已经产生数据, 不可删除");
			}
		}
	}

	public Map<String, Object> listTreeByTenant(TenantVo tenantVo) {
			
		List<MenuEntity> list = menuService.listTenantTempMenu(tenantVo.getTenantId());
		Set<String> menuCodeSet = CollectionUtil.getPropertySet(list, "code");
		
		Map<String, Object> map = new HashMap<>();
		List<MenuEntity> menuList = menuService.listTreeByCon(menuCodeSet);
		List<String> openKeys = new ArrayList<>();
		for (MenuEntity entity : menuList) {
			if(NumberUtil.equals(entity.getType(), MenuEntity.TYPE_MENU_DIR)){
				openKeys.add(entity.getCode());
			}
		}
		map.put("openKeys", openKeys);
		map.put("menu", MenuTreeUtil.treeFilter(menuList));
		
		list = menuService.listBtnByTenant(tenantVo.getTenantId());
		Set<String> selfOwnedCodeSet = new HashSet<>();
		for (MenuEntity entity : list) {
			menuCodeSet.add(entity.getCode());
			selfOwnedCodeSet.add(entity.getCode());
		}
		map.put("owned", menuCodeSet);
		map.put("selfOwned", selfOwnedCodeSet);
		return map;
	}
	
	public List<MenuEntity> listTenantOwnedMenu(){
		String tenantId = TokenUtil.getTenantId();
		List<MenuEntity> list = new ArrayList<>();
		list.addAll(menuService.listTenantTempMenu(tenantId));
		list.addAll(menuService.listBtnByTenant(tenantId));
		return list;
	}
	
	@Transactional(readOnly=false)
	public int insertMenu(TenantVo vo) {
		int n = 0;
		if(StringUtil.isNotBlank(vo.getAddMenu())){
			String[] addAuths = vo.getAddMenu().split(",");
			List<Tenant2MenuEntity> list = new ArrayList<>(addAuths.length);
			for(String menuCode : addAuths){
				list.add(new Tenant2MenuEntity(vo.getTenantId(), menuCode));
			}
			n += tenantDao.batchInsertTenantMenu(list);
		}

		if(StringUtil.isNotBlank(vo.getDelMenu())){
			String[] delAuths = vo.getDelMenu().split(",");
			vo.setDelMenuList(Arrays.asList(delAuths));
			n += tenantDao.deleteTenantMenuLogic(vo);
		}
		return n;
	}

}
