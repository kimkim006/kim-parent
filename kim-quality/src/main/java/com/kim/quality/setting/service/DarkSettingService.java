package com.kim.quality.setting.service;

import java.util.Arrays;
import java.util.List;

import com.kim.quality.setting.dao.DarkSettingDao;
import com.kim.quality.setting.entity.DarkSettingEntity;
import com.kim.quality.setting.vo.DarkSettingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;

/**
 * 抽检小黑屋表服务实现类
 * @date 2018-11-21 17:34:21
 * @author bo.liu01
 */
@Service
public class DarkSettingService extends BaseService {
	
	@Autowired
	private DarkSettingDao darkSettingDao;

	/**
	 * 单条记录查询
	 * @param id id
	 * @return 单个实体对象，若没有则null
	 * @date 2018-11-21 17:34:21
	 * @author bo.liu01
	 */
	public DarkSettingEntity findById(String id) {
	
		return darkSettingDao.find(new DarkSettingVo().id(id).tenantId(TokenUtil.getTenantId()));
	}
	
	/**
	 * 单条记录查询
	 * @param vo vo查询对象
	 * @return 单个实体对象，若没有则null
	 * @date 2018-11-21 17:34:21
	 * @author bo.liu01
	 */
	public DarkSettingEntity find(DarkSettingVo vo) {
	
		return darkSettingDao.find(vo);
	}
	
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-11-21 17:34:21
	 * @author bo.liu01
	 */
	public PageRes<DarkSettingEntity> listByPage(DarkSettingVo vo) {
		
		return darkSettingDao.listByPage(vo, vo.getPage());
	}
	
	public List<String> listDarkList(String date, String tenantId) {
		DarkSettingVo vo = new DarkSettingVo().tenantId(tenantId);
		vo.setDate(date);
		vo.setActive(DarkSettingEntity.ACTIVE_YES);
		List<DarkSettingEntity> list = darkSettingDao.list(vo);
		return CollectionUtil.getPropertyList(list, DarkSettingEntity::getUsername);
	}
	
	/**
	 * 不带带分页的查询
	 * @param vo vo查询对象
	 * @return 
	 * @date 2018-11-21 17:34:21
	 * @author bo.liu01
	 */
	public List<DarkSettingEntity> list(DarkSettingVo vo) {
		
		return darkSettingDao.list(vo);
	}

	/**
	 * 新增记录
	 * @date 2018-11-21 17:34:21
	 * @author bo.liu01
	 */
	/*@Transactional(readOnly=false)
	public DarkSettingEntity insert(DarkSettingEntity entity) {
		
		List<DarkSettingEntity> list = list(new DarkSettingVo().tenantId(entity.getTenantId()));
		Map<String, DarkSettingEntity> usernameMap = CollectionUtil.getMap(list, DarkSettingEntity::getUsername);
		String[] arr = entity.getUsername().split(",");
		int n = 0;
		DarkSettingEntity tmp = null;
		for (String username : arr) {
			if(StringUtil.isNotBlank(username)){
				entity.setUsername(username);
				entity.setActive(DarkSettingEntity.ACTIVE_YES);
				tmp = usernameMap.get(username);
				if(tmp != null){
					entity.setId(tmp.getId());
					n += darkSettingDao.update(entity);
				}else{
					n += darkSettingDao.insert(entity);
					usernameMap.put(username, entity);
				}
			}
		}
		return n > 0 ? entity : null;
	}*/
	@Transactional(readOnly=false)
	public DarkSettingEntity insert(DarkSettingEntity entity) {
		String[] arr = entity.getUsername().split(",");
		int n = 0;
		for (String username : arr) {
			if(StringUtil.isNotBlank(username)){
				entity.setUsername(username);
				entity.setActive(DarkSettingEntity.ACTIVE_YES);
				n += darkSettingDao.insert(entity);
			}
		}
		return n > 0 ? entity : null;
	}

	/**
	 * 修改记录
	 * @date 2018-11-21 17:34:21
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int update(DarkSettingEntity entity) {

		return darkSettingDao.update(entity);
	}

	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-11-21 17:34:21
	 * @author bo.liu01
	 */
	@Transactional(readOnly=false)
	public int delete(DarkSettingVo vo) {

		vo.setIds(Arrays.asList(vo.getId().split(",")));
		return darkSettingDao.deleteLogic(vo);
	}
	
	@Transactional(readOnly=false)
	public int active(DarkSettingVo vo) {

		vo.setIds(Arrays.asList(vo.getId().split(",")));
		return darkSettingDao.active(vo);
	}

}
