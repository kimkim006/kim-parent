package com.kim.admin.controller;

import java.util.List;

import com.kim.admin.common.AdminModule;
import com.kim.admin.service.ModuleService;
import com.kim.admin.vo.ModuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.base.entity.ModuleEntity;
import com.kim.common.base.BaseController;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.response.TableResponse;
import com.kim.common.util.CollectionUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;

/**
 * 模块表控制类
 * @date 2017-11-13 14:14:20
 * @author bo.liu01
 */
@OperateLog(module = AdminModule.MODULE)
@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {
	
	@Autowired
	private ModuleService moduleService;
	   
	/**
	 * 分页查询
	 * @date 2017-11-13 14:14:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(ModuleVo moduleVo){
		
		return new TableResponse(moduleService.listByPage(moduleVo));
	}

	/**
	 * 不带分页的查询,适用于下拉列表
	 * @date 2017-11-13 14:14:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listForCom", method=RequestMethod.GET)
	public ResultResponse list(ModuleVo moduleVo){
		List<ModuleEntity> list = moduleService.list(moduleVo);
		return new ObjectResponse(true).data(CollectionUtil.javaList2MapList(list,
				"name", "code"));
	}
	
}