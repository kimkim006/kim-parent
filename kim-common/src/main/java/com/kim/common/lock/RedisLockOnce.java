package com.kim.common.lock;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.kim.common.redis.RedisUtil;
import com.kim.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.kim.common.constant.RedisConstant;
import com.kim.common.context.ContextHolder;

public class RedisLockOnce {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** 原始锁标识 */
	private String origKey;
	/** 锁标识 */
	private String lockKey;
	/** 当前线程或者应用请求id */
	private String requestId;
	/** 超时时间 */
	private long expireTime = 60L * 3;
	
	public RedisLockOnce(String origKey) {
		this(origKey, ContextHolder.getTraceId());
	}
	
	public RedisLockOnce(String origKey, long expireTime) {
		this(origKey, ContextHolder.getTraceId());
		this.expireTime = expireTime;
	}
	
	public RedisLockOnce(String origKey, String requestId) {
		this.origKey = origKey;
		this.lockKey = RedisConstant.getLockKey(origKey);
		this.requestId = requestId;
	}
	
	public RedisLockOnce(String origKey, String requestId, long expireTime) {
		this(origKey, requestId);
		this.expireTime = expireTime;
	}
	
	/**
	 * 返回当前锁的状态
	 * @return true锁占用状态, false已释放或已过期
	 */
	public boolean getLockStatus(){
		String value = RedisUtil.opsForValue().get(lockKey);
		if(StringUtil.isBlank(value)){
			return false;
		}
		return requestId.equals(value);
	}

	/**
	 * 尝试获取redis分布式锁
	 * 
	 * @param lockKey 锁标志
	 * @param requestId 请求标识 
	 * @param expireTime 超期时间，单位:s
	 * @return 是否获取成功
	 */
	public boolean lock() {
		
		logger.info("开始尝试获取分布式锁, lockKey:{}, requestId:{}, expireTime:{}s", 
				lockKey, requestId, expireTime);
		
		RedisCallback<String> callback = connection ->{
			StringRedisSerializer serializer = new StringRedisSerializer();
			byte[] keyByte = serializer.serialize(lockKey);
			connection.set(keyByte, serializer.serialize(requestId), 
					Expiration.from(expireTime, TimeUnit.SECONDS) , SetOption.SET_IF_ABSENT);
			String newVal = serializer.deserialize(connection.get(keyByte));
			return requestId.equals(newVal)? requestId: null;
		};
		
		//尝试获取锁
		String result = RedisUtil.getRedisTemplate().execute(callback);
		
		logger.info("获取分布式锁结果:{}, lockKey:{}, requestId:{}, expireTime:{}s", 
				result != null, lockKey, requestId, expireTime);
		return result != null;
	}
	
	/**
	 * 尝试更新锁的有效时间，该方法是覆盖式
	 * @param expireTime 有效时间，单位:秒
	 * @return true设置成功, false该锁已失效或已被释放
	 */
	public boolean expire(long expireTime) {
		if(!getLockStatus()){
    		return false;
    	}
		
		Boolean flag = RedisUtil.expire(lockKey, expireTime, TimeUnit.SECONDS);
		if(flag != null && flag){
			this.expireTime = expireTime;
			return true;
		}
		return false;
	}
	
	/**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean unLock() {
    	
    	if(!getLockStatus()){
    		return false;
    	}

    	logger.info("开始释放分布式锁, lockKey:{}, requestId:{}, expireTime:{}s", 
				lockKey, requestId, expireTime);
    	
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(Long.class);
        
        long res = RedisUtil.getRedisTemplate().execute(redisScript, 
        		Collections.singletonList(lockKey), requestId);
        logger.info("释放分布式锁结果:{}, lockKey:{}, requestId:{}, expireTime:{}s", 
        		res > 0L, lockKey, requestId, expireTime);
        return res > 0L;
    }

	public String getOrigKey() {
		return origKey;
	}

	public String getLockKey() {
		return lockKey;
	}

	public String getRequestId() {
		return requestId;
	}

	/**
	 * 返回设置的有效时长, 单位:秒
	 * @return
	 */
	public long getExpireTime() {
		return expireTime;
	}
	
	/**
	 * 返回剩下的有效时长, 单位:秒
	 * @return 如果锁已释放返回-1, 否则返回剩余的有效时长
	 */
	public long getLeftExpireTime() {
		if(!getLockStatus()){
    		return -1;
    	}
		Long expTime = RedisUtil.getExpire(lockKey);
		return expTime == null?-1:expTime;
	}

}
