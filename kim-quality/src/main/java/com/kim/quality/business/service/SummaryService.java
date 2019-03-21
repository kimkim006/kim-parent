package com.kim.quality.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.quality.business.dao.SummaryDao;
import com.kim.quality.business.entity.SummaryEntity;
import com.kim.quality.business.vo.SummaryVo;
import com.kim.common.util.TokenUtil;

/**
 * 小结表服务实现类
 * @date 2018-11-16 15:55:08
 * @author bo.liu01
 */
@Service
public class SummaryService extends BaseService {
	
	@Autowired
	private SummaryDao summaryDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	public SummaryEntity findById(String id) {
	
		return summaryDao.find(new SummaryVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	public SummaryEntity find(SummaryVo vo) {
	
		return summaryDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	public PageRes<SummaryEntity> listByPage(SummaryVo vo) {
		
		return summaryDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	public List<SummaryEntity> list(SummaryVo vo) {
		
		return summaryDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public SummaryEntity insert(SummaryEntity entity) {
		
		int n = summaryDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(SummaryEntity entity) {

		return summaryDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(SummaryVo vo) {

		return summaryDao.deleteLogic(vo);
	}

}
