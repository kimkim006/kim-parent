package com.kim.admin.controller;

import com.kim.admin.common.AdminModule;
import com.kim.admin.common.AdminOperation;
import com.kim.admin.common.CommonConstant;
import com.kim.admin.entity.DictEntity;
import com.kim.admin.service.DictService;
import com.kim.admin.vo.DictVo;
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

import java.util.List;

/**
 * 数据字典表控制类
 * @date 2018-8-15 14:22:43
 * @author bo.liu01
 */
@OperateLog(module = AdminModule.DICT)
@Controller
@RequestMapping("dict")
public class DictController extends BaseController {
	
	@Autowired
	private DictService dictService;
	   
	/**
	 * 分页查询
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(DictVo vo){
		
		return new TableResponse(dictService.listByPage(vo));
	}
	
	@OperateLog(oper= Operation.QUERY_FOR_COM, parameterType= ParameterType.QUERY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="listForCom", method=RequestMethod.GET)
	public ResultResponse listForCom(DictVo vo){
		
		vo.setParentId(DictEntity.DEFAULT_PARENT_ID);
		List<DictEntity> list = dictService.list(vo);
		list.add(new DictEntity().tenantId(TokenUtil.getTenantId()).id(DictEntity.DEFAULT_PARENT_ID).name("字典项"));
		return new ObjectResponse(true).data(list);
	}
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(DictVo vo){

		DictEntity entity = dictService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.CHECK_UNIQUE, parameterType=ParameterType.QUERY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="checkUnique", method=RequestMethod.GET)
	public ResultResponse checkUnique(DictVo vo){
		
		return new ObjectResponse(true).data(dictService.find(vo) != null);
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	private String checkAddParam(DictEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 新增键值对
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= AdminOperation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody DictEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		entity = dictService.insert(entity);
		return new MsgResponse().rel(entity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2018-8-15 14:22:43
	 * @author bo.liu01
	 */
	private String checkParam(DictEntity entity) {
		
//		if(StringUtil.isBlank(entity.getTenantId())){
//			entity.tenantId(TokenUtil.getTenantId());
//		}

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
	private String checkUpdateParam(DictEntity entity) {
		
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
	public ResultResponse update(@RequestBody DictEntity entity) {

		String checkRes = checkUpdateParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = dictService.update(entity);
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
	public ResultResponse delete(@RequestBody DictVo vo) {
		if(StringUtil.isBlank(vo.getId())){
			logger.error("删除数据字典时id不能为空");
			return new MsgResponse(MsgCode.INVALID_PARAMETER).addMsg("id不能为空");
		}
		
		//当不是超级管理员时，注入当前用户能操作的租户编号
		if(!StringUtil.equals(CommonConstant.DEFAULT_TENANT_ID, TokenUtil.getTenantId())){
			vo.tenantId(TokenUtil.getTenantId());
		}
		int n = dictService.delete(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
}