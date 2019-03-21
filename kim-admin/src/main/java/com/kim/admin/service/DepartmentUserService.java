package com.kim.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.admin.dao.DepartmentUserDao;
import com.kim.admin.entity.DepartmentUserEntity;
import com.kim.admin.vo.DepartmentUserVo;
import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;

/**
 * 部门用户表服务实现类
 * @date 2017-11-16 14:28:31
 * @author bo.liu01
 */
@Service
public class DepartmentUserService extends BaseService {
	
	@Autowired
	private DepartmentUserDao departmentUserDao;
	
	/**
	 * 查询记录是否存在
	 * @param vo 查询对象
	 * @return 该记录的id值
	 * @date 2018-7-13 15:07:27
	 * @author bo.liu01
	 */
	public String checkExist(DepartmentUserVo vo) {
	
		return departmentUserDao.checkExist(vo);
	}

	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	public DepartmentUserEntity find(DepartmentUserVo vo) {
		
		return departmentUserDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo vo查询对象
	 * @return PageRes分页对象
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	public PageRes<DepartmentUserEntity> listByPage(DepartmentUserVo vo) {

		return departmentUserDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	public List<DepartmentUserEntity> list(DepartmentUserVo vo) {
		
		return departmentUserDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public DepartmentUserEntity insert(DepartmentUserEntity entity) {
		
		int n = departmentUserDao.insert(entity);
		return n > 0 ? entity : null;
	}
	
	/**
	 * 修改记录
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(DepartmentUserEntity entity) {

		return departmentUserDao.update(entity);
	}

	@Transactional(readOnly=false)
	public int deleteLogicByUser(DepartmentUserVo vo) {
		
		return departmentUserDao.deleteLogicByUser(vo);
	}
	
	/**
	 * 新增记录
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int insertUser(DepartmentUserEntity entity) {
		String[] usrnameList = entity.getUsernames().split(",");
		int n = 0;
		for (String usrname : usrnameList) {
			entity.setUsername(usrname);
			n += departmentUserDao.insert(entity);
		}
		return n;
	}

	public int removeUser(DepartmentUserEntity entity) {
		String[] usrnameList = entity.getUsernames().split(",");
		int n = 0;
		DepartmentUserVo vo = new DepartmentUserVo();
		vo.setTenantId(entity.getTenantId());
		for (String usrname : usrnameList) {
			vo.setUsername(usrname);
			n += departmentUserDao.deleteLogicByUser(vo);
		}
		return n;
	}

}
