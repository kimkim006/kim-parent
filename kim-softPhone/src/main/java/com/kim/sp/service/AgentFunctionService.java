package com.kim.sp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.sp.dao.AgentFunctionDao;
import com.kim.sp.entity.AgentFunctionEntity;
import com.kim.sp.vo.AgentFunctionVo;
import com.kim.common.util.TokenUtil;

/**
 * 话务工号功能码表服务实现类
 * @date 2019-3-7 15:49:54
 * @author liubo
 */
@Service
public class AgentFunctionService extends BaseService {
	
	@Autowired
	private AgentFunctionDao agentFunctionDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:49:54
	 * @author liubo
	 */
	public AgentFunctionEntity findById(String id) {
	
		return agentFunctionDao.find(new AgentFunctionVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:49:54
	 * @author liubo
	 */
	public AgentFunctionEntity find(AgentFunctionVo vo) {
	
		return agentFunctionDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-7 15:49:54
	 * @author liubo
	 */
	public PageRes<AgentFunctionEntity> listByPage(AgentFunctionVo vo) {
		
		return agentFunctionDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-7 15:49:54
	 * @author liubo
	 */
	public List<AgentFunctionEntity> list(AgentFunctionVo vo) {
		
		return agentFunctionDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-7 15:49:54
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public AgentFunctionEntity insert(AgentFunctionEntity entity) {
		
		int n = agentFunctionDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-7 15:49:54
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(AgentFunctionEntity entity) {

		return agentFunctionDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-7 15:49:54
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(AgentFunctionVo vo) {

		return agentFunctionDao.deleteLogic(vo);
	}

}
