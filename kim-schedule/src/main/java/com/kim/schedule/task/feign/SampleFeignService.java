package com.kim.schedule.task.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "icm-quality")
public interface SampleFeignService {
	
	@RequestMapping(value = "quality/sample/executeSystem", method = RequestMethod.GET)
	Object extractBySystem(@RequestParam Map<String, String> param);

}
