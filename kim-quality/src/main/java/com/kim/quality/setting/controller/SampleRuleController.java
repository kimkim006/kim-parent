package com.kim.quality.setting.controller;

import java.util.HashMap;
import java.util.Map;

import com.kim.quality.common.CommonConstant;
import com.kim.quality.common.QualityModule;
import com.kim.quality.common.QualityOperation;
import com.kim.quality.setting.entity.SampleRuleEntity;
import com.kim.quality.setting.service.SampleRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.util.StringUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.quality.setting.vo.SampleRuleVo;

/**
 * 系统抽检规则表控制类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
@OperateLog(module = QualityModule.SAMPLE_RULE)
@Controller
@RequestMapping("sampleRule")
public class SampleRuleController extends BaseController {
	
	@Autowired
	private SampleRuleService sampleRuleService;
	   
	/**
	 * 分页查询
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_TREE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listTree", method=RequestMethod.GET)
	public ResultResponse listTree(SampleRuleVo vo){
		
		return new ObjectResponse(true).data(sampleRuleService.listTree(vo));
	}
	
	@OperateLog(oper= QualityOperation.QUERY_BUSINESS_COM, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listBusinessCom", method=RequestMethod.GET)
	public ResultResponse listBusinessCom(SampleRuleVo vo){
		
		JSONArray array = BaseCacheUtil.getDictList(CommonConstant.DICT_BUSINESS_KEY, vo.getTenantId());
		Map<String, String> map = new HashMap<>(); 
		map.put("code", CommonConstant.BUSINESS_CALL_OUT);
		map.put("name", CommonConstant.BUSINESS_CALL_OUT_NAME);
		array.add(map);
		return new ObjectResponse(true).data(array);
	}
	
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(SampleRuleVo vo){

		SampleRuleEntity entity = sampleRuleService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 */
	private String checkAddParam(SampleRuleEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 新增记录
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody SampleRuleEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		entity = sampleRuleService.insert(entity);
		return new MsgResponse().rel(entity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 */
	private String checkParam(SampleRuleEntity entity) {
		
		return null;
	}
	
	/**
	 * 修改时的参数校验
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 */
	private String checkUpdateParam(SampleRuleEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 修改记录
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.UPDATE, operType = OperationType.UPDATE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse update(@RequestBody SampleRuleEntity entity) {

		String checkRes = checkUpdateParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = sampleRuleService.update(entity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2018-8-23 14:52:50
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody SampleRuleVo vo) {

		if(StringUtil.isBlank(vo.getId())){
			logger.error("删除抽检规则id不能为空");
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg("id不能为空");
		}
		int n = sampleRuleService.delete(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
}