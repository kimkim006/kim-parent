package com.kim.sp.service;

import com.kim.sp.dao.RestSettingDetailDao;
import com.kim.sp.entity.RestSettingDetailEntity;
import com.kim.sp.vo.RestSettingDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.common.util.TokenUtil;

/**
 * 小休配置明细表服务实现类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
@Service
public class RestSettingDetailService extends BaseService {
	
	@Autowired
	private RestSettingDetailDao restSettingDetailDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public RestSettingDetailEntity findById(String id) {
	
		return restSettingDetailDao.find(new RestSettingDetailVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public RestSettingDetailEntity find(RestSettingDetailVo vo) {
	
		return restSettingDetailDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public PageRes<RestSettingDetailEntity> listByPage(RestSettingDetailVo vo) {
		
		return restSettingDetailDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public List<RestSettingDetailEntity> list(RestSettingDetailVo vo) {
		
		return restSettingDetailDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public RestSettingDetailEntity insert(RestSettingDetailEntity entity) {
		
		int n = restSettingDetailDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(RestSettingDetailEntity entity) {

		return restSettingDetailDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(RestSettingDetailVo vo) {

		return restSettingDetailDao.deleteLogic(vo);
	}

}
