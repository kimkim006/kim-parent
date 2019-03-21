package com.kim.quality.business.controller;

import com.kim.quality.business.service.AllocateDetailService;
import com.kim.quality.business.service.AllocateService;
import com.kim.quality.business.vo.AllocateVo;
import com.kim.quality.common.QualityModule;
import com.kim.quality.common.QualityOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.response.TableResponse;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.quality.business.vo.AllocateDetailVo;

/**
 * 质检任务分配记录表控制类
 * @date 2018-9-10 10:10:10
 * @author bo.liu01
 */
@OperateLog(module = QualityModule.ALLOCATE)
@Controller
@RequestMapping("allocate")
public class AllocateController extends BaseController {
	
	@Autowired
	private AllocateService allocateService;
	@Autowired
	private AllocateDetailService allocateDetailService;

	/**
	 * 分页查询
	 * @date 2018-9-10 10:10:10
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(AllocateVo vo){
		
		return new TableResponse(allocateService.listByPage(vo));
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2018-9-10 10:10:10
	 * @author bo.liu01
	 */
	private String checkParam(AllocateVo vo) {
		
		return null;
	}
	
	/**
	 * 分配任务
	 * @date 2018-9-10 10:10:10
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= QualityOperation.ALLOCATE_EXE, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="exe", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse exe(@RequestBody AllocateVo vo) {

		String checkRes = checkParam(vo);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = allocateService.exe(vo);
		return new ObjectResponse(true).data(n);
	}
	/**
	 * @desc: 分页查询详情列表
	 * @param: [vo]
	 * @return: com.kim.common.response.ResultResponse
	 * @auther: yonghui.wu
	 * @date: 2018/9/12 13:57
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="detailListByPage", method=RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse detailListByPage(AllocateDetailVo vo){

		return new TableResponse(allocateDetailService.listByPage(vo));
	}
	
}