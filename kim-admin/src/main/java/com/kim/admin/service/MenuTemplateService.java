package com.kim.admin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kim.admin.dao.MenuTemplateDao;
import com.kim.admin.dao.TenantDao;
import com.kim.admin.entity.MenuTemplateEntity;
import com.kim.admin.entity.TenantEntity;
import com.kim.admin.vo.MenuTemplateVo;
import com.kim.admin.vo.TenantVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.admin.entity.MenuEntity;
import com.kim.admin.entity.Template2MenuEntity;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;

/**
 * 租户菜单模板表服务实现类
 * @date 2018-8-2 15:08:09
 * @author bo.liu01
 */
@Service
public class MenuTemplateService extends BaseService {
	
	@Autowired
	private MenuTemplateDao menuTemplateDao;
	@Autowired
	private TenantDao tenantDao;
	@Autowired
	private MenuService menuService;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-2 15:08:09
	 * @author bo.liu01
	 */
	public MenuTemplateEntity findById(String id) {
	
		return menuTemplateDao.find(new MenuTemplateVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-2 15:08:09
	 * @author bo.liu01
	 */
	public MenuTemplateEntity find(MenuTemplateVo vo) {
	
		return menuTemplateDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-8-2 15:08:09
	 * @author bo.liu01
	 */
	public PageRes<MenuTemplateEntity> listByPage(MenuTemplateVo vo) {
		
		return menuTemplateDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-8-2 15:08:09
	 * @author bo.liu01
	 */
	public List<MenuTemplateEntity> list(MenuTemplateVo vo) {
		
		return menuTemplateDao.list(vo);
	}
	
	public Map<String, Object> listTreeByTemp(String templateCode) {
		
		Map<String, Object> map = new HashMap<>();
		List<MenuEntity> menuList = menuService.listTreeByCon(null);
		List<String> openKeys = new ArrayList<>();
		for (MenuEntity entity : menuList) {
			if(NumberUtil.equals(entity.getType(), MenuEntity.TYPE_MENU_DIR)){
				openKeys.add(entity.getCode());
			}
		}
		map.put("openKeys", openKeys);
		map.put("menu", MenuTreeUtil.treeFilter(menuList));
		
		List<MenuEntity> list = menuService.listBtnByTemplate(templateCode);
		map.put("owned", CollectionUtil.getPropertySet(list, "code"));
		return map;
	}

	/**
	 * 新增记录
	 * @date 2018-8-2 15:08:09
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public MenuTemplateEntity insert(MenuTemplateEntity entity) {
		
		int n = menuTemplateDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-8-2 15:08:09
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(MenuTemplateEntity entity) {

		return menuTemplateDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-2 15:08:09
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(MenuTemplateVo vo) {
		
		MenuTemplateEntity entity = find(vo);
		if(entity == null){
			logger.error("该租户模板不存在, id:{}", vo.getId());
			return 0;
		}
		
		TenantVo v = new TenantVo();
		v.setTemplateCode(entity.getCode());
		List<TenantEntity> list = tenantDao.list(v);
		if(CollectionUtil.isNotEmpty(list)){
			logger.error("该租户模板正在使用, 不可删除, tenant个数:{}", list.size());
			throw new BusinessException("该租户模板正在使用, 不可删除");
		}
		int n = menuTemplateDao.deleteLogic(vo);
		if(n > 0){
			vo.setCode(entity.getCode());
			menuTemplateDao.deleteTempMenuLogic(vo);
		}
		return n;
	}

	@Transactional(readOnly=false)
	public int insertMenu(MenuTemplateVo vo) {
		int n = 0;
		if(StringUtil.isNotBlank(vo.getAddMenu())){
			String[] addAuths = vo.getAddMenu().split(",");
			List<Template2MenuEntity> list = new ArrayList<>(addAuths.length);
			for(String menuCode : addAuths){
				list.add(new Template2MenuEntity(vo.getCode(), menuCode));
			}
			n += menuTemplateDao.batchInsertTempMenu(list);
		}

		if(StringUtil.isNotBlank(vo.getDelMenu())){
			String[] delAuths = vo.getDelMenu().split(",");
			vo.setDelMenuList(Arrays.asList(delAuths));
			n += menuTemplateDao.deleteTempMenuLogic(vo);
		}
		return n;
	}

}
