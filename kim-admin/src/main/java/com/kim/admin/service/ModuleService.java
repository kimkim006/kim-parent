package com.kim.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.admin.dao.ModuleDao;
import com.kim.admin.vo.ModuleVo;
import com.kim.base.entity.ModuleEntity;
import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;

/**
 * 模块表服务实现类
 * @date 2017-11-13 14:14:20
 * @author bo.liu01
 */
@Service
public class ModuleService extends BaseService {
	
	@Autowired
	private ModuleDao moduleDao;

	/**
	 * 单条记录查询
	 * @param moduleVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2017-11-13 14:14:20
	 * @author bo.liu01
	 */
	public ModuleEntity find(ModuleVo moduleVo) {
		
		return moduleDao.find(moduleVo);
	}
	
	/**
	 * 带分页的查询
	 * @param moduleVo vo查询对象
	 * @return PageRes分页对象
	 * @date 2017-11-13 14:14:20
	 * @author bo.liu01
	 */
	public PageRes<ModuleEntity> listByPage(ModuleVo moduleVo) {
		
		return moduleDao.listByPage(moduleVo, moduleVo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param moduleVo vo查询对象
	 * @return 
	 * @date 2017-11-13 14:14:20
	 * @author bo.liu01
	 */
	public List<ModuleEntity> list(ModuleVo moduleVo) {
		
		return moduleDao.list(moduleVo);
	}

	/**
	 * 新增记录
	 * @date 2017-11-13 14:14:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public ModuleEntity insert(ModuleEntity moduleEntity) {
		
		int n = moduleDao.insert(moduleEntity);
		return n > 0 ? moduleEntity : null;
	}

	/**
	 * 修改记录
	 * @date 2017-11-13 14:14:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(ModuleEntity moduleEntity) {

		return moduleDao.update(moduleEntity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2017-11-13 14:14:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int deleteLogic(ModuleVo moduleVo) {

		return moduleDao.deleteLogic(moduleVo);
	}

}
