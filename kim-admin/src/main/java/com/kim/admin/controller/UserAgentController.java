package com.kim.admin.controller;

import com.kim.admin.common.AdminModule;
import com.kim.admin.entity.UserAgentEntity;
import com.kim.admin.vo.UserAgentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.admin.service.UserAgentService;
import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.response.TableResponse;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;

/**
 * 坐席工号表控制类
 * @date 2018-9-7 15:33:14
 * @author yonghui.wu
 */
@OperateLog(module = AdminModule.USERAGENT)
@Controller
@RequestMapping("userAgent")
public class UserAgentController extends BaseController {
	
	@Autowired
	private UserAgentService userAgentService;
	   
	/**
	 * 分页查询
	 * @date 2018-9-7 15:33:14
	 * @author yonghui.wu
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(UserAgentVo vo){
		
		return new TableResponse(userAgentService.listByPage(vo));
	}

	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-9-7 15:33:14
	 * @author yonghui.wu
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(UserAgentVo vo){
		UserAgentEntity entity = userAgentService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2018-9-7 15:33:14
	 * @author yonghui.wu
	 */
	private String checkAddParam(UserAgentEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 新增记录
	 * @date 2018-9-7 15:33:14
	 * @author yonghui.wu
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody UserAgentEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		entity = userAgentService.insert(entity);
		return new MsgResponse().rel(entity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * 校验是否存在有效的cisco/awaya工号
	 * @date 2018-9-7 15:33:14
	 * @author yonghui.wu
	 */
	private String checkParam(UserAgentEntity entity) {
		String resultMsg=null;
		entity.setAgentNo(entity.getAgentNo().trim());
		if(userAgentService.checkAgentNoUnique(entity)){
			resultMsg="话务工号已存在";
		}
		return resultMsg;
	}
	
	/**
	 * 修改时的参数校验
	 * @date 2018-9-7 15:33:14
	 * @author yonghui.wu
	 */
	private String checkUpdateParam(UserAgentEntity entity) {
		
		return checkParam(entity);
	}

	/**
	 * 修改记录
	 * @date 2018-9-7 15:33:14
	 * @author yonghui.wu
	 * @return
	 */
	@OperateLog(oper= Operation.UPDATE, operType = OperationType.UPDATE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse update(@RequestBody UserAgentEntity entity) {

		String checkRes = checkUpdateParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = userAgentService.update(entity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2018-9-7 15:33:14
	 * @author yonghui.wu
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody UserAgentVo vo) {

		int n = userAgentService.delete(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}

}