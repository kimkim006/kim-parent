package com.kim.admin.service;

import com.kim.common.util.BatchUtil;
import com.kim.admin.dao.MenuResourceDao;
import com.kim.admin.entity.MenuResourceEntity;
import com.kim.admin.vo.MenuResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;

/**
 * 菜单资源表服务实现类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Service
public class MenuResourceService extends BaseService {
	
	@Autowired
	private MenuResourceDao menuResourceDao;

	/**
	 * 单条记录查询
	 * @param menuResourceVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public MenuResourceEntity find(MenuResourceVo menuResourceVo) {
		
		return menuResourceDao.find(menuResourceVo);
	}
	
	/**
	 * 带分页的查询
	 * @param menuResourceVo vo查询对象
	 * @return PageRes分页对象
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public PageRes<MenuResourceEntity> listByPage(MenuResourceVo menuResourceVo) {
		
		return menuResourceDao.listByPage(menuResourceVo, menuResourceVo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param menuResourceVo vo查询对象
	 * @return 
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public List<MenuResourceEntity> list(MenuResourceVo menuResourceVo) {
		
		return menuResourceDao.list(menuResourceVo);
	}

	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int insert(MenuResourceVo menuResourceVo) {

		String[] resCodes = menuResourceVo.getResourceCode().split(",");
		List<MenuResourceEntity> list = new ArrayList<>(resCodes.length);
		for(String resCode:resCodes){
			MenuResourceEntity entity = new MenuResourceEntity();
			entity.setMenuCode(menuResourceVo.getMenuCode());
			entity.setResourceCode(resCode);
			list.add(entity);
		}
		return BatchUtil.batchInsert(menuResourceDao, list);
	}

	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public MenuResourceEntity insert(MenuResourceEntity menuResourceEntity) {

		int n = menuResourceDao.insert(menuResourceEntity);
		return n > 0 ? menuResourceEntity : null;
	}

	/**
	 * 修改记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(MenuResourceEntity menuResourceEntity) {

		return menuResourceDao.update(menuResourceEntity);
	}

	/**
	 * 物理删除记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(MenuResourceVo menuResourceVo) {

		String[] resCodes = menuResourceVo.getResourceCode().split(",");
		int n = 0;
		for(String resCode:resCodes){
			menuResourceVo.setResourceCode(resCode);
			n += menuResourceDao.deleteLogic(menuResourceVo);
		}
		return n;
	}

}
