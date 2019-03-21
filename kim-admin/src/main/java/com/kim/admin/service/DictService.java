package com.kim.admin.service;

import java.util.List;

import com.kim.admin.dao.DictDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.admin.entity.DictEntity;
import com.kim.admin.vo.DictVo;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseService;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;

/**
 * 数据字典表服务实现类
 * @date 2018-8-15 14:22:43
 * @author bo.liu01
 */
@Service
public class DictService extends BaseService {
	
	@Autowired
	private DictDao dictDao;
	

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	public DictEntity findById(String id) {
	
		return dictDao.find(new DictVo().id(id));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	public DictEntity find(DictVo vo) {
	
		return dictDao.find(vo);
	}
	
	/**
	 * 判断是否字典项，还是键值对
	 * @param vo
	 * @return true字典项，false键值对
	 */
	public boolean isFirst(DictVo vo){
		return StringUtil.isBlank(vo.getParentId()) 
				|| DictEntity.DEFAULT_PARENT_ID.equals(vo.getParentId());
	}
	
	/**
	 * 判断是否字典项，还是键值对
	 * @param entity
	 * @return true字典项，false键值对
	 */
	public boolean isFirst(DictEntity entity){
		return StringUtil.isBlank(entity.getParentId()) 
				|| DictEntity.DEFAULT_PARENT_ID.equals(entity.getParentId());
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	public PageRes<DictEntity> listByPage(DictVo vo) {
		
		if(StringUtil.isBlank(vo.getParentId())){
			vo.setParentId(DictEntity.DEFAULT_PARENT_ID);
		}
		if(isFirst(vo)){
			return dictDao.listFirstByPage(vo, vo.getPage());
		}else{
			return dictDao.listByPage(vo, vo.getPage());
		}
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	public List<DictEntity> list(DictVo vo) {
		
		return dictDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public DictEntity insert(DictEntity entity) {
		
		int n = dictDao.insert(entity);
		if(n > 0 && isFirst(entity)){
			BaseCacheUtil.clearDictCache(entity.getParentId(), entity.getTenantId());
		}
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(DictEntity entity) {

		int n = dictDao.update(entity);
		if(n > 0 && isFirst(entity)){
			BaseCacheUtil.clearDictCache(entity.getParentId(), entity.getTenantId());
		}
		return n;
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(DictVo vo) {
		
		DictEntity e = dictDao.find(vo);
		if(e == null){
			logger.error("该字典不存在， id:{}", vo.getId());
			return 0;
		}
		if(isFirst(e)){
			DictVo v =new DictVo().tenantId(vo.getTenantId());
			v.setParentId(vo.getId());
			List<DictEntity> list = dictDao.list(v );
			if(CollectionUtil.isNotEmpty(list)){
				logger.error("该字典项有键值对，不可删除, id:{}", vo.getId());
				throw new BusinessException("该字典项有键值对，不可删除");
			}
		}

		int n = dictDao.deleteLogic(vo);
		if(n > 0 && isFirst(e)){
			BaseCacheUtil.clearDictCache(e.getParentId(), vo.getTenantId());
		}
		return n;
	}

}
