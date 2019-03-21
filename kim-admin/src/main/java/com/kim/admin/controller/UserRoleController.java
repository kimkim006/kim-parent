package com.kim.admin.controller;

import com.kim.admin.common.AdminModule;
import com.kim.admin.service.UserRoleService;
import com.kim.admin.vo.UserRoleVo;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户角色表控制类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@OperateLog(module = AdminModule.USER_ROLE)
@Controller
@RequestMapping("userRole")
public class UserRoleController extends BaseController {
	
	@Autowired
	private UserRoleService userRoleService;
	   
	/**
	 * 分页查询
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listRole", method=RequestMethod.GET)
	public ResultResponse listRole(UserRoleVo userRoleVo){
		
		return new ObjectResponse(true).data(userRoleService.listRole(userRoleVo));
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	private String checkAddParam(UserRoleVo userRoleVo) {
		if(StringUtil.isBlank(userRoleVo.getUsername())){
			return "用户名不能为空";
		}
		if(StringUtil.isBlank(userRoleVo.getAddRole()) && StringUtil.isBlank(userRoleVo.getDelRole())){
			return "角色不能为空";
		}
		return null;
	}
	
	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.SAVE, operType = OperationType.SAVE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="save", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse save(@RequestBody UserRoleVo userRoleVo) {

		String checkRes = checkAddParam(userRoleVo);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = userRoleService.insert(userRoleVo);
		return new MsgResponse().rel(n > 0);
	}
	
}