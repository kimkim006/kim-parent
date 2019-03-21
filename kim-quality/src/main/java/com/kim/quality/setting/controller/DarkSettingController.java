package com.kim.quality.setting.controller;

import com.kim.quality.common.QualityModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.common.base.BaseController;
import com.kim.common.page.PageVo;
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
import com.kim.quality.setting.entity.DarkSettingEntity;
import com.kim.quality.setting.service.DarkSettingService;
import com.kim.quality.setting.vo.DarkSettingVo;

/**
 * 抽检小黑屋表控制类
 * @date 2018-11-21 17:34:22
 * @author bo.liu01
 */
@OperateLog(module = QualityModule.DARK_SETTING)
@Controller
@RequestMapping("darkSetting")
public class DarkSettingController extends BaseController {
	
	@Autowired
	private DarkSettingService darkSettingService;
	   
	/**
	 * 分页查询
	 * @date 2018-11-21 17:34:22
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(DarkSettingVo vo){
		
		vo.setOrderBy("oper_time");
		vo.setOrderType(PageVo.ORDER_TYPE_DESC);
		return new TableResponse(darkSettingService.listByPage(vo));
	}
	
	/**
	 * 不带分页的查询（此方法使用时请注意性能）
	 * @date 2018-11-21 17:34:22
	 * @author bo.liu01
	 */
	/* 
	@OperateLog(oper= Operation.QUERY, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="list", method=RequestMethod.GET)
	public ResultResponse list(DarkSettingVo vo){
		
		return new ObjectResponse(true).data(darkSettingService.list(vo));
	}
	*/
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-11-21 17:34:22
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(DarkSettingVo vo){

		DarkSettingEntity entity = darkSettingService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2018-11-21 17:34:22
	 * @author bo.liu01
	 */
	private String checkAddParam(DarkSettingEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 新增记录
	 * @date 2018-11-21 17:34:22
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody DarkSettingEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		entity = darkSettingService.insert(entity);
		return new MsgResponse().rel(entity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2018-11-21 17:34:22
	 * @author bo.liu01
	 */
	private String checkParam(DarkSettingEntity entity) {
		if(StringUtil.isBlank(entity.getUsername())){
			return "坐席工号不能为空";
		}
		if(StringUtil.isBlank(entity.getStartTime())){
			return "开始时间不能为空";
		}
		if(StringUtil.isBlank(entity.getEndTime())){
			return "结束时间不能为空";
		}
		return null;
	}
	
	/**
	 * 修改时的参数校验
	 * @date 2018-11-21 17:34:22
	 * @author bo.liu01
	 */
	private String checkUpdateParam(DarkSettingEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 修改记录
	 * @date 2018-11-21 17:34:22
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.UPDATE, operType = OperationType.UPDATE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse update(@RequestBody DarkSettingEntity entity) {

		String checkRes = checkUpdateParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = darkSettingService.update(entity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2018-11-21 17:34:22
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody DarkSettingVo vo) {

		int n = darkSettingService.delete(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="active", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse active(@RequestBody DarkSettingVo vo) {
		
		darkSettingService.active(vo);
		return new MsgResponse(MsgCode.SUCCESS);
	}
	
}