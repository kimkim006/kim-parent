package com.kim.quality.business.service;

import java.util.ArrayList;
import java.util.List;

import com.kim.quality.business.entity.SampleTapeEntity;
import com.kim.quality.resolver.RuleResolver;
import com.kim.quality.resolver.impl.SystemSampleResolver;
import com.kim.quality.setting.entity.RuleEntity;
import com.kim.quality.setting.entity.SampleRuleDetailEntity;
import com.kim.quality.setting.entity.SampleRuleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.common.exception.BusinessException;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.HttpServletUtil;
import com.kim.quality.business.entity.SampleEntity;
import com.kim.quality.resolver.RuleRegister;

public class ExtractUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ExtractUtil.class);
	
	public static List<SampleTapeEntity> extract(SampleEntity sample, SampleRuleEntity rule) {
		
		switch (rule.getSampleType()) {
		case RuleEntity.SAMPLE_TYPE_MANUAL:return extractByManual(sample, rule);
		case RuleEntity.SAMPLE_TYPE_SYSTEM:return extractBySystem(sample, rule);
		default:
			logger.error("未识别的抽检类型, sampleType:{}", rule.getSampleType());
			throw new BusinessException("未识别的抽检类型，请检查规则配置!");
		}
	}
	
	public static List<SampleTapeEntity> extractBySystem(SampleEntity sample, SampleRuleEntity rule) {
		
		RuleResolver resolver = HttpServletUtil.getBean(SystemSampleResolver.class);
		if(resolver == null){
			logger.error("系统抽检解析器不存在");
			throw new BusinessException("系统抽检解析器不存在，请检查代码配置!");
		}
		return resolver.extract(sample, rule, null);
	}
	
	public static List<SampleTapeEntity> extractByManual(SampleEntity sample, SampleRuleEntity rule) {
		List<SampleRuleDetailEntity> detailList = rule.getRuleDetail();
		List<SampleTapeEntity> tapeList = new ArrayList<>();
		List<SampleTapeEntity> tmpList;
		for (SampleRuleDetailEntity detail : detailList) {
			tmpList = extractByDetail(detail, sample, rule);
			if(CollectionUtil.isEmpty(tmpList)){
				break;
			}
			if(CollectionUtil.isEmpty(tapeList)){
				tapeList = tmpList;
			}else{
				tapeList = CollectionUtil.intersection(tapeList, tmpList, "tapeId");
			}
			if(CollectionUtil.isEmpty(tapeList)){
				break;
			}
		}
		return tapeList;
	}
	
	private static List<SampleTapeEntity> extractByDetail(SampleRuleDetailEntity detail, SampleEntity sample,
			SampleRuleEntity rule) {
		RuleResolver resolver = RuleRegister.getRuleResolver(detail.getResolver());
		if(resolver == null){
			logger.error("抽检时解析器不存在, resolver:{}", resolver);
			throw new BusinessException("解析器resolver不存在，请检查规则配置!");
		}
		
		return resolver.extract(sample, rule, detail);
	}
	
}
