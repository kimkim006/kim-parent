package com.kim.quality.setting.dao;

import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.setting.entity.EvaluationSettingEntity;
import com.kim.quality.setting.vo.EvaluationSettingVo;

/**
 * 质检评分选项表数据接口类
 * @date 2018-9-10 14:40:52
 * @author jianming.chen
 */
@Repository
public interface EvaluationSettingDao extends BaseDao<EvaluationSettingEntity, EvaluationSettingVo>{

}