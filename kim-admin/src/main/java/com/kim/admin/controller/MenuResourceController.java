package com.kim.admin.controller;

import com.kim.admin.common.AdminModule;
import com.kim.admin.service.MenuResourceService;
import com.kim.admin.vo.MenuResourceVo;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
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
 * 菜单资源表控制类
 * @date 2017-11-10 13:45:34
 * @author bo.liu01
 */
@OperateLog(module = AdminModule.MENU_RESOURCE)
@Controller
@RequestMapping("menuResource")
public class MenuResourceController extends BaseController {
	
	@Autowired
	private MenuResourceService menuResourceService;
	   
	/**
	 * 新增记录
	 * @date 2017-11-10 13:45:34
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody MenuResourceVo menuResourceVo) {

		String checkRes = checkParam(menuResourceVo);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = menuResourceService.insert(menuResourceVo);
		return new MsgResponse().rel(n > 0);
	}
	
	/**
	 * 新增和删除时的共用参数校验
	 * @date 2017-11-10 13:45:34
	 * @author bo.liu01
	 */
	private String checkParam(MenuResourceVo menuResourceVo) {
		if(StringUtil.isBlank(menuResourceVo.getMenuCode())){
			return "菜单编码不能为空";
		}
		if(StringUtil.isBlank(menuResourceVo.getResourceCode())){
			return "资源编码不能为空";
		}
		return null;
	}

	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2017-11-10 13:45:34
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody MenuResourceVo menuResourceVo) {

		String checkRes = checkParam(menuResourceVo);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}

		int n = menuResourceService.delete(menuResourceVo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
}