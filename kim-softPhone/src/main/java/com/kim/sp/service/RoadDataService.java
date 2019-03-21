package com.kim.sp.service;

import java.util.List;

import com.kim.sp.vo.RoadDataVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.base.common.PlatformEnum;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;
import com.kim.common.util.TokenUtil;
import com.kim.sp.dao.RoadDataDao;
import com.kim.sp.entity.RoadDataEntity;

/**
 * 随路数据表服务实现类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
@Service
public class RoadDataService extends BaseService {
	
	@Autowired
	private RoadDataDao roadDataDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public RoadDataEntity findById(String id) {
	
		return roadDataDao.find(new RoadDataVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public RoadDataEntity find(RoadDataVo vo) {
	
		return roadDataDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public PageRes<RoadDataEntity> listByPage(RoadDataVo vo) {
		
		return roadDataDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public List<RoadDataEntity> list(RoadDataVo vo) {
		
		return roadDataDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public RoadDataEntity insert(RoadDataEntity entity) {
		
		entity.setName(entity.getOperName());
		entity.setUsername(entity.getOperName());
		entity.setAgentId(BaseCacheUtil.getCurAgentId(PlatformEnum.CISCO));
		
		int n = roadDataDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(RoadDataEntity entity) {

		return roadDataDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(RoadDataVo vo) {

		return roadDataDao.deleteLogic(vo);
	}

}
