package com.kim.quality.business.controller;

import com.kim.quality.business.service.SampleService;
import com.kim.quality.business.vo.SampleVo;
import com.kim.quality.common.QualityModule;
import com.kim.quality.common.QualityOperation;
import com.kim.quality.setting.service.SampleRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.kim.quality.business.entity.SampleEntity;
import com.kim.quality.setting.vo.SampleRuleVo;

/**
 * 抽检批次记录表控制类
 * @date 2018-8-28 18:24:20
 * @author bo.liu01
 */
@OperateLog(module = QualityModule.SAMPLE)
@Controller
@RequestMapping("sample")
public class SampleController extends BaseController {
	
	@Autowired
	private SampleService sampleService;
	@Autowired
	private SampleRuleService sampleRuleService;
	   
	/**
	 * 分页查询
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(SampleVo vo){
		
		//默认只查询抽取成功和已分配的
		//if(vo.getStatus() == null){
			//vo.addStatusArr(SampleEntity.STATUS_SUCCESS, SampleEntity.STATUS_SUCCESS, SampleEntity.STATUS_ALLOCATED);
		//}
		
		return new TableResponse(sampleService.listByPage(vo));
	}
	
	/**
	 * 规则查询，只查询手工规则
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_TREE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listTree", method=RequestMethod.GET)
	public ResultResponse listTree(SampleRuleVo vo){
		
//		return new ObjectResponse(true).data(sampleRuleService.listManualRuleTree());
		return new ObjectResponse(true).data(sampleRuleService.listTree(vo));
	}
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(SampleVo vo){

		SampleEntity entity = sampleService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	private String checkAddParam(SampleEntity entity) {
		if(StringUtil.isBlank(entity.getExtractStartDate())){
			return "数据开始日期不能为空";
		}
		if(entity.getRuleId() == null){
			return "抽检规则不能为空";
		}
		return null;
	}
	
	/**
	 * 新增记录
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= QualityOperation.QUALITY_EXECUTE, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="execute", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse execute(@RequestBody SampleEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		return new ObjectResponse(true).data(sampleService.execute(entity));
	}
	
	@OperateLog(oper= QualityOperation.QUALITY_EXECUTE_BY_SYSTEM, operType = OperationType.INSERT,
			parameterType= ParameterType.QUERY, logOperation=false, injectCurntTenant=false,
			injectCurntDate=false, injectCurntUser=false)
	@ResponseBody
	@RequestMapping(value="executeSystem", method=RequestMethod.GET)
	public ResultResponse executeSystem(SampleEntity entity) {

		String checkRes = checkParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		return new ObjectResponse(true).data(sampleService.executeSystem(entity));
	}
	
	private String checkParam(SampleEntity entity) {
		if(StringUtil.isBlank(entity.getExtractDate())){
			return "数据日期不能为空";
		}
		if(StringUtil.isBlank(entity.getTenantId())){
			return "租户id不能为空";
		}
		if(StringUtil.isBlank(entity.getOperUser())){
			entity.setOperUser("unknown");
		}
		if(StringUtil.isBlank(entity.getOperName())){
			entity.setOperUser("未知匿名");
		}
		return null;
	}

	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody SampleVo vo) {

		if(StringUtil.isBlank(vo.getId())){
			logger.error("id不能为空");
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg("id不能为空");
		}
		int n = sampleService.delete(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2018-8-28 18:24:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= QualityOperation.DELETE_DETAIL, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="deleteDetail", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse deleteDetail(@RequestBody SampleVo vo) {

		String checkRes = checkParamForDelDetail(vo);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		
		int n = sampleService.deleteDetail(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("已删除失败");
	}
	
	private String checkParamForDelDetail(SampleVo vo) {
		if(StringUtil.isBlank(vo.getBatchNo())){
			return "批次不能为空";
		}
		if(StringUtil.isBlank(vo.getTaskId())){
			return "请选择质检任务";
		}
		return null;
	}
}