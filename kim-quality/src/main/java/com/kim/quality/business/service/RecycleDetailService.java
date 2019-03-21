package com.kim.quality.business.service;

import com.kim.quality.business.dao.RecycleDetailDao;
import com.kim.quality.business.entity.RecycleDetailEntity;
import com.kim.quality.business.vo.RecycleDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.common.util.TokenUtil;

/**
 * 任务回收明细表服务实现类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
@Service
public class RecycleDetailService extends BaseService {
	
	@Autowired
	private RecycleDetailDao recycleDetailDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	public RecycleDetailEntity findById(String id) {
	
		return recycleDetailDao.find(new RecycleDetailVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	public RecycleDetailEntity find(RecycleDetailVo vo) {
	
		return recycleDetailDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	public PageRes<RecycleDetailEntity> listByPage(RecycleDetailVo vo) {
		
		return recycleDetailDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	public List<RecycleDetailEntity> list(RecycleDetailVo vo) {
		
		return recycleDetailDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public RecycleDetailEntity insert(RecycleDetailEntity entity) {
		
		int n = recycleDetailDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(RecycleDetailEntity entity) {

		return recycleDetailDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(RecycleDetailVo vo) {

		return recycleDetailDao.deleteLogic(vo);
	}

}
