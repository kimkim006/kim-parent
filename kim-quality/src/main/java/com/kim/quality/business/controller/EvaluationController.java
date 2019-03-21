package com.kim.quality.business.controller;

import com.kim.quality.business.entity.EvaluationEntity;
import com.kim.quality.business.vo.EvaluationVo;
import com.kim.quality.common.QualityModule;
import com.kim.quality.common.QualityOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.quality.business.service.EvaluationService;
import com.kim.quality.common.CommonConstant;

/**
 * 质检评分表控制类
 * @date 2018-9-17 15:43:36
 * @author jianming.chen
 */
@OperateLog(module = QualityModule.EVALUATION)
@Controller
@RequestMapping("evaluation")
public class EvaluationController extends BaseController {

	@Autowired
	private EvaluationService evaluationService;
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-9-17 15:43:36
	 * @author jianming.chen
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(EvaluationVo vo){

		EvaluationEntity entity = evaluationService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	/**
	 * @desc:获取初始录音总分
	 * @param: [vo]
	 * @return: com.kim.common.response.ResultResponse
	 * @auther: yonghui.wu
	 * @date: 2018/9/26 15:35
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="getOrigScore", method=RequestMethod.GET)
	public ResultResponse getOrigScore(EvaluationVo vo){
		String origScore = BaseCacheUtil.getParam(CommonConstant.DICT_ORIG_SCORE_KEY, vo.getTenantId());
		return new ObjectResponse(true).data(origScore);
	}
	/**
	 * 新增时的参数校验
	 * @date 2018-9-17 15:43:36
	 * @author jianming.chen
	 */
	private String checkAddParam(EvaluationEntity entity) {

		return checkParam(entity);
	}

	/**
	 * 新增记录
	 * @date 2018-9-17 15:43:36
	 * @author jianming.chen
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="add", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody EvaluationEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		evaluationService.insert(entity);
		return new ObjectResponse(true).data(entity);
	}

	/**
	 * 新增和修改时的共用参数校验
	 * @date 2018-9-17 15:43:36
	 * @author jianming.chen
	 */
	private String checkParam(EvaluationEntity entity) {
		if(StringUtils.isBlank(entity.getEvaluationSettings())){
			return "请添加评分项";
		}
		return null;
	}
	
	@OperateLog(oper= QualityOperation.CALC_LAST, parameterType=ParameterType.QUERY,
			logOperation=false, injectCurntTenant=false,
			injectCurntDate=false, injectCurntUser=false)
	@ResponseBody
	@RequestMapping(value="calcLast", method=RequestMethod.GET)
	public ResultResponse calcLast(EvaluationVo vo){

		evaluationService.calcLast(vo);
		return new MsgResponse().rel(true);
	}

}