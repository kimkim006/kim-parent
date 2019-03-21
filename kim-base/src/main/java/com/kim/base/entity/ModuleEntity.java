package com.kim.base.entity;

import com.kim.log.entity.Module;

/**
 * 模块表实体类
 * @date 2017-11-10 17:57:36
 * @author bo.liu01
 */
public class ModuleEntity extends Module {
	
	private static final long serialVersionUID = 1L;

	public ModuleEntity(){
	}

	public ModuleEntity(Module module){
		this.className = module.getClassName();
		this.code = module.getCode();
		this.name = module.getName();
		this.url = module.getUrl();
		this.atvFlag = module.getAtvFlag();
		this.operName = module.getOperName();
        this.operUser = module.getOperUser();
        this.operTime = module.getOperTime();
	}




}