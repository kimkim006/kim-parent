package com.kim.quality.business.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kim.quality.business.service.SummaryService;
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
import com.kim.common.response.TableResponse;
import com.kim.common.util.NumberUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.quality.business.entity.SummaryEntity;
import com.kim.quality.business.vo.SummaryVo;
import com.kim.quality.common.CommonConstant;
import com.kim.quality.common.QualityModule;

/**
 * 小结表控制类
 * @date 2018-11-16 15:55:08
 * @author bo.liu01
 */
@OperateLog(module = QualityModule.SUMMARY)
@Controller
@RequestMapping("summary")
public class SummaryController extends BaseController {
	
	@Autowired
	private SummaryService summaryService;
	   
	/**
	 * 分页查询
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(SummaryVo vo){
		
		return new TableResponse(summaryService.listByPage(vo));
	}
	
	@OperateLog(oper= Operation.QUERY, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listSource", method=RequestMethod.GET)
	public ResultResponse listSource(){
		
		return new ObjectResponse(true).data(BaseCacheUtil.getDictList(CommonConstant.TAPE_RELATES_DICT_CODE, TokenUtil.getTenantId()));
	}
	
	/**
	 * 不带分页的查询（此方法使用时请注意性能）
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_FOR_COM, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listCom", method=RequestMethod.GET)
	public ResultResponse list(SummaryVo vo){
		
		List<Map<String, String>> mapList = new ArrayList<>();
		if(StringUtil.isNotBlank(vo.getParentCode()) || NumberUtil.equals(vo.getLevel(), 0)){
			List<SummaryEntity> list = summaryService.list(vo);
			Map<String, String> map = null;
			String format = "(%s)%s";
			for (SummaryEntity summary : list) {
				if(NumberUtil.equals(summary.getLevel(), 0)){
					summary.setName(String.format(format,  SummaryEntity.getSourceName(summary.getSource()), summary.getName()));
				}
				map = new HashMap<>();
				map.put("name", summary.getName());
				map.put("value", summary.getCode());
				mapList.add(map);
			}
		}
		return new ObjectResponse(true).data(mapList);
	}
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(SummaryVo vo){

		SummaryEntity entity = summaryService.find(vo);
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
	private String checkAddParam(SummaryEntity entity) {
		
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
	public ResultResponse add(@RequestBody SummaryEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		entity = summaryService.insert(entity);
		return new MsgResponse().rel(entity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	private String checkParam(SummaryEntity entity) {
		
		return null;
	}
	
	/**
	 * 修改时的参数校验
	 * @date 2018-11-16 15:55:08
	 * @author bo.liu01
	 */
	private String checkUpdateParam(SummaryEntity entity) {
		
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
	public ResultResponse update(@RequestBody SummaryEntity entity) {

		String checkRes = checkUpdateParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		int n = summaryService.update(entity);
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
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody SummaryVo vo) {

		int n = summaryService.delete(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
}