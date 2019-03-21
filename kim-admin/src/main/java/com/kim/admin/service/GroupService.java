package com.kim.admin.service;

import com.kim.admin.dao.GroupDao;
import com.kim.admin.dao.GroupUserDao;
import com.kim.admin.entity.GroupEntity;
import com.kim.admin.entity.GroupUserEntity;
import com.kim.admin.vo.GroupUserVo;
import com.kim.admin.vo.GroupVo;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 质检小组表服务实现类
 * @date 2018-8-17 18:12:05
 * @author jianming.chen
 */
@Service
public class GroupService extends BaseService {
	
	@Autowired
	private GroupDao groupDao;

	@Autowired
	private GroupUserDao groupUserDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-17 18:12:05
	 * @author jianming.chen
	 */
	public GroupEntity findById(String id) {
	
		return groupDao.find(new GroupVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-17 18:12:05
	 * @author jianming.chen
	 */
	public GroupEntity find(GroupVo vo) {
	
		return groupDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param groupVo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-8-17 18:12:05
	 * @author jianming.chen
	 */
	public PageRes<GroupEntity> listByPage(GroupVo groupVo) {

		return groupDao.listByPage(groupVo, groupVo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-8-17 18:12:05
	 * @author jianming.chen
	 */
	public List<GroupEntity> list(GroupVo vo) {
		
		return groupDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-8-17 18:12:05
	 * @author jianming.chen
	 */
	@Transactional(readOnly=false)
	public GroupEntity insert(GroupEntity entity) {
		
		int n = groupDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-8-17 18:12:05
	 * @author jianming.chen
	 */
	@Transactional(readOnly=false)
	public int update(GroupEntity entity) {

		return groupDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-17 18:12:05
	 * @author jianming.chen
	 */
	@Transactional(readOnly=false)
	public int delete(GroupVo vo) {
		GroupEntity groupEntity = groupDao.find(vo);
		GroupUserVo groupUserVo = new GroupUserVo();
		groupUserVo.setGroupCode(groupEntity.getCode());
		groupUserVo.setTenantId(groupEntity.getTenantId());
		List<GroupUserEntity> groupUserEntityList = groupUserDao.list(groupUserVo);
		if(CollectionUtil.isNotEmpty(groupUserEntityList)){
			logger.error("该小组正在被使用，不可删除，groupCode:{}", vo.getCode());
			throw new BusinessException("该小组正在被使用，不可删除");
		}
		return groupDao.deleteLogic(vo);
	}

}
