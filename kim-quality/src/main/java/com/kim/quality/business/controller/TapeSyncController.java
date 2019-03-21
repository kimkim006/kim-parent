package com.kim.quality.business.controller;

import com.kim.quality.business.entity.TapeSyncEntity;
import com.kim.quality.business.service.TapeSyncService;
import com.kim.quality.business.vo.TapeSyncVo;
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
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;

/**
 * 录音转储记录表控制类
 * @date 2018-8-21 14:48:54
 * @author bo.liu01
 */
@OperateLog(module = QualityModule.TAPE_SYNC)
@Controller
@RequestMapping("tapeSync")
public class TapeSyncController extends BaseController {
	
	@Autowired
	private TapeSyncService tapeSyncService;
	   
	/**
	 * 分页查询
	 * @date 2018-8-21 14:48:54
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(TapeSyncVo vo){
		
		return new TableResponse(tapeSyncService.listByPage(vo));
	}
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-8-21 14:48:54
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(TapeSyncVo vo){

		TapeSyncEntity entity = tapeSyncService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
}