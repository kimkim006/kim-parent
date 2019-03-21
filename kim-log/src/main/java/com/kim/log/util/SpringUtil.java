package com.kim.log.util;

import java.util.Map;

import com.kim.log.annotation.OperateLog;
import com.kim.log.entity.Module;
import com.kim.log.entity.ModuleResEntry;
import com.kim.log.entity.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.kim.common.base.BaseEntity;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;

/**
 * Created by bo.liu01 on 2017/11/13.
 */
public class SpringUtil {

    private final static Logger logger = LoggerFactory.getLogger(SpringUtil.class);

    public static ModuleResEntry reflushResource(Map<RequestMappingInfo, HandlerMethod> handlerMethods){

        ModuleResEntry moduleRes = new ModuleResEntry();
        String operUser = TokenUtil.getUsername();
        String operName = TokenUtil.getCurrentName();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
//            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
//            PatternsRequestCondition prc = requestMappingInfo.getPatternsCondition();
//            Set<String> patterns = prc.getPatterns();
//            for (String uStr : patterns) {
//                //resList.add(uStr);
//            }
            Class<?> beanType = handlerMethod.getBeanType();
            Module module = moduleRes.getModule(beanType.getName());
            OperateLog log = beanType.getAnnotation(OperateLog.class);
            if(log == null){
                logger.warn("未启用模块管理，请添加OperateLog注解，beanType:{}", beanType.getName());
                continue;
            }
            RequestMapping requestMapping;
            if(module == null){
                module = new Module();
                module.setAtvFlag(BaseEntity.ATV_FLAG_NO);
                module.setOperUser(operUser);
                module.setOperName(operName);
                module.setClassName(beanType.getName());
                requestMapping = beanType.getAnnotation(RequestMapping.class);

                if(requestMapping == null || requestMapping.value() == null
                        || requestMapping.value().length == 0 || StringUtil.isBlank(requestMapping.value()[0])){
                    module.setCode(beanType.getSimpleName());
                }else{
                    module.setCode(requestMapping.value()[0]);
                }
                module.setName(log.module());
                module.setUrl(requestMapping != null ? requestMapping.value(): null);
                moduleRes.addModule(module);//添加
            }
            log = handlerMethod.getMethodAnnotation(OperateLog.class);
            if(log == null){
                logger.warn("未启用模块管理，请添加OperateLog注解，beanType:{}, method:{}", beanType.getName(), handlerMethod.getMethod().getName());
                continue;
            }
            requestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
            Resource resource = new Resource();
            resource.setAtvFlag(BaseEntity.ATV_FLAG_NO);
            resource.setOperUser(operUser);
            resource.setOperName(operName);
            resource.setClassMethod(handlerMethod.getMethod().getName());
            if(requestMapping.value().length == 0){
                resource.setCode(String.format("%s.%s", module.getCode(), handlerMethod.getMethod().getName()));
            }else{
                resource.setCode(String.format("%s.%s", module.getCode(), requestMapping.value()[0]));
            }
            resource.setName(log.oper());
            resource.setMethod(requestMapping.method());
            resource.setUrl(requestMapping.value());
            resource.setUrl(module.getUrl() + resource.getUrl());
            resource.setModuleCode(module.getCode());
            moduleRes.addResources(resource);
        }
        return moduleRes;
    }
}
