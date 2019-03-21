package com.kim.admin.service;

import com.kim.admin.dao.UserOrgDao;
import com.kim.admin.entity.UserOrgEntity;
import com.kim.admin.vo.UserOrgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kim.base.service.BaseUserOrgService;
import com.kim.common.base.BaseService;
import com.kim.common.page.PageRes;

/**
 * 组织人员关系表服务实现类
 * @date 2018-9-4 14:40:25
 * @author yonghui.wu
 */
@Service
public class UserOrgService extends BaseService {
	
	@Autowired
	private UserOrgDao userOrgDao;
	@Autowired
	private BaseUserOrgService baseUserOrgService;

	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-9-4 14:40:25
	 * @author yonghui.wu
	 */
	public PageRes<UserOrgEntity> listByGroupPage(UserOrgVo vo) {

		return userOrgDao.listByGroupPage(vo, vo.getPage());
	}
	/**
	 * 带分页的查询
	 * @param vo 查询对象
	 * @return PageRes分页对象
	 * @date 2018-9-4 14:40:25
	 * @author yonghui.wu
	 */
	public PageRes<UserOrgEntity> listByUserPage(UserOrgVo vo) {

		return userOrgDao.listByUserPage(vo, vo.getPage());
	}
	/**
	 * 批量新增记录
	 * 1、删除历史配置关系
	 * 2、新增新的组织关系
	 * @date 2018-9-4 14:40:25
	 * @author yonghui.wu
	 */
	@Transactional(readOnly=false)
	public int addUserOrg(UserOrgVo vo) {
		String[] itemCodes = vo.getItemCodes().split(",");
		UserOrgEntity userOrg = new UserOrgEntity();
		userOrg.copyBaseField(vo);
		userOrg.setUpperSuperior(vo.getUpperSuperior());
		userOrg.setCodeType(UserOrgEntity.CODE_TYPE_GROUP);
		int n = 0;
		for (String itemCode : itemCodes) {
			//删除关系
			UserOrgVo delVo = new UserOrgVo().tenantId(vo.getTenantId());
			delVo.setItemCode(itemCode);
			delVo.setCodeType(UserOrgEntity.CODE_TYPE_GROUP);
			n += userOrgDao.deleteLogic(delVo);
			//新增关系
			userOrg.setItemCode(itemCode);
			n += userOrgDao.insert(userOrg);
		}
		if(n > 0){
			baseUserOrgService.reloadOrgUserCache(vo.getTenantId(), UserOrgEntity.CODE_TYPE_GROUP);
		}
		return n;
	}
	
	/**
	 * 逻辑删除记录，如有修改，请在此备注
	 * @date 2018-9-4 14:40:25
	 * @author yonghui.wu
	 */
	@Transactional(readOnly=false)
	public int delete(UserOrgVo vo) {

		int n = userOrgDao.deleteLogic(vo);
		if(n > 0){
			baseUserOrgService.reloadOrgUserCache(vo.getTenantId(), UserOrgEntity.CODE_TYPE_GROUP);
		}
		return n;
	}
}
