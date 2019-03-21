package com.kim.quality.business.service;

import com.kim.quality.business.dao.IvrInfoDao;
import com.kim.quality.business.entity.IvrInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.quality.business.vo.IvrInfoVo;
import com.kim.common.util.TokenUtil;

/**
 * 录音IVR信息服务实现类
 * @date 2018-11-16 15:55:07
 * @author bo.liu01
 */
@Service
public class IvrInfoService extends BaseService {
	
	@Autowired
	private IvrInfoDao ivrInfoDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-11-16 15:55:07
	 * @author bo.liu01
	 */
	public IvrInfoEntity findById(String id) {
	
		return ivrInfoDao.find(new IvrInfoVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-11-16 15:55:07
	 * @author bo.liu01
	 */
	public IvrInfoEntity find(IvrInfoVo vo) {
	
		return ivrInfoDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-11-16 15:55:07
	 * @author bo.liu01
	 */
	public PageRes<IvrInfoEntity> listByPage(IvrInfoVo vo) {
		
		return ivrInfoDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-11-16 15:55:07
	 * @author bo.liu01
	 */
	public List<IvrInfoEntity> list(IvrInfoVo vo) {
		
		return ivrInfoDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-11-16 15:55:07
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public IvrInfoEntity insert(IvrInfoEntity entity) {
		
		int n = ivrInfoDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-11-16 15:55:07
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(IvrInfoEntity entity) {

		return ivrInfoDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-11-16 15:55:07
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(IvrInfoVo vo) {

		return ivrInfoDao.deleteLogic(vo);
	}

}
