package com.kim.admin.controller;

import com.kim.admin.common.AdminModule;
import com.kim.admin.common.AdminOperation;
import com.kim.admin.vo.TenantVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.admin.entity.TenantEntity;
import com.kim.admin.service.TenantService;
import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.response.TableResponse;
import com.kim.common.util.StringUtil;
import com.kim.common.validation.ValidateEntity;
import com.kim.common.validation.ValidateUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;

/**
 * 租户表控制类
 * @date 2018-7-5 13:05:21
 * @author bo.liu01
 */
@OperateLog(module = AdminModule.TENANT)
@Controller
@RequestMapping("tenant")
public class TenantController extends BaseController {
	
	@Autowired
	private TenantService tenantService;
	   
	/**
	 * 分页查询
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(TenantVo tenantVo){
		
		return new TableResponse(tenantService.listByPage(tenantVo));
	}
	
	@OperateLog(oper= Operation.QUERY_FOR_COM, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listForCom", method=RequestMethod.GET)
	public ResultResponse listForCom(TenantVo tenantVo){
		
		return new ObjectResponse(true).data(tenantService.list(tenantVo));
	}
	
	@OperateLog(oper= AdminOperation.TENANT_MENU_TREE, parameterType= ParameterType.QUERY,
			injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="listTreeByTenant", method=RequestMethod.GET)
	public ResultResponse listTreeByTenant(TenantVo tenantVo){
		
		return new ObjectResponse(true).data(tenantService.listTreeByTenant(tenantVo));
	}
	
	/**
	 * 不带分页的查询（此方法使用时请注意性能）
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	/* 
	@OperateLog(oper= Operation.QUERY, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="list", method=RequestMethod.GET)
	public ResultResponse list(TenantVo tenantVo){
		
		return new ObjectResponse(true).data(tenantService.list(tenantVo));
	}
	*/
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(TenantVo tenantVo){

		TenantEntity tenantEntity = tenantService.find(tenantVo);
		if(tenantEntity != null){
			return new ObjectResponse(true).data(tenantEntity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	@OperateLog(oper= AdminOperation.TENANT_MENU_ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="menu/add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse addMenu(@RequestBody TenantVo vo) {

		String checkRes = checkAddParam(vo);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = tenantService.insertMenu(vo);
		return new MsgResponse().rel(n > 0);
	}
	
	private String checkAddParam(TenantVo vo) {
		if(StringUtil.isBlank(vo.getAddMenu()) && StringUtil.isBlank(vo.getDelMenu())){
			return "菜单不能为空";
		}
		if(StringUtil.isBlank(vo.getTenantId())){
			return "租户ID不能为空";
		}
		return null;
	}

	/**
	 * 新增时的参数校验
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	private String checkAddParam(TenantEntity tenantEntity) {
		String res = checkParam(tenantEntity);
		if(res != null){
			return res;
		}
		TenantEntity t = tenantService.find(new TenantVo().setName(tenantEntity.getName()));
		if(t != null){
			return "该租户已存在";
		}
		return null;
	}
	
	/**
	 * 新增记录
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody TenantEntity tenantEntity) {

		String checkRes = checkAddParam(tenantEntity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		tenantEntity = tenantService.insert(tenantEntity);
		return new MsgResponse().rel(tenantEntity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	private String checkParam(TenantEntity tenantEntity) {
		
		String res = ValidateUtil.checkEntity(tenantEntity, new ValidateEntity("name", "租户名称", true, 100));
		if(res != null){
			return res;
		}
		tenantEntity.setName(tenantEntity.getName().trim());
		return null;
	}
	
	/**
	 * 修改时的参数校验
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	private String checkUpdateParam(TenantEntity tenantEntity) {
		String res = checkParam(tenantEntity);
		if(res != null){
			return res;
		}
		
		String t = tenantService.checkUnique(new TenantVo()
				.setName(tenantEntity.getName()).setTenantId(tenantEntity.getTenantId()));
		if(t != null){
			return "该租户已存在";
		}
		return null;
	}
	
	/**
	 * 修改记录
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.UPDATE, operType = OperationType.UPDATE,
			parameterType= ParameterType.BODY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse update(@RequestBody TenantEntity tenantEntity) {

		String checkRes = checkUpdateParam(tenantEntity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = tenantService.update(tenantEntity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2018-7-5 13:05:21
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse deleteLogic(@RequestBody TenantVo tenantVo) {

		int n = tenantService.deleteLogic(tenantVo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
}