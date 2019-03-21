package com.kim.quality.business.controller;

import com.kim.quality.common.QualityModule;
import com.kim.quality.common.QualityOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.response.TableResponse;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.TokenUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.quality.business.entity.AdjustScoreEntity;
import com.kim.quality.business.service.AdjustScoreService;
import com.kim.quality.business.vo.AdjustScoreVo;
import com.kim.quality.common.CommonConstant;

/**
 * 调整分数记录表控制类
 * @date 2018-12-12 14:44:33
 * @author liubo
 */
@OperateLog(module = QualityModule.ADJUST_SCORE)
@Controller
@RequestMapping("adjustScore")
public class AdjustScoreController extends BaseController {
	
	@Autowired
	private AdjustScoreService adjustScoreService;
	   
	/**
	 * 分页查询
	 * @date 2018-12-12 14:44:33
	 * @author liubo
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(AdjustScoreVo vo){
		
		return new TableResponse(adjustScoreService.listByPage(vo));
	}
	
	/**
	 * 不带分页的查询（此方法使用时请注意性能）
	 * @date 2018-12-12 14:44:33
	 * @author liubo
	 */
	/* 
	@OperateLog(oper= Operation.QUERY, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="list", method=RequestMethod.GET)
	public ResultResponse list(ActionNoteVo vo){
		
		return new ObjectResponse(true).data(actionNoteService.list(vo));
	}
	*/
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-12-12 14:44:33
	 * @author liubo
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(AdjustScoreVo vo){

		AdjustScoreEntity entity = adjustScoreService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	@OperateLog(oper= QualityOperation.FIND_ADJUST_SCORE_OPT, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="findOpt", method=RequestMethod.GET)
	public ResultResponse findOpt(){
		
		String tenantId = TokenUtil.getTenantId();
		JSONObject json = new JSONObject();
		json.put("minScore", NumberUtil.toDouble(BaseCacheUtil.getParam(CommonConstant.ADJUST_SCORE_LIMIT_MIN_KEY, tenantId),
				CommonConstant.ADJUST_SCORE_LIMIT_MIN_DEFAULT));
		json.put("maxScore", NumberUtil.toDouble(BaseCacheUtil.getParam(CommonConstant.ADJUST_SCORE_LIMIT_MAX_KEY, tenantId),
				CommonConstant.ADJUST_SCORE_LIMIT_MAX_DEFAULT));
		json.put("maxCount", NumberUtil.toInt(BaseCacheUtil.getParam(CommonConstant.ADJUST_SCORE_LIMIT_MAX_COUNT_KEY, tenantId),
				CommonConstant.ADJUST_SCORE_LIMIT_MAX_COUNT_DEFAULT));
		
		return new ObjectResponse(true).data(json);
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2018-12-12 14:44:33
	 * @author liubo
	 */
	private String checkAddParam(AdjustScoreEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 新增记录
	 * @date 2018-12-12 14:44:33
	 * @author liubo
	 * @return
	 */
	@OperateLog(oper= QualityOperation.ADD_ADJUST_SCORE, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody AdjustScoreEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		entity = adjustScoreService.insert(entity);
		return new MsgResponse().rel(entity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2018-12-12 14:44:33
	 * @author liubo
	 */
	private String checkParam(AdjustScoreEntity entity) {
		
		return null;
	}
	
}