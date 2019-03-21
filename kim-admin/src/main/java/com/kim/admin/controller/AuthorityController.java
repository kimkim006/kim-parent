package com.kim.admin.controller;

import com.kim.admin.common.AdminModule;
import com.kim.admin.common.AdminOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.admin.entity.AuthorityEntity;
import com.kim.admin.service.AuthorityService;
import com.kim.admin.vo.AuthorityVo;
import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.util.StringUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.OperationType;

/**
 * 权限表控制类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@OperateLog(module = AdminModule.AUTHORITY)
@Controller
@RequestMapping("authority")
public class AuthorityController extends BaseController {
	
	@Autowired
	private AuthorityService authorityService;
	   
	/**
	 * 分页查询
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= AdminOperation.AUTH_MENU_TREE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listTree", method=RequestMethod.GET)
	public ResultResponse listTree(AuthorityVo authorityVo){
		
		return new ObjectResponse(true).data(authorityService.listTree(authorityVo));
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	private String checkAddParam(AuthorityVo authorityVo) {

		if(StringUtil.isBlank(authorityVo.getAddAuth()) && StringUtil.isBlank(authorityVo.getDelAuth())){
			return "菜单不能为空";
		}
		if(StringUtil.isBlank(authorityVo.getOwnerCode())){
			return "用户名或角色编码不能为空";
		}
		return null;
	}
	
	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= AdminOperation.AUTH_ADD_ROLE, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="role/add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse roleAdd(@RequestBody AuthorityVo authorityVo) {

		String checkRes = checkAddParam(authorityVo);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		authorityVo.setOwnerType(AuthorityEntity.OWNER_TYPE_ROLE);
		int n = authorityService.insert(authorityVo);
		return new MsgResponse().rel(n > 0);
	}

	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= AdminOperation.AUTH_ADD_USER, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="user/add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse userAdd(@RequestBody AuthorityVo authorityVo) {

		String checkRes = checkAddParam(authorityVo);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		authorityVo.setOwnerType(AuthorityEntity.OWNER_TYPE_USER);
		int n = authorityService.insert(authorityVo);
		return new MsgResponse().rel(n > 0);
	}
	
}