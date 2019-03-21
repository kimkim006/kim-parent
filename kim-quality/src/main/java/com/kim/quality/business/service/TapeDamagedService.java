package com.kim.quality.business.service;

import com.kim.quality.business.entity.TapeDamagedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.quality.business.dao.TapeDamagedDao;
import com.kim.quality.business.vo.TapeDamagedVo;
import com.kim.common.util.TokenUtil;

/**
 * 录音损坏明细表服务实现类
 * @date 2018-9-26 15:01:14
 * @author yonghui.wu
 */
@Service
public class TapeDamagedService extends BaseService {
	
	@Autowired
	private TapeDamagedDao tapeDamagedDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-26 15:01:14
	 * @author yonghui.wu
	 */
	public TapeDamagedEntity findById(String id) {
	
		return tapeDamagedDao.find(new TapeDamagedVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-26 15:01:14
	 * @author yonghui.wu
	 */
	public TapeDamagedEntity find(TapeDamagedVo vo) {
	
		return tapeDamagedDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-9-26 15:01:14
	 * @author yonghui.wu
	 */
	public PageRes<TapeDamagedEntity> listByPage(TapeDamagedVo vo) {
		
		return tapeDamagedDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-9-26 15:01:14
	 * @author yonghui.wu
	 */
	public List<TapeDamagedEntity> list(TapeDamagedVo vo) {
		
		return tapeDamagedDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-9-26 15:01:14
	 * @author yonghui.wu
	 */
	@Transactional(readOnly=false)
	public TapeDamagedEntity insert(TapeDamagedEntity entity) {
		
		int n = tapeDamagedDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-9-26 15:01:14
	 * @author yonghui.wu
	 */
	@Transactional(readOnly=false)
	public int update(TapeDamagedEntity entity) {

		return tapeDamagedDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-9-26 15:01:14
	 * @author yonghui.wu
	 */
	@Transactional(readOnly=false)
	public int delete(TapeDamagedVo vo) {

		return tapeDamagedDao.deleteLogic(vo);
	}

}
