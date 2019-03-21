package com.kim.sp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.sp.dao.AgentFuncRlDao;
import com.kim.sp.entity.AgentFuncRlEntity;
import com.kim.sp.vo.AgentFuncRlVo;
import com.kim.common.util.TokenUtil;

/**
 * 话务工号与功能码表关联表服务实现类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
@Service
public class AgentFuncRlService extends BaseService {
	
	@Autowired
	private AgentFuncRlDao agentFuncRlDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public AgentFuncRlEntity findById(String id) {
	
		return agentFuncRlDao.find(new AgentFuncRlVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public AgentFuncRlEntity find(AgentFuncRlVo vo) {
	
		return agentFuncRlDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public PageRes<AgentFuncRlEntity> listByPage(AgentFuncRlVo vo) {
		
		return agentFuncRlDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public List<AgentFuncRlEntity> list(AgentFuncRlVo vo) {
		
		return agentFuncRlDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public AgentFuncRlEntity insert(AgentFuncRlEntity entity) {
		
		int n = agentFuncRlDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(AgentFuncRlEntity entity) {

		return agentFuncRlDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(AgentFuncRlVo vo) {

		return agentFuncRlDao.deleteLogic(vo);
	}

}
