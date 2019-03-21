package com.kim.sp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.base.common.PlatformEnum;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;
import com.kim.common.util.DateUtil;
import com.kim.common.util.TokenUtil;
import com.kim.sp.dao.AgentNotReadyDao;
import com.kim.sp.entity.AgentNotReadyEntity;
import com.kim.sp.vo.AgentNotReadyVo;

/**
 * 挂机不就绪记录表服务实现类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
@Service
public class AgentNotReadyService extends BaseService {
	
	@Autowired
	private AgentNotReadyDao agentNotReadyDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public AgentNotReadyEntity findById(String id) {
	
		return agentNotReadyDao.find(new AgentNotReadyVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public AgentNotReadyEntity find(AgentNotReadyVo vo) {
	
		return agentNotReadyDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public PageRes<AgentNotReadyEntity> listByPage(AgentNotReadyVo vo) {
		
		return agentNotReadyDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public List<AgentNotReadyEntity> list(AgentNotReadyVo vo) {
		
		return agentNotReadyDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public AgentNotReadyEntity insert(AgentNotReadyEntity entity) {
		
		entity.setStartTime(DateUtil.getCurrentTime());
		entity.setTypes(AgentNotReadyEntity.TYPE_NOT_READY);
		entity.setUsername(entity.getOperUser());
		entity.setName(entity.getOperName());
		entity.setAgentId(BaseCacheUtil.getCurAgentId(PlatformEnum.CISCO));
		
		int n = agentNotReadyDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(AgentNotReadyEntity entity) {

		return agentNotReadyDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(AgentNotReadyVo vo) {

		return agentNotReadyDao.deleteLogic(vo);
	}

}
