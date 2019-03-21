package com.kim.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by bo.liu01 on 2017/11/23.
 */
public class Env2BeanUtil {

    private static final Logger logger = LoggerFactory.getLogger(Env2BeanUtil.class);

    public static <T> T transfer(Environment env, T bean, String prefix){

        prefix = StringUtil.isNotBlank(prefix) ? prefix + "." : "";

        List<Field> fields = BeanUtil.getBusinessFields(bean.getClass());
        boolean acc;
        String val;
        for(Field fd:fields){
            val = env.getProperty(prefix + fd.getName());
            if(val != null){
                acc = fd.isAccessible();
                fd.setAccessible(true);
                try {
                    fd.set(bean, val);
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(), e);
                }
                fd.setAccessible(acc);
            }
        }
        return bean;
    }
}
