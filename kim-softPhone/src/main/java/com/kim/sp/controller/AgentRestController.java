package com.kim.sp.controller;

import com.kim.sp.entity.AgentRestEntity;
import com.kim.sp.service.AgentRestService;
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
import com.kim.common.util.VerifyUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.sp.common.SoftPhoneModule;
import com.kim.sp.common.SoftPhoneOperation;
import com.kim.sp.vo.AgentRestVo;

/**
 * 坐席小休记录表控制类
 * @date 2019-3-13 10:14:40
 * @author liubo
 */
@OperateLog(module = SoftPhoneModule.AGENT_REST)
@Controller
@RequestMapping("agentRest")
public class AgentRestController extends BaseController {
	
	@Autowired
	private AgentRestService agentRestService;
	   
	/**
	 * 分页查询
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	//@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(AgentRestVo vo){
		
		return new TableResponse(agentRestService.listByPage(vo));
	}
	
	/**
	 * 不带分页的查询（此方法使用时请注意性能）
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	/* 
	@OperateLog(oper= Operation.QUERY, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="list", method=RequestMethod.GET)
	public ResultResponse list(AgentRestVo vo){
		
		return new ObjectResponse(true).data(agentRestService.list(vo));
	}
	*/
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	//@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(AgentRestVo vo){

		AgentRestEntity entity = agentRestService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	private String checkAddParam(AgentRestEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 新增记录
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 * @return
	 */
	@OperateLog(oper= SoftPhoneOperation.REST_START, operType = OperationType.INSERT, logOperation=false,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="restStart", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse rest(@RequestBody AgentRestEntity entity) {

		VerifyUtil.verify(checkAddParam(entity), SoftPhoneOperation.REST_START);
		
		entity = agentRestService.restStart(entity);
		return new MsgResponse().rel(entity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	private String checkParam(AgentRestEntity entity) {
		
		return null;
	}
	
	/**
	 * 修改时的参数校验
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	private String checkUpdateParam(AgentRestEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 修改记录
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 * @return
	 */
	@OperateLog(oper= SoftPhoneOperation.REST_END, operType = OperationType.UPDATE, logOperation=false,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="restEnd", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse restEnd(@RequestBody AgentRestEntity entity) {

		VerifyUtil.verify(checkUpdateParam(entity), SoftPhoneOperation.REST_END);
		int n = agentRestService.restEnd(entity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2019-3-13 10:14:40
	 * @author liubo
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	//@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody AgentRestVo vo) {

		int n = agentRestService.delete(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
}