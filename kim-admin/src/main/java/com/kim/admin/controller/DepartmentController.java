package com.kim.admin.controller;

import com.kim.admin.common.AdminModule;
import com.kim.admin.common.AdminOperation;
import com.kim.admin.entity.DepartmentEntity;
import com.kim.admin.entity.DepartmentUserEntity;
import com.kim.admin.service.DepartmentUserService;
import com.kim.admin.vo.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.admin.common.CommonConstant;
import com.kim.admin.service.DepartmentService;
import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;

/**
 * 部门表控制类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@OperateLog(module = AdminModule.DEPARTMENT)
@Controller
@RequestMapping("department")
public class DepartmentController extends BaseController {
	
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private DepartmentUserService departmentUserService;
	   
	/**
	 * 下拉列表查询
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= AdminOperation.DEPART_TREE_CON, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listTreeByCon", method=RequestMethod.GET)
	public ResultResponse listTreeByCon(DepartmentVo departmentVo){
		
		return new ObjectResponse(true).data(departmentService.listTree(departmentVo));
	}

	/**
	 * 不带分页的树形查询
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_TREE, parameterType= ParameterType.QUERY,
			injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="listTree", method=RequestMethod.GET)
	public ResultResponse listTree(DepartmentVo departmentVo){
		
		if(!StringUtil.equals(CommonConstant.DEFAULT_TENANT_ID, TokenUtil.getTenantId())
				|| StringUtil.isBlank(departmentVo.getTenantId())){
			departmentVo.setTenantId(TokenUtil.getTenantId());
		}

		return new ObjectResponse(true).data(departmentService.listTree(departmentVo));
	}

	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(DepartmentVo departmentVo){

		DepartmentEntity departmentEntity = departmentService.find(departmentVo);
		if(departmentEntity != null){
			return new ObjectResponse(true).data(departmentEntity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	private String checkAddParam(DepartmentEntity departmentEntity) {
		DepartmentVo departmentVo = new DepartmentVo();
		departmentVo.setCode(departmentEntity.getCode());
		departmentVo.setTenantId(departmentEntity.getTenantId());
		DepartmentEntity en = departmentService.find(departmentVo );
		if(en!=null){
			return "该机构编码已存在";
		}
		return checkParam(departmentEntity);
	}
	
	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody DepartmentEntity departmentEntity) {

		String checkRes = checkAddParam(departmentEntity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		departmentEntity = departmentService.insert(departmentEntity);
		return new MsgResponse().rel(departmentEntity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	private String checkParam(DepartmentEntity departmentEntity) {
		
		return null;
	}
	
	/**
	 * 修改时的参数校验
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	private String checkUpdateParam(DepartmentEntity departmentEntity) {
		DepartmentVo departmentVo = new DepartmentVo();
		departmentVo.setId(departmentEntity.getId());
		departmentVo.setCode(departmentEntity.getCode());
		departmentVo.setTenantId(departmentEntity.getTenantId());
		String id = departmentService.checkUnique(departmentVo );
		if(id!=null){
			return "该机构编码已存在";
		}
		return checkParam(departmentEntity);
	}
	
	/**
	 * 修改记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.UPDATE, operType = OperationType.UPDATE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse update(@RequestBody DepartmentEntity departmentEntity) {

		String checkRes = checkUpdateParam(departmentEntity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = departmentService.update(departmentEntity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody DepartmentVo departmentVo) {

		int n = departmentService.delete(departmentVo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 部门新增人员
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= AdminOperation.DEPART_ADD_USER, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="addUser", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse addUser(@RequestBody DepartmentUserEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		return new MsgResponse().rel(departmentUserService.insertUser(entity) > 0);
	}

	private String checkAddParam(DepartmentUserEntity entity) {
		if(StringUtil.isBlank(entity.getDepartmentCode())){
			return "部门编码不能为空";
		}
		if(StringUtil.isBlank(entity.getUsernames())){
			return "人员工号不能为空";
		}
		return null;
	}
	
	/**
	 * 部门新增人员
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= AdminOperation.DEPART_REMOVE_USER, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="removeUser", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse removeUser(@RequestBody DepartmentUserEntity entity) {

		if(StringUtil.isBlank(entity.getUsernames())){
			logger.error("人员工号不能为空");
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg("人员工号不能为空");
		}
		return new MsgResponse().rel(departmentUserService.removeUser(entity) > 0);
	}
	
	
}