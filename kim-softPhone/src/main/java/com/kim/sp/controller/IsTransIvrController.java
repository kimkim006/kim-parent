package com.kim.sp.controller;

import com.kim.sp.common.SoftPhoneModule;
import com.kim.sp.vo.IsTransIvrVo;
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
import com.kim.sp.entity.IsTransIvrEntity;
import com.kim.sp.service.IsTransIvrService;

/**
 * 转IVR信息表控制类
 * @date 2019-3-7 15:41:34
 * @author liubo
 */
@OperateLog(module = SoftPhoneModule.IS_TRANS_IVR)
@Controller
@RequestMapping("isTransIvr")
public class IsTransIvrController extends BaseController {
	
	@Autowired
	private IsTransIvrService isTransIvrService;
	   
	/**
	 * 分页查询
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	//@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(IsTransIvrVo vo){
		
		return new TableResponse(isTransIvrService.listByPage(vo));
	}
	
	/**
	 * 不带分页的查询（此方法使用时请注意性能）
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	/* 
	@OperateLog(oper= Operation.QUERY, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="list", method=RequestMethod.GET)
	public ResultResponse list(IsTransIvrVo vo){
		
		return new ObjectResponse(true).data(isTransIvrService.list(vo));
	}
	*/
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	//@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(IsTransIvrVo vo){

		IsTransIvrEntity entity = isTransIvrService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	private String checkAddParam(IsTransIvrEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 新增记录
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
//	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody IsTransIvrEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		entity = isTransIvrService.insert(entity);
		return new MsgResponse().rel(entity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	private String checkParam(IsTransIvrEntity entity) {
		
		return null;
	}
	
	/**
	 * 修改时的参数校验
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	private String checkUpdateParam(IsTransIvrEntity entity) {
		if(StringUtil.isBlank(entity.getCustomerCallId())){
			return "接入号不能为空";
		}
		return checkParam(entity);
	}
	
	/**
	 * 修改记录
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 * @return
	 */
	@OperateLog(oper= Operation.UPDATE, operType = OperationType.UPDATE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse update(@RequestBody IsTransIvrEntity entity) {

		String checkRes = checkUpdateParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = isTransIvrService.update(entity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2019-3-7 15:41:34
	 * @author liubo
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	//@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody IsTransIvrVo vo) {

		int n = isTransIvrService.delete(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
}