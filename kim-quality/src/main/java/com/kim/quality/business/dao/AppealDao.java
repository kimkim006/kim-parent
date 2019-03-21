package com.kim.quality.business.dao;

import java.util.List;
import java.util.Map;

import com.kim.quality.business.entity.AppealEntity;
import com.kim.quality.business.vo.AppealVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 申诉记录表数据接口类
 * @date 2018-9-19 10:11:34
 * @author bo.liu01
 */
@Repository
public interface AppealDao extends BaseDao<AppealEntity, AppealVo>{
	
	List<AppealEntity> listKey(AppealVo vo);

	int clearPre(AppealVo vo);

	List<String> listLast(AppealVo vo);

	int updateLastById(Map<String, Object> param);

}