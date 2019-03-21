package com.kim.quality.business.controller;

import com.kim.quality.business.entity.TapeEntity;
import com.kim.quality.business.vo.TapeVo;
import com.kim.quality.common.QualityModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.common.base.BaseController;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.response.TableResponse;
import com.kim.common.util.StringUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.quality.business.service.TapeService;

/**
 * 录音池表控制类
 * @date 2018-8-16 18:34:17
 * @author bo.liu01
 */
@OperateLog(module = QualityModule.TAPE)
@Controller
@RequestMapping("tape")
public class TapeController extends BaseController {
	
	@Autowired
	private TapeService tapeService;
	   
	/**
	 * 分页查询
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(TapeVo vo){
		if(StringUtil.isNotBlank(vo.getEndTime())){
			vo.setEndTime(vo.getEndTime().subSequence(0, 10)+ " 23:59:59");
		}
		return new TableResponse(tapeService.listByPage(vo));
	}
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-8-16 18:34:17
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(TapeVo vo){

		TapeEntity entity = tapeService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
}