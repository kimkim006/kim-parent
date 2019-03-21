package com.kim.base.entity;

import com.kim.log.entity.Resource;

/**
 * 资源表实体类
 * @date 2017-11-10 17:57:35
 * @author bo.liu01
 */
public class ResourceEntity extends Resource {
	
	private static final long serialVersionUID = 1L; 
	
	private String moduleName;//模块名称

    public ResourceEntity(){
    }

    public ResourceEntity(Resource resource){
        this.moduleCode = resource.getModuleCode();
        this.method = resource.getMethod();
        this.code = resource.getCode();
        this.name = resource.getName();
        this.url = resource.getUrl();
        this.atvFlag = resource.getAtvFlag();
        this.operName = resource.getOperName();
        this.operUser = resource.getOperUser();
        this.operTime = resource.getOperTime();
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

}