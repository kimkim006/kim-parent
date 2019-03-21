package com.kim.common.redis;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.kim.common.util.HttpServletUtil;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

public class RedisUtil {
	
	private static volatile StringRedisTemplate stringRedisTemplate;
	private static final Object lock = new Object();
	
	public static StringRedisTemplate getRedisTemplate(){
		if(stringRedisTemplate == null){
			synchronized (lock) {
				if(stringRedisTemplate == null){
					stringRedisTemplate = HttpServletUtil.getBean(StringRedisTemplate.class);
				}
			}
		}
		return stringRedisTemplate;
	}
	
	public static ValueOperations<String, String> opsForValue(){
		return getRedisTemplate().opsForValue();
	}
	
	public static ListOperations<String, String> opsForList(){
		return getRedisTemplate().opsForList();
	}
	
	public static SetOperations<String, String> opsForSet(){
		return getRedisTemplate().opsForSet();
	}
	
	public static ZSetOperations<String, String> opsForZSet(){
		return getRedisTemplate().opsForZSet();
	}
	
	public static HashOperations<String, Object, Object> opsForHash(){
		return getRedisTemplate().opsForHash();
	}
	
	public static Boolean expire(String key, long timeout, TimeUnit unit){
		return getRedisTemplate().expire(key, timeout, unit);
	}
	
	public static Long getExpire(String key){
		return getRedisTemplate().getExpire(key);
	}
	
	public static Long getExpire(String key, TimeUnit timeUnit){
		return getRedisTemplate().getExpire(key, timeUnit);
	}
	
	public static Boolean hasKey(String key){
		return getRedisTemplate().hasKey(key);
	}
	
	public static Set<String> keys(String pattern){
		return getRedisTemplate().keys(pattern);
	}
	
	public static void delete(String key){
		getRedisTemplate().delete(key);
	}
	
	public static void delete(Collection<String> keys){
		getRedisTemplate().delete(keys);
	}
	
}
