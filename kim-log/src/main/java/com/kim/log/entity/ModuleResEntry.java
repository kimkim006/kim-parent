package com.kim.log.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kim.common.base.BaseObject;
import com.kim.common.util.CollectionUtil;

/**
 * Created by bo.liu01 on 2017/11/13.
 */
public class ModuleResEntry extends BaseObject {
	private static final long serialVersionUID = 1L;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private List<Resource> resources = new ArrayList<>();
    private List<Module> modules = new ArrayList<>();
    private Map<String, Module> moduleClassMap = new HashMap<>();
    private Map<String, Module> moduleCodeMap = new HashMap<>();

    public void addResources(Resource res) {
        this.resources.add(res);
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
        this.moduleClassMap = CollectionUtil.getMapByProperty(modules, "className");
        this.moduleCodeMap = CollectionUtil.getMapByProperty(modules, "code");
    }

    public void addModule(Module module) {
        this.modules.add(module);
        if(moduleClassMap.containsKey(module.getClassName())){
            logger.debug("已存在的模块，module:{}", module);
            return ;
        }
        moduleClassMap.put(module.getClassName(), module);
        if(moduleCodeMap.containsKey(module.getCode())){
            moduleCodeMap.put(module.getCode()+"_"+System.currentTimeMillis(), module);
        }else{
            moduleCodeMap.put(module.getCode(), module);
        }
    }

    public Module getModule(String className) {
        return moduleClassMap.get(className);
    }

    public boolean containt(String className) {
        return moduleClassMap.containsKey(className);
    }
}
