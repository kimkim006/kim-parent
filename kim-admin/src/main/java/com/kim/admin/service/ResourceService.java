package com.kim.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.admin.dao.ResourceDao;
import com.kim.admin.vo.ResourceVo;
import com.kim.base.entity.ResourceEntity;
import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;

/**
 * 资源表服务实现类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Service
public class ResourceService extends BaseService {
	
	@Autowired
	private ResourceDao resourceDao;

	/**
	 * 单条记录查询
	 * @param resourceVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public ResourceEntity find(ResourceVo resourceVo) {
		
		return resourceDao.find(resourceVo);
	}

	/**
	 * 带分页的查询
	 * @param resourceVo vo查询对象
	 * @return PageRes分页对象
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public PageRes<ResourceEntity> listByPage(ResourceVo resourceVo) {
		return resourceDao.listByPage(resourceVo, resourceVo.getPage());
	}

	/**
	 * 不带带分页的查询
	 * @param resourceVo vo查询对象
	 * @return 
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public List<ResourceEntity> list(ResourceVo resourceVo) {
		
		return resourceDao.list(resourceVo);
	}

	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public ResourceEntity insert(ResourceEntity resourceEntity) {
		
		int n = resourceDao.insert(resourceEntity);
		return n > 0 ? resourceEntity : null;
	}

	/**
	 * 修改记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(ResourceEntity resourceEntity) {

		return resourceDao.update(resourceEntity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int deleteLogic(ResourceVo resourceVo) {

		return resourceDao.deleteLogic(resourceVo);
	}

}
