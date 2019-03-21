package com.kim.sp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.sp.dao.PlatformDao;
import com.kim.sp.entity.PlatformEntity;
import com.kim.sp.vo.PlatformVo;
import com.kim.common.util.TokenUtil;

/**
 * 话务平台信息表服务实现类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
@Service
public class PlatformService extends BaseService {
	
	@Autowired
	private PlatformDao platformDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public PlatformEntity findById(String id) {
	
		return platformDao.find(new PlatformVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public PlatformEntity find(PlatformVo vo) {
	
		return platformDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public PageRes<PlatformEntity> listByPage(PlatformVo vo) {
		
		return platformDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	public List<PlatformEntity> list(PlatformVo vo) {
		
		return platformDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public PlatformEntity insert(PlatformEntity entity) {
		
		int n = platformDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(PlatformEntity entity) {

		return platformDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(PlatformVo vo) {

		return platformDao.deleteLogic(vo);
	}

}
