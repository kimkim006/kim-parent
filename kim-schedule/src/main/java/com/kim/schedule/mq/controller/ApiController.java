package com.kim.schedule.mq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.common.base.BaseController;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.util.HttpServletUtil;
import com.kim.common.util.StringUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.schedule.common.CommonConstant;
import com.kim.schedule.common.ScheduleModule;
import com.kim.schedule.common.ScheduleOperation;
import com.kim.schedule.mq.service.InputDataParseService;

/**
 * 定时调度任务表控制类
 * @date 2018-8-21 10:39:53
 * @author bo.liu01
 */
@OperateLog(module = ScheduleModule.API)
@Controller
@RequestMapping("api/tape")
public class ApiController extends BaseController {
	
	@Autowired
	private InputDataParseService inputDataParseService;
	   
	@OperateLog(oper= ScheduleOperation.API_SUMMARY, parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="summary", method=RequestMethod.POST)
	public ResultResponse summary(){
		String bodyString = HttpServletUtil.getParamterBodyString();
		if(StringUtil.isBlank(bodyString)){
			logger.error("接收到的body参数为空");
			return new MsgResponse().msg("未传任何body参数!");
		}
		return inputDataParseService.parse(HttpServletUtil.getParamterBodyString(), 
				CommonConstant.MQ_INTERFACE_SUMMARY);
	}
	
	@OperateLog(oper= ScheduleOperation.API_TAPE_SUMMARY, parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="tapeSummary", method=RequestMethod.POST)
	public ResultResponse tapeSummary(){
		String bodyString = HttpServletUtil.getParamterBodyString();
		if(StringUtil.isBlank(bodyString)){
			logger.error("接收到的body参数为空");
			return new MsgResponse().msg("未传任何body参数!");
		}
		return inputDataParseService.parse(HttpServletUtil.getParamterBodyString(), 
				CommonConstant.MQ_INTERFACE_TAPE_SUMMARY);
	}
	
}