package com.kim.quality.business.controller;

import com.kim.quality.business.entity.TapeSummaryEntity;
import com.kim.quality.business.service.TapeSummaryService;
import com.kim.quality.business.vo.TapeSummaryVo;
import com.kim.quality.common.QualityModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
 * 录音小结关联表控制类
 * @date 2018-11-16 15:55:08
 * @author bo.liu01
 */
@OperateLog(module = QualityModule.TAPE_SUMMARY)
@Controller
@RequestMapping("tapeSummary")
public class TapeSummaryController extends BaseController {
	
	@Autowired
	private TapeSummaryService tapeSummaryService;
	   
	/**
	 * 分页查询
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	//@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(TapeSummaryVo vo){
		
		return new TableResponse(tapeSummaryService.listByPage(vo));
	}
	
	/**
	 * 不带分页的查询（此方法使用时请注意性能）
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	/* 
	@OperateLog(oper= Operation.QUERY, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="list", method=RequestMethod.GET)
	public ResultResponse list(TapeSummaryVo vo){
		
		return new ObjectResponse(true).data(tapeSummaryService.list(vo));
	}
	*/
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	//@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(TapeSummaryVo vo){

		TapeSummaryEntity entity = tapeSummaryService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	private String checkAddParam(TapeSummaryEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 新增记录
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	//@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody TapeSummaryEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		entity = tapeSummaryService.insert(entity);
		return new MsgResponse().rel(entity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	private String checkParam(TapeSummaryEntity entity) {
		
		return null;
	}
	
	/**
	 * 修改时的参数校验
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	private String checkUpdateParam(TapeSummaryEntity entity) {
		
		return checkParam(entity);
	}
	
	/**
	 * 修改记录
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.UPDATE, operType = OperationType.UPDATE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	//@RequestMapping(value="update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse update(@RequestBody TapeSummaryEntity entity) {

		String checkRes = checkUpdateParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = tapeSummaryService.update(entity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	//@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody TapeSummaryVo vo) {

		int n = tapeSummaryService.delete(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
}