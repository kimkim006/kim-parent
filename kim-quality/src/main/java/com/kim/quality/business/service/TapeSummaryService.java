package com.kim.quality.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.quality.business.dao.TapeSummaryDao;
import com.kim.quality.business.entity.TapeSummaryEntity;
import com.kim.quality.business.vo.TapeSummaryVo;
import com.kim.common.util.TokenUtil;

/**
 * 录音小结关联表服务实现类
 * @date 2018-11-16 15:55:08
 * @author bo.liu01
 */
@Service
public class TapeSummaryService extends BaseService {
	
	@Autowired
	private TapeSummaryDao tapeSummaryDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	public TapeSummaryEntity findById(String id) {
	
		return tapeSummaryDao.find(new TapeSummaryVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	public TapeSummaryEntity find(TapeSummaryVo vo) {
	
		return tapeSummaryDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	public PageRes<TapeSummaryEntity> listByPage(TapeSummaryVo vo) {
		
		return tapeSummaryDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	public List<TapeSummaryEntity> list(TapeSummaryVo vo) {
		
		return tapeSummaryDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public TapeSummaryEntity insert(TapeSummaryEntity entity) {
		
		int n = tapeSummaryDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(TapeSummaryEntity entity) {

		return tapeSummaryDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(TapeSummaryVo vo) {

		return tapeSummaryDao.deleteLogic(vo);
	}

}
