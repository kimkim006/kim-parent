package com.kim.admin.controller;

import com.kim.admin.common.AdminModule;
import com.kim.admin.common.CommonConstant;
import com.kim.admin.entity.ParamEntity;
import com.kim.admin.service.ParamService;
import com.kim.admin.vo.ParamVo;
import com.kim.common.base.BaseController;
import com.kim.common.response.*;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 参数配置表控制类
 * @date 2018-8-15 14:22:43
 * @author bo.liu01
 */
@OperateLog(module = AdminModule.PARAM)
@Controller
@RequestMapping("param")
public class ParamController extends BaseController {
	
	@Autowired
	private ParamService paramService;
	   
	/**
	 * 分页查询
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(ParamVo vo){
		
		return new TableResponse(paramService.listByPage(vo));
	}
	
	/**
	 * 不带分页的查询（此方法使用时请注意性能）
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	/* 
	@OperateLog(oper= Operation.QUERY, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="list", method=RequestMethod.GET)
	public ResultResponse list(ParamVo vo){
		
		return new ObjectResponse(true).data(paramService.list(vo));
	}
	*/
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(ParamVo vo){

		ParamEntity entity = paramService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 查询编码是否唯一
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.CHECK_UNIQUE, parameterType=ParameterType.QUERY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="checkUnique", method=RequestMethod.GET)
	public ResultResponse checkUnique(ParamVo vo){
		
		return new ObjectResponse(true).data(paramService.find(vo) != null);
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	private String checkAddParam(ParamEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 新增记录
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody ParamEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		entity = paramService.insert(entity);
		return new MsgResponse().rel(entity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	private String checkParam(ParamEntity entity) {
		//当不是超级管理员时，注入当前用户能操作的租户编号
		if(!StringUtil.equals(CommonConstant.DEFAULT_TENANT_ID, TokenUtil.getTenantId())){
			entity.tenantId(TokenUtil.getTenantId());
		}
		return null;
	}
	
	/**
	 * 修改时的参数校验
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	private String checkUpdateParam(ParamEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 修改记录
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.UPDATE, operType = OperationType.UPDATE,
			parameterType= ParameterType.BODY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse update(@RequestBody ParamEntity entity) {

		String checkRes = checkUpdateParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = paramService.update(entity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody ParamVo vo) {

		//当不是超级管理员时，注入当前用户能操作的租户编号
		if(!StringUtil.equals(CommonConstant.DEFAULT_TENANT_ID, TokenUtil.getTenantId())){
			vo.tenantId(TokenUtil.getTenantId());
		}
		int n = paramService.delete(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
}