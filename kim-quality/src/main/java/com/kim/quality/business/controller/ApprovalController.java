package com.kim.quality.business.controller;

import com.kim.quality.business.service.ApprovalService;
import com.kim.quality.business.vo.ApprovalVo;
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
import com.kim.common.util.StringUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.quality.business.entity.ApprovalEntity;

/**
 * 审批记录明细表控制类
 * @date 2018-9-15 17:21:22
 * @author jianming.chen
 */
@OperateLog(module = QualityModule.APPROVAL)
@Controller
@RequestMapping("approval")
public class ApprovalController extends BaseController {
	
	@Autowired
	private ApprovalService approvalService;
	   
	/**
	 * 分页查询
	 * @date 2018-9-15 17:21:22
	 * @author jianming.chen
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	//@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(ApprovalVo vo){
		
		return new TableResponse(approvalService.listByPage(vo));
	}
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-9-15 17:21:22
	 * @author jianming.chen
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method= RequestMethod.GET)
	public ResultResponse find(ApprovalVo vo){

		ApprovalEntity entity = approvalService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 单个审核时的参数校验
	 * @date 2018-9-15 17:21:22
	 * @author jianming.chen
	 */
	private String checkAuditParam(ApprovalEntity entity) {
		if(StringUtil.isBlank(entity.getId())){
			return "id不能为空";
		}
		return checkParam(entity);
	}
	
	/**
	 * 审核记录
	 * @date 2018-9-15 17:21:22
	 * @author jianming.chen
	 * @return
	 */
	@OperateLog(oper= Operation.UPDATE, operType = OperationType.UPDATE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="audit", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse audit(@RequestBody ApprovalEntity entity) {

		String checkRes = checkAuditParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = approvalService.audit(entity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 审核时的参数校验
	 * @date 2018-9-15 17:21:22
	 * @author jianming.chen
	 */
	private String checkParam(ApprovalEntity entity) {
		if(entity.getType() == null){
			return "审批类型不能为空";
		}
		if(StringUtil.isBlank(entity.getTaskId())){
			return "任务id不能为空";
		}
		if(StringUtil.isBlank(entity.getContent())){
			return "意见内容不能为空";
		}
		if(entity.getStatus() == null){
			return "审核意见不能为空";
		}
		return null;
	}
	
	
	/**
	 * 批量审核
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.BATCH_AUDIT, operType = OperationType.UPDATE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="auditBatch", method=RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse auditBatch(@RequestBody ApprovalEntity entity) {
		String checkRes = checkAuditBatchParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		return new ObjectResponse(true).data(approvalService.auditBatch(entity));
	}
	
	/**
	 * 批量时的参数校验
	 * @date 2018-9-15 17:21:22
	 * @author jianming.chen
	 */
	private String checkAuditBatchParam(ApprovalEntity entity) {
		
		return checkParam(entity);
	}
	
	@OperateLog(oper= QualityOperation.CALC_LAST, parameterType=ParameterType.QUERY,
			logOperation=false, injectCurntTenant=false,
			injectCurntDate=false, injectCurntUser=false)
	@ResponseBody
	@RequestMapping(value="calcLast", method=RequestMethod.GET)
	public ResultResponse calcLast(ApprovalVo vo){

		approvalService.calcLast(vo);
		return new MsgResponse().rel(true);
	}
}