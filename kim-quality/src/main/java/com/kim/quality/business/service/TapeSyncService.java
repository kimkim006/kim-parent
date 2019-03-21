package com.kim.quality.business.service;

import com.kim.quality.business.dao.TapeSyncDao;
import com.kim.quality.business.entity.TapeSyncEntity;
import com.kim.quality.business.vo.TapeSyncVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.common.util.TokenUtil;

/**
 * 录音转储记录表服务实现类
 * @date 2018-8-21 14:48:54
 * @author bo.liu01
 */
@Service
public class TapeSyncService extends BaseService {
	
	@Autowired
	private TapeSyncDao tapeSyncDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-21 14:48:54
	 * @author bo.liu01
	 */
	public TapeSyncEntity findById(String id) {
	
		return tapeSyncDao.find(new TapeSyncVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-21 14:48:54
	 * @author bo.liu01
	 */
	public TapeSyncEntity find(TapeSyncVo vo) {
	
		return tapeSyncDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-8-21 14:48:54
	 * @author bo.liu01
	 */
	public PageRes<TapeSyncEntity> listByPage(TapeSyncVo vo) {
		
		return tapeSyncDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-8-21 14:48:54
	 * @author bo.liu01
	 */
	public List<TapeSyncEntity> list(TapeSyncVo vo) {
		
		return tapeSyncDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-8-21 14:48:54
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public TapeSyncEntity insert(TapeSyncEntity entity) {
		
		int n = tapeSyncDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-8-21 14:48:54
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(TapeSyncEntity entity) {

		return tapeSyncDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-21 14:48:54
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(TapeSyncVo vo) {

		return tapeSyncDao.deleteLogic(vo);
	}

}
