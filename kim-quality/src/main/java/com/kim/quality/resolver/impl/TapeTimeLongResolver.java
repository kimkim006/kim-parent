package com.kim.quality.resolver.impl;

import com.kim.quality.business.vo.TapeVo;
import com.kim.quality.resolver.entity.ResolverParam;
import com.kim.quality.setting.entity.SampleRuleEntity;
import org.springframework.stereotype.Component;

import com.kim.quality.resolver.RuleResolverConstant;
import com.kim.quality.resolver.entity.TapeTimeLongParam;
import com.kim.quality.setting.entity.SampleRuleDetailEntity;

@Component
public class TapeTimeLongResolver extends RuleDetailResolver<TapeTimeLongParam> {
	
	@Override
	public String getName() {
		return RuleResolverConstant.TAPETIME_LONG_NAME;
	}
	
	@Override
	public String getCode() {
		return RuleResolverConstant.TAPETIME_LONG;
	}
	
	@Override
	protected Class<TapeTimeLongParam> getParamClass() {
		return TapeTimeLongParam.class;
	}
	
	@Override
	protected void setParam(TapeVo vo, ResolverParam param, SampleRuleEntity rule, SampleRuleDetailEntity detail) {
		TapeTimeLongParam timeLongParam = (TapeTimeLongParam)param;
		vo.setRecordTimeStart(timeLongParam.getStart());
		vo.setRecordTimeEnd(timeLongParam.getEnd());
	}
	
}
