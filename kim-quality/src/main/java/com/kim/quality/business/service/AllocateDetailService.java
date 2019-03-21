package com.kim.quality.business.service;

import com.kim.quality.business.entity.AllocateDetailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.quality.business.dao.AllocateDetailDao;
import com.kim.quality.business.vo.AllocateDetailVo;
import com.kim.common.util.TokenUtil;

/**
 * 任务分配明细表服务实现类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
@Service
public class AllocateDetailService extends BaseService {
	
	@Autowired
	private AllocateDetailDao allocateDetailDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	public AllocateDetailEntity findById(String id) {
	
		return allocateDetailDao.find(new AllocateDetailVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	public AllocateDetailEntity find(AllocateDetailVo vo) {
	
		return allocateDetailDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	public PageRes<AllocateDetailEntity> listByPage(AllocateDetailVo vo) {
		
		return allocateDetailDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	public List<AllocateDetailEntity> list(AllocateDetailVo vo) {
		
		return allocateDetailDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public AllocateDetailEntity insert(AllocateDetailEntity entity) {
		
		int n = allocateDetailDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(AllocateDetailEntity entity) {

		return allocateDetailDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(AllocateDetailVo vo) {

		return allocateDetailDao.deleteLogic(vo);
	}

}
