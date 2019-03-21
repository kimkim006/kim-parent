package com.kim.quality.resolver;

import java.util.HashMap;
import java.util.Map;

import com.kim.quality.resolver.impl.SummaryResolver;
import com.kim.quality.resolver.impl.TapeTimeLongResolver;
import com.kim.quality.resolver.impl.UserTypeResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.common.util.HttpServletUtil;

/**
 * 规则解析器注册器
 * @author bo.liu01
 *
 */
public class RuleRegister {
	
	private final static Logger logger = LoggerFactory.getLogger(RuleRegister.class);
	
	private static final Map<String, RuleResolver> ruleResolvers = new HashMap<>();
	private static final Map<String, Class<? extends RuleResolver>> resolverClassMap = new HashMap<>();
	
	public static void register(RuleResolver ruleResolver) {
		ruleResolvers.put(ruleResolver.getCode(), ruleResolver);
	}
	
	static{
		resolverClassMap.put(RuleResolverConstant.TAPETIME_LONG, TapeTimeLongResolver.class);
		resolverClassMap.put(RuleResolverConstant.USER_TYPE, UserTypeResolver.class);
		resolverClassMap.put(RuleResolverConstant.SUMMARY, SummaryResolver.class);
	}
	
	public static Map<String, RuleResolver> getRuleResolvers() {
		return ruleResolvers;
	}
	
	public static RuleResolver getRuleResolver(String code){
		RuleResolver resolver = ruleResolvers.get(code);
		if(resolver == null){
			Class<? extends RuleResolver> clazz =  resolverClassMap.get(code);
			if(clazz == null){
				logger.warn("不存在的规则解析器, code:{}", code);
				return null;
			}
			resolver = HttpServletUtil.getBean(clazz);
			if(resolver == null){
				logger.warn("不存规则解析器实例，请检查代码配置, code:{}, className:{}", code, clazz.getSimpleName());
				return null;
			}
			ruleResolvers.put(code, resolver);
		}
		return resolver;
	}

}
