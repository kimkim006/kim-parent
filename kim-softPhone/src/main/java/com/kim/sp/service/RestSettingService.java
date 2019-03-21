package com.kim.sp.service;

import com.kim.sp.entity.RestSettingEntity;
import com.kim.sp.vo.RestSettingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import com.kim.common.page.PageRes;
import com.kim.common.base.BaseService;
import com.kim.sp.dao.RestSettingDao;
import com.kim.common.util.TokenUtil;

/**
 * 小休配置表服务实现类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
@Service
public class RestSettingService extends BaseService {
	
	@Autowired
	private RestSettingDao restSettingDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public RestSettingEntity findById(String id) {
	
		return restSettingDao.find(new RestSettingVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public RestSettingEntity find(RestSettingVo vo) {
	
		return restSettingDao.find(vo);
	}
	
	public RestSettingEntity findByOrg(String groupCode, String tenantId) {
		RestSettingVo vo = new RestSettingVo().tenantId(tenantId);
		vo.setDepartmentCode(groupCode);
		return restSettingDao.find(vo);
		
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public PageRes<RestSettingEntity> listByPage(RestSettingVo vo) {
		
		return restSettingDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	public List<RestSettingEntity> list(RestSettingVo vo) {
		
		return restSettingDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public RestSettingEntity insert(RestSettingEntity entity) {
		
		int n = restSettingDao.insert(entity);
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int update(RestSettingEntity entity) {

		return restSettingDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2019-3-7 15:41:35
	 * @author liubo
	 */
	@Transactional(readOnly=false)
	public int delete(RestSettingVo vo) {

		return restSettingDao.deleteLogic(vo);
	}

}
