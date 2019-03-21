package com.kim.admin.service;

import com.kim.admin.dao.UserPostDao;
import com.kim.admin.vo.UserPostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.util.TokenUtil;
import com.kim.common.base.BaseService;
import com.kim.admin.entity.UserPostEntity;

/**
 * 用户职位表服务实现类
 * @date 2018-7-13 15:07:27
 * @author bo.liu01
 */
@Service
public class UserPostService extends BaseService {
	
	@Autowired
	private UserPostDao userPostDao;

	/**
	 * 查询记录是否存在
	 * @param userPostVo vo查询对象
	 * @return 该记录的id值
	 * @date 2018-7-13 15:07:27
	 * @author bo.liu01
	 */
	public String checkExist(UserPostVo userPostVo) {
	
		return userPostDao.checkExist(userPostVo);
	}
	
	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-7-13 15:07:27
	 * @author bo.liu01
	 */
	public UserPostEntity findById(String id) {
		
		return userPostDao.find(new UserPostVo().id(id));
	}
	
	/**
	 * 单条记录查询
	 * @param userPostVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-7-13 15:07:27
	 * @author bo.liu01
	 */
	public UserPostEntity find(UserPostVo userPostVo) {
	
		return userPostDao.find(userPostVo);
	}
	
	/**
	 * 带分页的查询
	 * @param userPostVo vo查询对象
	 * @return PageRes分页对象
	 * @date 2018-7-13 15:07:27
	 * @author bo.liu01
	 */
	public PageRes<UserPostEntity> listByPage(UserPostVo userPostVo) {
		
		return userPostDao.listByPage(userPostVo, userPostVo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param userPostVo vo查询对象
	 * @return 
	 * @date 2018-7-13 15:07:27
	 * @author bo.liu01
	 */
	public List<UserPostEntity> list(UserPostVo userPostVo) {
		
		return userPostDao.list(userPostVo);
	}

	/**
	 * 新增记录
	 * @date 2018-7-13 15:07:27
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public UserPostEntity insert(UserPostEntity userPostEntity) {
		
		int n = userPostDao.insert(userPostEntity);
		return n > 0 ? userPostEntity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-7-13 15:07:27
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(UserPostEntity userPostEntity) {

		return userPostDao.update(userPostEntity);
	}
	
	@Transactional(readOnly=false)
	public int delete(String username) {

		UserPostVo userPostVo = new UserPostVo();
		userPostVo.setUsername(username);
		userPostVo.setTenantId(TokenUtil.getTenantId());
		return userPostDao.deleteLogic(userPostVo);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-7-13 15:07:27
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(UserPostVo userPostVo) {

		return userPostDao.deleteLogic(userPostVo);
	}

}
