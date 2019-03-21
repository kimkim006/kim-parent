package com.kim.admin.dao;

import java.util.List;

import com.kim.admin.entity.MenuTemplateEntity;
import com.kim.admin.vo.MenuTemplateVo;
import org.springframework.stereotype.Repository;

import com.kim.admin.entity.Template2MenuEntity;
import com.kim.common.base.BaseDao;

 /**
 * 租户菜单模板表数据接口类
 * @date 2018-8-2 15:08:10
 * @author bo.liu01
 */
@Repository
public interface MenuTemplateDao extends BaseDao<MenuTemplateEntity, MenuTemplateVo>{
	
	int batchInsertTempMenu(List<Template2MenuEntity> list);

	int deleteTempMenuLogic(MenuTemplateVo vo);

}