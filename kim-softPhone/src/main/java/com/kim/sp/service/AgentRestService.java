package com.kim.sp.service;

import com.kim.sp.entity.AgentRestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.admin.entity.DepartmentUserEntity;
import com.kim.base.common.PlatformEnum;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.sp.dao.AgentRestDao;
import com.kim.sp.vo.AgentRestVo;
import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;

/**
 * 坐席小休记录表服务实现类
 * @date 2019-3-13 10:14:40
 * @author liubo
 */
@Service
public class AgentRestService extends BaseService {
	
	@Autowired
	private AgentRestDao agentRestDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	public AgentRestEntity findById(String id) {
	
		return agentRestDao.find(new AgentRestVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	public AgentRestEntity find(AgentRestVo vo) {
	
		return agentRestDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	public PageRes<AgentRestEntity> listByPage(AgentRestVo vo) {
		
		return agentRestDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	public List<AgentRestEntity> list(AgentRestVo vo) {
		
		return agentRestDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public AgentRestEntity restStart(AgentRestEntity entity) {
		
		entity.setRestDate(DateUtil.formatDate(new Date()));
		entity.setBqTime(entity.getOperTime());
		entity.setStartTime(entity.getOperTime());
		entity.setResult(AgentRestEntity.RESULT_SUCCESS);
		entity.setUsername(entity.getOperUser());
		entity.setName(entity.getOperName());
		entity.setAgentId(BaseCacheUtil.getCurAgentId(PlatformEnum.CISCO));
		DepartmentUserEntity depart = BaseCacheUtil.getCurDepart();
		if(depart != null){
			entity.setDepartmentCode(depart.getDepartmentCode());
			entity.setDepartmentName(depart.getDepartmentName());
		}
		int n = agentRestDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int restEnd(AgentRestEntity entity) {
		
		AgentRestVo vo = new AgentRestVo().tenantId(entity.getTenantId());
		vo.setUsername(entity.getOperUser());
		vo.setRestDate(DateUtil.formatDate(new Date()));
		AgentRestEntity agentRest = agentRestDao.findByUsername(vo);
		if(agentRest == null){
			logger.warn("结束小休时未找到小休开始记录，将新创建小休开始记录！username:{}, tenantId:{}", vo.getUsername(), vo.getTenantId());
			entity.setEndTime(entity.getOperTime());
			entity.setTimeLong(0L);
			restStart(entity);
			return 1;
		}
		//检查数据的完整
		String startTime = agentRest.getStartTime();
		if(StringUtil.isBlank(startTime)){
			logger.warn("结束小休时检查到小休开始记录没有开始时间，将使用排队时间！id:{}, username:{}, tenantId:{}", 
					agentRest.getId(), vo.getUsername(), vo.getTenantId());
			startTime = agentRest.getBqTime();
			if(StringUtil.isBlank(startTime)){
				logger.warn("结束小休时检查到小休开始记录没有开始时间，将使用操作时间！id:{}, username:{}, tenantId:{}", 
						agentRest.getId(), vo.getUsername(), vo.getTenantId());
				startTime = agentRest.getOperTime();
			}
		}
		if(StringUtil.isBlank(startTime)){
			entity.setTimeLong(0L);
			logger.warn("结束小休时检查到小休开始记录没有开始时间，小休时长将设为0！id:{}, username:{}, tenantId:{}", 
					agentRest.getId(), vo.getUsername(), vo.getTenantId());
		}else{
			//小休时长，单位：秒
			entity.setTimeLong((new Date().getTime() - DateUtil.parseDate(startTime).getTime())/1000);
		}
		entity.setEndTime(entity.getOperTime());
		entity.setId(agentRest.getId());
		
		return agentRestDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(AgentRestVo vo) {

		return agentRestDao.deleteLogic(vo);
	}

}
