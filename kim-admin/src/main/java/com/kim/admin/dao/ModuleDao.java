package com.kim.admin.dao;

import com.kim.admin.vo.ModuleVo;
import org.springframework.stereotype.Repository;

import com.kim.base.entity.ModuleEntity;
import com.kim.common.base.BaseDao;

 /**
 * 模块表数据接口类
 * @date 2017-11-13 14:14:20
 * @author bo.liu01
 */
@Repository
public interface ModuleDao extends BaseDao<ModuleEntity, ModuleVo>{

}