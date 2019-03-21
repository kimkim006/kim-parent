package com.kim.quality.setting.service;

import java.util.List;

import com.kim.quality.setting.dao.RuleDirDao;
import com.kim.quality.setting.entity.RuleDirEntity;
import com.kim.quality.setting.vo.RuleDirVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.common.base.BaseService;
import com.kim.common.util.TokenUtil;

/**
 * 质检规则目录表服务实现类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
@Service
public class RuleDirService extends BaseService {
	
	@Autowired
	private RuleDirDao ruleDirDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	public RuleDirEntity findById(String id) {
	
		return ruleDirDao.find(new RuleDirVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	public List<RuleDirEntity> list(RuleDirVo vo) {
		
		return ruleDirDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public RuleDirEntity insert(RuleDirEntity entity) {
		
		int n = ruleDirDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(RuleDirEntity entity) {

		return ruleDirDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(RuleDirVo vo) {

		return ruleDirDao.deleteLogic(vo);
	}

}
