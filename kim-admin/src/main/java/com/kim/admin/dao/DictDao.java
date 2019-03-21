package com.kim.admin.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kim.admin.entity.DictEntity;
import com.kim.admin.vo.DictVo;
import com.kim.common.base.BaseDao;
import com.kim.common.page.PageRes;
import com.kim.common.page.PageVo;

 /**
 * 数据字典表数据接口类
 * @date 2018-8-15 14:22:43
 * @author bo.liu01
 */
@Repository
public interface DictDao extends BaseDao<DictEntity, DictVo>{
	
	PageRes<DictEntity> listFirstByPage(@Param("obj")DictVo vo, @Param("page")PageVo page);
	
}