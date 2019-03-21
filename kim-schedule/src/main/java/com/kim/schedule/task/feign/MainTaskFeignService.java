package com.kim.schedule.task.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "icm-quality")
public interface MainTaskFeignService {

	@RequestMapping(value = "quality/appeal/calcLast", method = RequestMethod.GET)
	Object appealCalcLast(@RequestParam Map<String, String> param);
	
	@RequestMapping(value = "quality/approval/calcLast", method = RequestMethod.GET)
	Object approvalCalcLast(@RequestParam Map<String, String> param);
	
	@RequestMapping(value = "quality/evaluation/calcLast", method = RequestMethod.GET)
	Object evaluationCalcLast(@RequestParam Map<String, String> param);
}
