package com.kim.base.service;

import java.util.ArrayList;
import java.util.List;

import com.kim.base.config.BaseBeanConfig;
import com.kim.base.entity.ResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.kim.base.dao.BaseOperDao;
import com.kim.base.entity.ModuleEntity;
import com.kim.common.base.BaseService;
import com.kim.common.util.BatchUtil;
import com.kim.common.util.HttpServletUtil;
import com.kim.log.entity.Module;
import com.kim.log.entity.ModuleResEntry;
import com.kim.log.entity.Resource;
import com.kim.log.util.SpringUtil;

@Service
public class BaseOperService extends BaseService{
	
	@Autowired
	private BaseOperDao baseOperDao;
	@Autowired
	private BaseBeanConfig baseBeanConfig;
	
	public int reflush() {

		//通过上下文对象获取RequestMappingHandlerMapping实例对象
		RequestMappingHandlerMapping bean = HttpServletUtil.getBean(RequestMappingHandlerMapping.class);
		ModuleResEntry moduleRes = SpringUtil.reflushResource(bean.getHandlerMethods());

		List<Module> modules = moduleRes.getModules();
		List<Resource> resources = moduleRes.getResources();
		List<ModuleEntity> moduleList = transferM(modules);
		List<ResourceEntity> resourceList = transferR(resources);

		BatchUtil.batchInsert(baseOperDao, "insertModuleBatch",moduleList);
		baseOperDao.deleteAllModule(baseBeanConfig.getServiceId());
		baseOperDao.activeModule(baseBeanConfig.getServiceId());
		BatchUtil.batchInsert(baseOperDao, "insertResourceBatch", resourceList);
		baseOperDao.deleteAllResource(baseBeanConfig.getServiceId());
		baseOperDao.activeResource(baseBeanConfig.getServiceId());

		return resourceList.size();
	}
	
	private List<ModuleEntity> transferM(List<Module> modules){
		List<ModuleEntity> list = new ArrayList<>(modules.size());
		for(Module module : modules){
			ModuleEntity m = new ModuleEntity(module);
			m.setServiceId(baseBeanConfig.getServiceId());
			list.add(m);
		}
		return list;
	}

	private List<ResourceEntity> transferR(List<Resource> resources){
		List<ResourceEntity> list = new ArrayList<>(resources.size());
		for(Resource resource : resources){
			ResourceEntity r = new ResourceEntity(resource);
			r.setServiceId(baseBeanConfig.getServiceId());
			list.add(r);
		}
		return list;
	}

}
