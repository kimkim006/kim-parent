package com.kim.schedule.job.controller;

import com.kim.schedule.common.ScheduleModule;
import com.kim.schedule.common.ScheduleOperation;
import com.kim.schedule.job.entity.TaskJobEntity;
import com.kim.schedule.job.scheduler.TaskUtils;
import com.kim.schedule.job.service.TaskJobService;
import com.kim.schedule.job.vo.TaskJobVo;
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

/**
 * 定时调度任务表控制类
 * @date 2018-8-21 10:39:53
 * @author bo.liu01
 */
@OperateLog(module = ScheduleModule.TASK_JOB)
@Controller
@RequestMapping("taskJob")
public class TaskJobController extends BaseController {
	
	@Autowired
	private TaskJobService taskJobService;
	   
	/**
	 * 分页查询
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 */
	@OperateLog(oper= ScheduleOperation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(TaskJobVo vo){
		
		return new TableResponse(taskJobService.listByPage(vo));
	}
	
	/**
	 * 不带分页的查询（此方法使用时请注意性能）
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 */
	/* 
	@OperateLog(oper= Operation.QUERY, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="list", method=RequestMethod.GET)
	public ResultResponse list(TaskJobVo vo){
		
		return new ObjectResponse(true).data(taskJobService.list(vo));
	}
	*/
	
	/**
	 * 查询单条记录
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 * @param jobName 
	 */
	@OperateLog(oper=Operation.CHECK_UNIQUE, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="checkUnique", method=RequestMethod.GET)
	public ResultResponse checkUnique(String jobName){

		TaskJobVo vo = new TaskJobVo();
		vo.setJobName(jobName);
		return new ObjectResponse(true).data(taskJobService.find(vo)!= null);
	}
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(TaskJobVo vo){
		
		TaskJobEntity entity = taskJobService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 新增记录
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody TaskJobEntity entity) {

		String checkRes = TaskUtils.checkJob(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		entity = taskJobService.insert(entity);
		return new MsgResponse().rel(entity != null);
	}
	
	/**
	 * 修改记录
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.UPDATE, operType = OperationType.UPDATE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse update(@RequestBody TaskJobEntity entity) {

		String checkRes = TaskUtils.checkJob(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = taskJobService.update(entity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2018-8-21 10:39:53
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody TaskJobVo vo) {

		int n = taskJobService.delete(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	@OperateLog(oper=ScheduleOperation.ACTIVE, parameterType=ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="active", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public Object active(@RequestBody TaskJobVo vo) {

		if(StringUtil.isBlank(vo.getId())){
       		logger.error("id不能为空");
       		return new MsgResponse(MsgCode.INVALID_PARAMETER).msg("id不能为空");
       	}
    	if(vo.getActive() == null || vo.getActive() != TaskJobEntity.ACTIVE_YES ){
    		vo.setActive(TaskJobEntity.ACTIVE_NO);
    	}
    	
		int n = taskJobService.active(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 立即执行定时任务
	 * @param vo
	 * @return
	 */
	@OperateLog(oper=ScheduleOperation.EXECUTE, parameterType=ParameterType.BODY, elapsedTime=true)
   	@ResponseBody
   	@RequestMapping(value="execute", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
   	public Object execute(@RequestBody TaskJobVo vo) {

       	if(StringUtil.isBlank(vo.getId())){
       		logger.error("id不能为空");
       		return new MsgResponse(MsgCode.INVALID_PARAMETER).msg("id不能为空");
       	}
       	
   		taskJobService.execute(vo);
   		return new MsgResponse(MsgCode.SUCCESS);
   	}
}