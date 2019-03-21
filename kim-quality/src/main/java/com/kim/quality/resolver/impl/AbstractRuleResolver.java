package com.kim.quality.resolver.impl;

import com.kim.quality.resolver.entity.ResolverParam;
import com.kim.quality.setting.entity.SampleRuleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.exception.BusinessException;
import com.kim.common.util.StringUtil;
import com.kim.quality.resolver.RuleResolver;
import com.kim.quality.setting.entity.SampleRuleDetailEntity;

/**
 * 基础的抽取数据类
 * @author bo.liu01
 *
 */
public abstract class AbstractRuleResolver implements RuleResolver {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected <T extends ResolverParam> T parseParam(SampleRuleEntity rule, SampleRuleDetailEntity detail, Class<T> clazz) {
		if(StringUtil.isBlank(detail.getResolverParam())){
			logger.error("抽取质检任务-缺少参数, ruleId:{}, detailResolver:{}",
					rule.getId(), detail.getResolver());
			throw new BusinessException("抽检规则明细缺少参数，请检查抽检规则!");
		}
		T param;
		try {
			param = JSONObject.parseObject(detail.getResolverParam(), clazz);
		} catch (Exception e) {
			logger.error("抽取质检任务-参数解析错误, ruleId:{}, detailResolver:{}, param:{}",
					rule.getId(), detail.getResolver(), detail.getResolverParam());
			throw new BusinessException("抽检规则明细参数解析错误，请检查抽检规则!");
		}
		String res = param.checkParam();
		if(StringUtil.isNotBlank(res)){
			logger.error("抽取质检任务-参数配置错误, ruleId:{}, detailResolver:{}, param:{}, actParam:{}",
					rule.getId(), detail.getResolver(), detail.getResolverParam(), JSONObject.toJSONString(param));
			throw new BusinessException("抽检规则明细参数配置错误，请检查抽检规则!");
		}
		return param;
	}
	
}
