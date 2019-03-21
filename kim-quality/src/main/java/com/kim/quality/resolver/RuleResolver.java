package com.kim.quality.resolver;

import java.util.List;

import com.kim.quality.business.entity.SampleEntity;
import com.kim.quality.business.entity.SampleTapeEntity;
import com.kim.quality.setting.entity.SampleRuleDetailEntity;
import com.kim.quality.setting.entity.SampleRuleEntity;

/**
 * 规则解析器
 * @author bo.liu01
 *
 */
public interface RuleResolver {
	
	/**
	 * 获取规则解析器的名字
	 * @return
	 */
	String getName();
	
	/**
	 * 获取规则解析器的编码
	 * @return
	 */
	String getCode();
	
	/**
	 * 抽取数据方法
	 * @return
	 */
	List<SampleTapeEntity> extract(SampleEntity sample, SampleRuleEntity rule, SampleRuleDetailEntity detail);
	

}
