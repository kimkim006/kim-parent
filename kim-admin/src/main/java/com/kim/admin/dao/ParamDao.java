package com.kim.admin.dao;

import com.kim.admin.entity.ParamEntity;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.admin.vo.ParamVo;

 /**
 * 参数配置表数据接口类
 * @date 2018-8-15 14:22:43
 * @author bo.liu01
 */
@Repository
public interface ParamDao extends BaseDao<ParamEntity, ParamVo>{

}