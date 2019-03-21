package com.kim.quality.business.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kim.quality.business.enums.MainTaskStatusEnum;
import com.kim.quality.business.service.RecycleService;
import com.kim.quality.business.vo.RecycleDetailVo;
import com.kim.quality.common.QualityModule;
import com.kim.quality.common.QualityOperation;
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
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.quality.business.service.RecycleDetailService;
import com.kim.quality.business.vo.RecycleVo;

/**
 * 任务回收记录表控制类
 * @date 2018-9-10 10:10:11
 * @author bo.liu01
 */
@OperateLog(module = QualityModule.RECYCLE)
@Controller
@RequestMapping("recycle")
public class RecycleController extends BaseController {
	
	@Autowired
	private RecycleService recycleService;
	@Autowired
	private RecycleDetailService recycleDetailService;
	   
	/**
	 * 分页查询
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(RecycleVo vo){
		
		return new TableResponse(recycleService.listByPage(vo));
	}
	
	@OperateLog(oper= QualityOperation.QUERY_RECYCLE_USER, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listUserByBacth", method=RequestMethod.GET)
	public ResultResponse listUserByBacth(RecycleVo vo){
		
		return new TableResponse(recycleService.listUserByBacth(vo));
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2018-9-10 10:10:11
	 * @author bo.liu01
	 */
	private String checkParam(RecycleVo entity) {
		
		return null;
	}
	
	/**
	 * 分配任务
	 * @date 2018-9-10 10:10:10
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= QualityOperation.RECYCLE_EXE, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="exe", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse exe(@RequestBody RecycleVo vo) {

		String checkRes = checkParam(vo);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		return new ObjectResponse(true).data(recycleService.exe(vo));
	}
	/**
	 * @desc: 分页查询详情列表
	 * @param: [vo]
	 * @return: com.kim.common.response.ResultResponse
	 * @auther: yonghui.wu
	 * @date: 2018/9/12 13:59
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="detailListByPage", method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse detailListByPage(RecycleDetailVo vo){

		return new TableResponse(recycleDetailService.listByPage(vo));
	}
	/**
	 * @desc: 查询枚举状态key/value值
	 * @param: [vo]
	 * @return: com.kim.common.response.ResultResponse
	 * @auther: yonghui.wu
	 * @date: 2018/9/14 13:23
	 */
	@OperateLog(oper= Operation.QUERY, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="statusList", method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse statusList(RecycleDetailVo vo){

		List<Map<String,String>> mapList = new ArrayList<>();
		Map<String,String> map = new HashMap<>();
		map.put("value",null);
		map.put("label","全部");
		mapList.add(map);
		for(MainTaskStatusEnum s:MainTaskStatusEnum.values()){
			map = new HashMap<>();
			map.put("value", String.valueOf(s.getKey()));
			map.put("label", s.getValue());
			mapList.add(map);
		}
		return new ObjectResponse(true).data(mapList);
	}
	
}