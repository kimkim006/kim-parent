package com.kim.admin.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.admin.entity.MenuResourceEntity;
import com.kim.admin.vo.MenuResourceVo;

 /**
 * 菜单资源表数据接口类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@Repository
public interface MenuResourceDao extends BaseDao<MenuResourceEntity, MenuResourceVo>{
	
	int deleteLogicByMenu(MenuResourceVo vo);

}