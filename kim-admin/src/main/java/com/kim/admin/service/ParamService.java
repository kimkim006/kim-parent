package com.kim.admin.service;

import java.util.List;

import com.kim.admin.dao.ParamDao;
import com.kim.admin.entity.ParamEntity;
import com.kim.admin.vo.ParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;

/**
 * 参数配置表服务实现类
 * @date 2018-8-15 14:22:43
 * @author bo.liu01
 */
@Service
public class ParamService extends BaseService {
	
	@Autowired
	private ParamDao paramDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	public ParamEntity findById(String id) {
	
		return paramDao.find(new ParamVo().id(id));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	public ParamEntity find(ParamVo vo) {
	
		return paramDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	public PageRes<ParamEntity> listByPage(ParamVo vo) {
		
		return paramDao.listByPage(vo, vo.getPage());
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	public List<ParamEntity> list(ParamVo vo) {
		
		return paramDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public ParamEntity insert(ParamEntity entity) {
		
		int n = paramDao.insert(entity);
		if(n > 0){
			BaseCacheUtil.clearParamCache(entity.getCode(), entity.getTenantId());
		}
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(ParamEntity entity) {

		int n = paramDao.update(entity);
		if(n > 0){
			BaseCacheUtil.clearParamCache(entity.getCode(), entity.getTenantId());
		}
		return n;
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(ParamVo vo) {
		
		ParamEntity e = paramDao.find(vo);
		if(e == null){
			logger.error("该参数配置不存在, id:{}",  vo.getId());
			return 0;
		}

		int n = paramDao.deleteLogic(vo);
		if(n > 0){
			BaseCacheUtil.clearParamCache(e.getCode(), vo.getTenantId());
		}
		return n;
	}

}
