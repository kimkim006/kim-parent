package com.kim.quality.setting.dao;

import com.kim.quality.setting.entity.SampleRuleDetailEntity;
import com.kim.quality.setting.vo.SampleRuleDetailVo;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;

/**
 * 人工抽检规则明细表数据接口类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
@Repository
public interface SampleRuleDetailDao extends BaseDao<SampleRuleDetailEntity, SampleRuleDetailVo>{

}