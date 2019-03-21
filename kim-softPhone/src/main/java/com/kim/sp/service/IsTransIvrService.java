package com.kim.sp.service;

import java.util.List;

import com.kim.sp.dao.IsTransIvrDao;
import com.kim.sp.entity.Service10Entity;
import com.kim.sp.vo.IsTransIvrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.base.common.PlatformEnum;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;
import com.kim.common.util.DateUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.sp.entity.IsTransIvrEntity;

/**
 * 转IVR信息表服务实现类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
@Service
public class IsTransIvrService extends BaseService {
	
	@Autowired
	private IsTransIvrDao isTransIvrDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public IsTransIvrEntity findById(String id) {
	
		return isTransIvrDao.find(new IsTransIvrVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public IsTransIvrEntity find(IsTransIvrVo vo) {
	
		return isTransIvrDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public PageRes<IsTransIvrEntity> listByPage(IsTransIvrVo vo) {
		
		return isTransIvrDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public List<IsTransIvrEntity> list(IsTransIvrVo vo) {
		
		return isTransIvrDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public IsTransIvrEntity insert(IsTransIvrEntity entity) {
		
		int n = isTransIvrDao.insert(entity);
		return n > 0 ? entity : null;
	}
	
	@Transactional(readOnly=false)
	public IsTransIvrEntity insertByService10(Service10Entity entity) {
		
		IsTransIvrEntity isTransIvr = new IsTransIvrEntity();
		isTransIvr.copyBaseField(entity);
		isTransIvr.setCreateTime(entity.getOperTime());
		isTransIvr.setCreateName(entity.getOperName());
		isTransIvr.setCreateUser(entity.getOperUser());
		
		isTransIvr.setName(entity.getOperName());
		isTransIvr.setUsername(entity.getOperUser());
		isTransIvr.setAgentId(entity.getAgentId());
		isTransIvr.setCustomerCallId(entity.getCustomerCallId());
		isTransIvr.setIsTrans(IsTransIvrEntity.IS_TRANS_NO);
		isTransIvr.setTelephone(entity.getAni());
		isTransIvr.setAgentId(entity.getAgentId());
		isTransIvr.setServiceNo(entity.getServiceNo());
		int n = isTransIvrDao.insert(isTransIvr);
		return n > 0 ? isTransIvr : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(IsTransIvrEntity entity) {

		entity.setEndTime(DateUtil.getCurrentTime());
		entity.setIsTrans(IsTransIvrEntity.IS_TRANS_NO);
		entity.setUsername(entity.getOperUser());
		if(StringUtil.isBlank(entity.getAgentId())){
			entity.setAgentId(BaseCacheUtil.getCurAgentId(PlatformEnum.CISCO));
		}
		
		//根据customerCallId与agentId,username更新isTrans
		return isTransIvrDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(IsTransIvrVo vo) {

		return isTransIvrDao.deleteLogic(vo);
	}

}
