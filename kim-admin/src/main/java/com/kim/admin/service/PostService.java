package com.kim.admin.service;

import com.kim.admin.dao.UserPostDao;
import com.kim.admin.vo.UserPostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.admin.dao.PostDao;
import com.kim.admin.entity.PostEntity;
import com.kim.admin.entity.UserPostEntity;
import com.kim.admin.vo.PostVo;

/**
 * 职位表服务实现类
 * @date 2017-11-16 14:28:31
 * @author bo.liu01
 */
@Service
public class PostService extends BaseService {
	
	@Autowired
	private PostDao postDao;
	@Autowired
	private UserPostDao userPostDao;
	
	public String checkUnique(PostVo postVo) {
		
		return postDao.checkUnique(postVo);
	}

	/**
	 * 单条记录查询
	 * @param postVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	public PostEntity find(PostVo postVo) {
		
		return postDao.find(postVo);
	}
	
	/**
	 * 带分页的查询
	 * @param postVo vo查询对象
	 * @return PageRes分页对象
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	public PageRes<PostEntity> listByPage(PostVo postVo) {
		
		return postDao.listByPage(postVo, postVo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param postVo vo查询对象
	 * @return 
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	public List<PostEntity> list(PostVo postVo) {
		
		return postDao.list(postVo);
	}

	/**
	 * 新增记录
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public PostEntity insert(PostEntity postEntity) {
		
		int n = postDao.insert(postEntity);
		return n > 0 ? postEntity : null;
	}

	/**
	 * 修改记录
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(PostEntity postEntity) {

		return postDao.update(postEntity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2017-11-16 14:28:31
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(PostVo postVo) {
		
		UserPostVo v = new UserPostVo();
		v.setPostId(postVo.getId());
		v.setTenantId(postVo.getTenantId());
		List<UserPostEntity> list = userPostDao.list(v);
		if(CollectionUtil.isNotEmpty(list)){
			logger.error("职位正在被使用，不可删除, 用户个数:{}, postId:{}", list.size(), postVo.getId());
			throw new BusinessException("职位正在被使用，不可删除");
		}

		return postDao.deleteLogic(postVo);
	}

}
