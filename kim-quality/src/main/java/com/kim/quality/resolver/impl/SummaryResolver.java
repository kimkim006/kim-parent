package com.kim.quality.resolver.impl;

import com.kim.quality.business.vo.TapeVo;
import com.kim.quality.resolver.entity.ResolverParam;
import org.springframework.stereotype.Component;

import com.kim.quality.resolver.RuleResolverConstant;
import com.kim.quality.resolver.entity.SummaryParam;
import com.kim.quality.setting.entity.SampleRuleDetailEntity;
import com.kim.quality.setting.entity.SampleRuleEntity;

@Component
public class SummaryResolver extends RuleDetailResolver<SummaryParam> {

	@Override
	public String getName() {
		return RuleResolverConstant.SUMMARY_NAME;
	}

	@Override
	public String getCode() {
		return RuleResolverConstant.SUMMARY;
	}

	@Override
	protected Class<SummaryParam> getParamClass() {
		return SummaryParam.class;
	}

	@Override
	protected void setParam(TapeVo vo, ResolverParam param, SampleRuleEntity rule, SampleRuleDetailEntity detail) {
		SummaryParam summaryParam = (SummaryParam)param;
		vo.setTypeCode(summaryParam.getTypeCode());
		vo.setFirstCode(summaryParam.getFirstCode());
		vo.setSecondCode(summaryParam.getSecondCode());
		vo.setThirdCode(summaryParam.getThirdCode());
		vo.setForthCode(summaryParam.getForthCode());
	}

}
