package com.kim.quality.business.service;

import com.kim.quality.business.dao.EvaluationDetailDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.quality.business.entity.EvaluationDetailEntity;
import com.kim.quality.business.vo.EvaluationDetailVo;
import com.kim.common.util.TokenUtil;

/**
 * 质检评分明细表服务实现类
 * @date 2018-9-25 14:28:11
 * @author yonghui.wu
 */
@Service
public class EvaluationDetailService extends BaseService {
	
	@Autowired
	private EvaluationDetailDao evaluationDetailDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-25 14:28:11
	 * @author yonghui.wu
	 */
	public EvaluationDetailEntity findById(String id) {
	
		return evaluationDetailDao.find(new EvaluationDetailVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-9-25 14:28:11
	 * @author yonghui.wu
	 */
	public EvaluationDetailEntity find(EvaluationDetailVo vo) {
	
		return evaluationDetailDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-9-25 14:28:11
	 * @author yonghui.wu
	 */
	public PageRes<EvaluationDetailEntity> listByPage(EvaluationDetailVo vo) {
		
		return evaluationDetailDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-9-25 14:28:11
	 * @author yonghui.wu
	 */
	public List<EvaluationDetailEntity> list(EvaluationDetailVo vo) {
		
		return evaluationDetailDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-9-25 14:28:11
	 * @author yonghui.wu
	 */
	@Transactional(readOnly=false)
	public EvaluationDetailEntity insert(EvaluationDetailEntity entity) {
		
		int n = evaluationDetailDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-9-25 14:28:11
	 * @author yonghui.wu
	 */
	@Transactional(readOnly=false)
	public int update(EvaluationDetailEntity entity) {

		return evaluationDetailDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-9-25 14:28:11
	 * @author yonghui.wu
	 */
	@Transactional(readOnly=false)
	public int delete(EvaluationDetailVo vo) {

		return evaluationDetailDao.deleteLogic(vo);
	}

}
