package com.kim.quality.business.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.business.entity.EvaluationDetailEntity;
import com.kim.quality.business.vo.EvaluationDetailVo;

 /**
 * 质检评分明细表数据接口类
 * @date 2018-9-25 14:28:10
 * @author yonghui.wu
 */
@Repository
public interface EvaluationDetailDao extends BaseDao<EvaluationDetailEntity, EvaluationDetailVo>{

}