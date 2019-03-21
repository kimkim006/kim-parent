package com.kim.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kim.admin.dao.DepartmentUserDao;
import com.kim.admin.entity.DepartmentEntity;
import com.kim.admin.entity.DepartmentUserEntity;
import com.kim.admin.vo.DepartmentUserVo;
import com.kim.admin.vo.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kim.admin.dao.DepartmentDao;
import com.kim.common.base.BaseService;
import com.kim.common.constant.RedisConstant;
import com.kim.common.exception.BusinessException;
import com.kim.common.page.PageRes;
import com.kim.common.redis.RedisUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.common.util.UUIDUtils;

/**
 * 部门表服务实现类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Service
public class DepartmentService extends BaseService {
	
	@Autowired
	private DepartmentDao departmentDao;
	@Autowired
	private DepartmentUserDao departmentUserDao;
	
	private static final String TENANT_DEPART_CODE_FORMAT = "tenant_%s";
	
	private String[] calc(DepartmentEntity depart, Map<String, DepartmentEntity> departMap) {
		if(depart == null){
			return null;
		}
		if(StringUtil.isNotBlank(depart.getFullName()) && StringUtil.isNotBlank(depart.getFullCode())){
			return new String[]{depart.getFullCode(), depart.getFullName()};
		}
		if(StringUtil.isBlank(depart.getParentCode())){
			depart.setFullCode(depart.getCode());
			depart.setFullName(depart.getName());
		}else{
			String[] pFull = calc(departMap.get(depart.getParentCode()), departMap);
			if(pFull == null){
				depart.setFullCode(depart.getCode());
				depart.setFullName(depart.getName());
			}else{
				depart.setFullCode(pFull[0] + "." + depart.getCode());
				depart.setFullName(pFull[1] + " / " + depart.getName());
			}
		}
		return new String[]{depart.getFullCode(), depart.getFullName()};
	}
	
	public void resetCache(String tenantId){
		RedisUtil.delete(RedisConstant.getDepartKey(tenantId));
		getAllDepartment(tenantId);
	}
	
	public List<DepartmentEntity> getAllDepartment(List<String> tenantIdList){
		List<DepartmentEntity> list = new ArrayList<>();
		for (String tenantId : tenantIdList) {
			list.addAll(getAllDepartment(tenantId));
		}
		return list;
	}
	
	public List<DepartmentEntity> getAllDepartment(String tenantId){
		//先从redis缓存中获取
		String key = RedisConstant.getDepartKey(tenantId);
		String value = RedisUtil.opsForValue().get(key);
		if(StringUtil.isNotBlank(value)){
			return JSONObject.parseArray(value, DepartmentEntity.class);
		}
		List<DepartmentEntity> list = departmentDao.listByTenantId(tenantId);
		if(CollectionUtil.isEmpty(list)){
			return new ArrayList<>();
		}
		Map<String, DepartmentEntity> departMap = CollectionUtil.getMapByProperty(list, "code");
		for (DepartmentEntity entity : list) {
			calc(entity, departMap);
		}
		RedisUtil.opsForValue().set(key, JSON.toJSONString(list));
		return list;
	}
	
	public Map<String, DepartmentEntity> getAllDepartMap(String tenantId){
		return CollectionUtil.getMapByProperty(getAllDepartment(tenantId), "code");
	}
	
	public DepartmentEntity findById(String id) {
		
		return departmentDao.find(new DepartmentVo().id(id).setTenantId(TokenUtil.getTenantId()));
	}
	
	public String checkUnique(DepartmentVo departmentVo) {
		
		return departmentDao.checkUnique(departmentVo);
	}

	/**
	 * 单条记录查询
	 * @param departmentVo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public DepartmentEntity find(DepartmentVo departmentVo) {
		
		DepartmentEntity entity = departmentDao.find(departmentVo);
		if(entity != null){
			List<DepartmentEntity> list = getAllDepartment(departmentVo.getTenantId());
			for (DepartmentEntity d : list) {
				if(StringUtil.equals(entity.getCode(), d.getCode())){
					entity.setFullCode(d.getFullCode());
					entity.setFullName(d.getFullName());
					entity.setParentName(d.getFullName().replace(" / "+entity.getName(), ""));
				}
			}
		}
		
		return entity;
	}
	
	/**
	 * 带分页的查询
	 * @param departmentVo vo查询对象
	 * @return PageRes分页对象
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public PageRes<DepartmentEntity> listByPage(DepartmentVo departmentVo) {
		
		return departmentDao.listByPage(departmentVo, departmentVo.getPage());
	}
	
	public List<DepartmentEntity> list(DepartmentVo departmentVo) {
		return departmentDao.list(departmentVo);
	}
	
	/**
	 * 不带带分页的查询
	 * @param departmentVo vo查询对象
	 * @return 
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	public List<DepartmentEntity> listAll(DepartmentVo departmentVo) {
		
		return getAllDepartment(departmentVo.getTenantId());
	}

	public List<DepartmentEntity> listTree(DepartmentVo departmentVo) {
		List<DepartmentEntity> list = getAllDepartment(departmentVo.getTenantId());
		
		if(departmentVo != null && StringUtil.isNotBlank(departmentVo.getFullCode())){
			for (DepartmentEntity d : list) {
				if(d.getFullCode() != null && d.getFullCode().startsWith(departmentVo.getFullCode())){
					d.setDisabled(true);
				}
			}
		}
		Map<String, DepartmentEntity> dptMap = CollectionUtil.getMapByProperty(list, "code");
		DepartmentEntity pdpt = null;
		List<DepartmentEntity> result = new ArrayList<>();
		for (DepartmentEntity dpt : list) {
			if(StringUtil.isBlank(dpt.getParentCode())){
				result.add(dpt);
				continue;
			}
			pdpt = dptMap.get(dpt.getParentCode());
			if(pdpt == null){
				logger.warn("数据异常，该部门没有上级部门，请检查数据, code:{}, name:{}", dpt.getCode(), dpt.getName());
				continue;
			}
			pdpt.addChild(dpt);
		}
		
		return result;
	}
	
	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public DepartmentEntity insertTenantDepart(DepartmentEntity entity) {
		entity.setCode(String.format(TENANT_DEPART_CODE_FORMAT, UUIDUtils.genShortUuid()));
		int n = departmentDao.insert(entity);
		resetCache(entity.getTenantId());
		return n > 0 ? entity : null;
	}

	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public DepartmentEntity insert(DepartmentEntity entity) {
		int n = departmentDao.insert(entity);
		resetCache(entity.getTenantId());
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(DepartmentEntity entity) {

		int n = departmentDao.update(entity);
		if(n > 0){
			resetCache(entity.getTenantId());
		}
		return n;
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(DepartmentVo departmentVo) {
		
		DepartmentEntity d = findById(departmentVo.getId());
		if(d == null){
			return 0;
		}
		
		DepartmentVo v = new DepartmentVo();
		v.setParentCode(d.getCode());
		v.setParentCode(d.getTenantId());
		List<DepartmentEntity> tmplist = departmentDao.list(v);
		if(CollectionUtil.isNotEmpty(tmplist)){
			logger.error("该机构下有子机构，不可删除，departCode:{}", d.getCode());
			throw new BusinessException("该机构下有子机构，不可删除");
		}
		
		DepartmentUserVo v1 = new DepartmentUserVo();
		v1.setTenantId(departmentVo.getTenantId());
		v1.setDepartmentCode(d.getCode());
		List<DepartmentUserEntity> list = departmentUserDao.list(v1);
		if(CollectionUtil.isNotEmpty(list)){
			logger.error("该部门仍有人员存在，不可删除，departCode:{}", d.getCode());
			throw new BusinessException("该部门仍有人员存在，不可删除");
		}
		int n = departmentDao.deleteLogic(departmentVo);
		if(n > 0){
			resetCache(departmentVo.getTenantId());
		}
		return n;
	}

}
