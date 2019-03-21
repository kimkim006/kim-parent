package com.kim.common.util;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class SpringUtil {
	
	private static final PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
	
	public static boolean match(String pattern, String path) {
		
		return resourceLoader.getPathMatcher().match(pattern, path);
		
	}

}
