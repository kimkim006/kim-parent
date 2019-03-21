package com.kim.base.controller;

import com.kim.base.common.BaseModule;
import com.kim.base.common.BaseOperation;
import com.kim.base.service.BaseOperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.common.base.BaseController;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;

@OperateLog(module = BaseModule.MODULE_RESOURCE)
@Controller
@RequestMapping("baseOper")
public class BaseOperController extends BaseController {
	
	@Autowired
	private BaseOperService baseOperService;
	/**
	 * 重新刷新资源表数据，此接口调用后会自动从代码中提取接口路径保存到数据库
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= BaseOperation.REFLUSH_RES, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="reflush", method=RequestMethod.GET)
	public ResultResponse reflush(){

		int n = baseOperService.reflush();
		return new ObjectResponse(n > 0).data(n);
	}

}
