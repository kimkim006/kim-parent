package com.kim.quality.setting.service;

import com.kim.quality.setting.entity.SampleRuleEntity;
import com.kim.quality.setting.vo.SampleRuleDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.quality.setting.dao.SampleRuleDetailDao;
import com.kim.quality.setting.entity.SampleRuleDetailEntity;
import com.kim.common.util.TokenUtil;

/**
 * 抽检规则明细表服务实现类
 * @date 2018-8-23 14:52:50
 * @author bo.liu01
 */
@Service
public class SampleRuleDetailService extends BaseService {
	
	@Autowired
	private SampleRuleDetailDao sampleRuleDetailDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 */
	public SampleRuleDetailEntity findById(String id) {
	
		return sampleRuleDetailDao.find(new SampleRuleDetailVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 */
	public SampleRuleDetailEntity find(SampleRuleDetailVo vo) {
	
		return sampleRuleDetailDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 */
	public PageRes<SampleRuleDetailEntity> listByPage(SampleRuleDetailVo vo) {
		
		return sampleRuleDetailDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 */
	public List<SampleRuleDetailEntity> list(SampleRuleDetailVo vo) {
		
		return sampleRuleDetailDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int insert(SampleRuleEntity entity) {
		
		List<SampleRuleDetailEntity> list = entity.getRuleDetail();
		for (SampleRuleDetailEntity detail : list) {
			detail.setRuleId(entity.getId());
			detail.setOperName(entity.getOperName());
			detail.setOperUser(entity.getOperUser());
			detail.setTenantId(entity.getTenantId());
			detail.setOperTime(entity.getOperTime());
		}
		return sampleRuleDetailDao.insertBatch(list);
	}
	
	/**
	 * 修改记录
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(SampleRuleEntity entity) {
		
		deleteByRuleId(entity.getId());
		
		return insert(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int deleteByRuleId(String ruleId) {

		SampleRuleDetailVo vo = new SampleRuleDetailVo().tenantId(TokenUtil.getTenantId());
		vo.setRuleId(ruleId);
		return sampleRuleDetailDao.deleteLogic(vo);
	}
	
	@Transactional(readOnly=false)
	public int delete(SampleRuleDetailVo vo) {
		
		return sampleRuleDetailDao.deleteLogic(vo);
	}

}
