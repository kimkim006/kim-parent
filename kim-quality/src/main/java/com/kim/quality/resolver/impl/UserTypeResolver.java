package com.kim.quality.resolver.impl;

import com.kim.quality.business.vo.TapeVo;
import com.kim.quality.resolver.entity.ResolverParam;
import org.springframework.stereotype.Component;

import com.kim.quality.resolver.RuleResolverConstant;
import com.kim.quality.resolver.entity.UserTypeParam;
import com.kim.quality.setting.entity.SampleRuleDetailEntity;
import com.kim.quality.setting.entity.SampleRuleEntity;

@Component
public class UserTypeResolver extends RuleDetailResolver<UserTypeParam> {

	@Override
	public String getName() {
		return RuleResolverConstant.USER_TYPE_NAME;
	}
	
	@Override
	public String getCode() {
		return RuleResolverConstant.USER_TYPE;
	}

	@Override
	protected Class<UserTypeParam> getParamClass() {
		return UserTypeParam.class;
	}

	@Override
	protected void setParam(TapeVo vo, ResolverParam param, SampleRuleEntity rule, SampleRuleDetailEntity detail) {
		UserTypeParam userTypeParam = (UserTypeParam)param;
		vo.setUserType(userTypeParam.getUserType());
	}
	

}
