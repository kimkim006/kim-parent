package com.kim.admin.controller;

import com.kim.admin.common.AdminModule;
import com.kim.admin.common.AdminOperation;
import com.kim.admin.entity.MenuTemplateEntity;
import com.kim.admin.vo.MenuTemplateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.admin.service.MenuTemplateService;
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

/**
 * 租户菜单模板表控制类
 * @date 2018-8-2 15:08:10
 * @author bo.liu01
 */
@OperateLog(module = AdminModule.MENU_TEMPLATE)
@Controller
@RequestMapping("menuTemplate")
public class MenuTemplateController extends BaseController {
	
	@Autowired
	private MenuTemplateService menuTemplateService;
	   
	/**
	 * 分页查询
	 * @date 2018-8-2 15:08:10
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(MenuTemplateVo vo){
		
		return new TableResponse(menuTemplateService.listByPage(vo));
	}
	
	/**
	 * 不带分页的查询（此方法使用时请注意性能）
	 * @date 2018-8-2 15:08:10
	 * @author bo.liu01
	 */
	@OperateLog(oper= AdminOperation.QUERY_FOR_COM, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listForCom", method=RequestMethod.GET)
	public ResultResponse listForCom(){
		
		return new ObjectResponse(true).data(menuTemplateService.list(new MenuTemplateVo()));
	}
	
	/**
	 * 分页查询
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= AdminOperation.TEMPLATE_MENU_TREE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listTreeByTemp", method=RequestMethod.GET)
	public ResultResponse listTreeByTemp(MenuTemplateVo vo){
		
		return new ObjectResponse(true).data(menuTemplateService.listTreeByTemp(vo.getCode()));
	}
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-8-2 15:08:10
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(MenuTemplateVo vo){

		MenuTemplateEntity entity = menuTemplateService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2018-8-2 15:08:10
	 * @author bo.liu01
	 */
	private String checkAddParam(MenuTemplateEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 新增记录
	 * @date 2018-8-2 15:08:10
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody MenuTemplateEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		entity = menuTemplateService.insert(entity);
		return new MsgResponse().rel(entity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2018-8-2 15:08:10
	 * @author bo.liu01
	 */
	private String checkParam(MenuTemplateEntity entity) {
		
		return null;
	}
	
	/**
	 * 修改时的参数校验
	 * @date 2018-8-2 15:08:10
	 * @author bo.liu01
	 */
	private String checkUpdateParam(MenuTemplateEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 修改记录
	 * @date 2018-8-2 15:08:10
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.UPDATE, operType = OperationType.UPDATE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse update(@RequestBody MenuTemplateEntity entity) {

		String checkRes = checkUpdateParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = menuTemplateService.update(entity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2018-8-2 15:08:10
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody MenuTemplateVo vo) {

		int n = menuTemplateService.delete(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 新增记录
	 * @date 2018-8-2 15:08:10
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= AdminOperation.TEMPLATE_MENU_ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="menu/add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse menuAdd(@RequestBody MenuTemplateVo vo) {

		String checkRes = checkAddParam(vo);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = menuTemplateService.insertMenu(vo);
		return new MsgResponse().rel(n > 0);
	}

	private String checkAddParam(MenuTemplateVo vo) {
		if(StringUtil.isBlank(vo.getAddMenu()) && StringUtil.isBlank(vo.getDelMenu())){
			return "菜单不能为空";
		}
		if(StringUtil.isBlank(vo.getCode())){
			return "模板编码不能为空";
		}
		return null;
	}
	
}