package com.kim.quality.business.controller;

import java.util.ArrayList;
import java.util.List;

import com.kim.quality.business.entity.MainTaskDigestInfo;
import com.kim.quality.business.enums.TaskStageEnum;
import com.kim.quality.business.vo.MainTaskVo;
import com.kim.quality.common.QualityModule;
import com.kim.quality.common.QualityOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.response.TableResponse;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.quality.business.enums.MainTaskStatusEnum;
import com.kim.quality.business.service.MainTaskService;

/**
 * 质检任务表控制类
 * @date 2018-8-28 18:24:20
 * @author bo.liu01
 */
@OperateLog(module = QualityModule.MAIN_TASK)
@Controller
@RequestMapping("mainTask")
public class MainTaskController extends BaseController {
	
	@Autowired
	private MainTaskService mainTaskService;
	
	/**
	 * 查询任务的可用状态
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= QualityOperation.MAIN_TASK_STATUS, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listStatus", method=RequestMethod.GET)
	public ResultResponse listStatus(String stage) {
		if(StringUtil.isBlank(stage)){
			return new ObjectResponse(true).data(new ArrayList<>());
		}
		TaskStageEnum taskStage = TaskStageEnum.getStageEnum(stage);
		List<JSONObject> list = new ArrayList<>(); 
		if(taskStage != null){
			list = MainTaskStatusEnum.list(taskStage.getStatus());
		}
		return new ObjectResponse(true).data(list);
	}
	
	private void setEndTime(MainTaskVo vo){
		if(StringUtil.isNotBlank(vo.getEndTime())){
			vo.setEndTime(vo.getEndTime().subSequence(0, 10)+ " 23:59:59");
		}
	}
	   
	/**
	 * 分页查询
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(MainTaskVo vo) {

		setEndTime(vo);
		return new TableResponse(mainTaskService.listByPage(vo));
	}
	
	@OperateLog(oper= Operation.DOWNLOAD, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="download", method=RequestMethod.GET)
	public ResultResponse download(MainTaskVo vo) {

		setEndTime(vo);
		return new ObjectResponse(true).data(mainTaskService.download(vo));
	}
	
	@OperateLog(oper= QualityOperation.DOWNLOAD_TASK, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="downloadTask", method=RequestMethod.GET)
	public ResultResponse downloadTask(MainTaskVo vo) {

		setEndTime(vo);
		return new ObjectResponse(true).data(mainTaskService.downloadTask(vo));
	}
	
	/**
	 * 分页查询
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= QualityOperation.SAMPLE_DETAIL, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listSampleDetail", method=RequestMethod.GET)
	public ResultResponse listSampleDetail(MainTaskVo vo) {
		
		return new TableResponse(mainTaskService.listSampleDetail(vo));
	}
	
	/**
	 * 分页查询当前用户的质检结果列表
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= QualityOperation.MAIN_TASK_RESULT, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listResult", method=RequestMethod.GET)
	public ResultResponse listResult(MainTaskVo vo) {
		
		//只查询当前坐席的结果
		vo.setAgentId(TokenUtil.getUsername());
		if(vo.getStatus() == null){
			vo.setStatusArr(CollectionUtil.array2List(TaskStageEnum.APPEAL.getStatus()));
		}
		setEndTime(vo);
		return new TableResponse(mainTaskService.listResult(vo));
	}
	
	/**
	 * 分页查询
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listApproval", method=RequestMethod.GET)
	public ResultResponse listApproval(MainTaskVo vo) {
		
		vo.setAuditor(TokenUtil.getUsername());
		if(vo.getStatus() == null){
			vo.setStatusArr(CollectionUtil.array2List(TaskStageEnum.APPROVAL.getStatus()));
		}
		setEndTime(vo);
		return new TableResponse(mainTaskService.listApproval(vo));
	}
	/**
	 * 分页查询
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listEvaluation", method=RequestMethod.GET)
	public ResultResponse listEvaluation(MainTaskVo vo) {
		vo.setCurProcesser(TokenUtil.getUsername());
		if(vo.getStatus() == null){
			vo.setStatusArr(CollectionUtil.array2List(TaskStageEnum.EVALUATION.getStatus()));
		}
		setEndTime(vo);
		return new TableResponse(mainTaskService.listEvaluation(vo));
	}
	
	/**
	 * 根据任务id查询任务的摘要信息，包括该任务的所有评分，审核申诉记录id集合
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@OperateLog(oper=QualityOperation.MAIN_TASK_INFO, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="digestInfo", method=RequestMethod.GET)
	public ResultResponse digestInfo(String taskId){

		if(StringUtil.isBlank(taskId)){
			logger.error("查询任务摘要信息时，任务id不能为空!");
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg("任务id不能为空");
		}
		MainTaskDigestInfo entity = mainTaskService.digestInfo(taskId);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
}