package com.kim.common.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kim.common.constant.CommonConstants;
import com.kim.common.util.UUIDUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author bo.liu01
 *
 */
@Component
public class ContextHolder implements ApplicationContextAware{

    protected static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();
    
    private static ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	ContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }
    
    private static void assertApplicationContext() {
        if (applicationContext == null) {
    		throw new RuntimeException("applicaitonContext属性为null,请检查是否注入了ContextHolder!");
        }
    }

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(10);
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key){
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>(10);
            threadLocal.set(map);
        }
        return map.get(key);
    }
    
    public static HttpServletRequest getRequest() {
		return (HttpServletRequest) get("request");
	}
    
    public static HttpServletResponse getResponse() {
    	return (HttpServletResponse) get("response");
    }

    public static void initContext(HttpServletRequest request, ServletResponse response){
        set("request", request);
        set("response", response);
    }
    
    public static void clearCurrentContext(){
        threadLocal.remove();
    }
    
    public static void put(String key, Object value){
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }
    
    public static String getTraceId(){
        Object value = get(CommonConstants.CONTEXT_TRACE_ID);
        if(value == null){
        	value = UUIDUtils.getUuid();
            put(CommonConstants.CONTEXT_TRACE_ID, value);
        }
        return returnObjectValue(value);
    }

    protected static String returnObjectValue(Object value) {
        return value==null?null:value.toString();
    }

    public static void remove(){
        threadLocal.remove();
    }

}
