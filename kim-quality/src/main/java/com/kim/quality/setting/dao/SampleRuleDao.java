package com.kim.quality.setting.dao;

import com.kim.quality.setting.entity.SampleRuleEntity;
import org.springframework.stereotype.Repository;

import com.kim.common.base.BaseDao;
import com.kim.quality.setting.vo.SampleRuleVo;

 /**
 * 人工抽检规则表数据接口类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
@Repository
public interface SampleRuleDao extends BaseDao<SampleRuleEntity, SampleRuleVo>{
	
	int insertAtta(SampleRuleEntity entity);
	
	int updateAtta(SampleRuleEntity entity);
	
	int deleteLogicAtta(SampleRuleVo vo);

	SampleRuleEntity findAtta(SampleRuleVo vo);

	void clearSystemActiveRule(SampleRuleVo vo);

}