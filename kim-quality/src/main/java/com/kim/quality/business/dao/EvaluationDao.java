package com.kim.quality.business.dao;

import java.util.List;
import java.util.Map;

import com.kim.quality.business.entity.EvaluationEntity;
import com.kim.quality.business.vo.EvaluationVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 质检评分表数据接口类
 * @date 2018-9-17 15:43:36
 * @author jianming.chen
 */
@Repository
public interface EvaluationDao extends BaseDao<EvaluationEntity, EvaluationVo>{
	
	List<EvaluationEntity> listKey(EvaluationVo vo);
	
	int clearPre(EvaluationVo vo);

	List<String> listLast(EvaluationVo vo);

	int updateLastById(Map<String, Object> param);

}