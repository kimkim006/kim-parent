package com.kim.sp.controller;

import com.kim.sp.common.SoftPhoneModule;
import com.kim.sp.common.SoftPhoneOperation;
import com.kim.sp.vo.MmtContractVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.common.base.BaseController;
import com.kim.common.response.ResultResponse;
import com.kim.common.response.TableResponse;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.sp.service.MmtIvrService;

/**
 * 工号信息表控制类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
@OperateLog(module = SoftPhoneModule.MMT_IVR)
@Controller
@RequestMapping("mmtIvr")
public class MmtIvrController extends BaseController {
	
	@Autowired
	private MmtIvrService mmtIvrService;
	   
	/**
	 * 分页查询
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@OperateLog(oper= SoftPhoneOperation.CONTRACT_LIST, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="contractList", method=RequestMethod.GET)
	public ResultResponse contractList(MmtContractVo vo){
		
		return new TableResponse(mmtIvrService.contractList(vo));
	}
	
	
}