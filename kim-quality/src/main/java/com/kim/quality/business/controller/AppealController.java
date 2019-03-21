package com.kim.quality.business.controller;

import java.io.File;
import java.util.List;

import com.kim.quality.business.service.AppealService;
import com.kim.quality.common.BeanConfig;
import com.kim.quality.common.QualityModule;
import com.kim.quality.common.QualityOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.kim.base.service.BaseCacheUtil;
import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.response.TableResponse;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.FileUtil;
import com.kim.common.util.HttpServletUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.impexp.util.DownloadUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.quality.business.entity.AppealEntity;
import com.kim.quality.business.entity.AttachmentEntity;
import com.kim.quality.business.service.AttachmentService;
import com.kim.quality.business.vo.AppealVo;
import com.kim.quality.common.CommonConstant;

import jodd.util.ArraysUtil;

/**
 * 申诉记录表控制类
 * @date 2018-9-19 10:11:34
 * @author bo.liu01
 */
@OperateLog(module = QualityModule.APPEAL)
@Controller
@RequestMapping("appeal")
public class AppealController extends BaseController {
	
	@Autowired
	private AppealService appealService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private BeanConfig beanConfig;
	   
	/**
	 * 分页查询
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(AppealVo vo){
		
		return new TableResponse(appealService.listByPage(vo));
	}
	
	/**
	 * 分页查询当前用户的申诉单
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	@OperateLog(oper= QualityOperation.QUERY_CUR_APPEAL, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listCurByPage", method=RequestMethod.GET)
	public ResultResponse listCurByPage(AppealVo vo){
		
		vo.setAppealer(vo.getOperUser());
		return new TableResponse(appealService.listByPage(vo));
	}
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(AppealVo vo){

		AppealEntity entity = appealService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该申诉记录不存在, id:"+vo.getId());
		}
	}
	
	@OperateLog(oper=QualityOperation.FIND_ATTACHMENT_CONFIG, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="findOpt", method=RequestMethod.GET)
	public ResultResponse findOpt(){

		String tenantId = TokenUtil.getTenantId();
		JSONObject opt = new JSONObject();
		opt.put("tenantId", tenantId);
		opt.put("accept", BaseCacheUtil.getParam(CommonConstant.APPEAL_ATTACHMENT_ALLOW_TYPE_KEY, tenantId, 
				CommonConstant.APPEAL_ATTACHMENT_ALLOW_TYPE_DEFAULT));
		opt.put("limitSize", BaseCacheUtil.getParam(CommonConstant.APPEAL_ATTACHMENT_FILE_MAX_KEY, tenantId, 
				CommonConstant.APPEAL_ATTACHMENT_FILE_MAX_DEFAULT));
		opt.put("limit", BaseCacheUtil.getParam(CommonConstant.APPEAL_ATTACHMENT_LIMIT_KEY, tenantId, 
				CommonConstant.APPEAL_ATTACHMENT_LIMIT_DEFAULT));
		
		return new ObjectResponse(true).data(opt);
	}
	
	/**
	 * 新增记录
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 * @return
	 */
	@OperateLog(oper= QualityOperation.ADD_ATTACHMENT, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="addAttachment", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse addAttachment(@RequestParam("file") MultipartFile[] file) {

		String tenantId = TokenUtil.getTenantId();
		List<String> files = HttpServletUtil.fileStreamToFile(beanConfig.getFileAttaDir(), file);
		String checkRes = checkAddAttaParam(files, tenantId);
		if(checkRes != null){
			logger.error(checkRes);
			if(CollectionUtil.isNotEmpty(files)){
				FileUtil.deleteFile(files.get(0));
			}
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		
		String path = files.get(0);
		AttachmentEntity entity = new AttachmentEntity().tenantId(tenantId);
		entity.setPath(path);
		entity.setFileName(FileUtil.getFileName(path));
		entity.setType(FileUtil.getFileSuffix(path));
		entity.setOperUser(TokenUtil.getUsername());
		entity.setOperName(TokenUtil.getCurrentName());
		entity = attachmentService.insert(entity);
		
		entity.setF(DownloadUtil.encode(entity.getPath()));
		entity.setS(DownloadUtil.sign(entity.getPath()));
		return new ObjectResponse(true).data(entity);
	}
	
	private String checkAddAttaParam(List<String> files, String tenantId) {
		if(CollectionUtil.isEmpty(files) || StringUtil.isBlank(files.get(0))){
			return "请选择文件";
		}
		String fileName = files.get(0);
		String allowType = BaseCacheUtil.getParam(CommonConstant.APPEAL_ATTACHMENT_ALLOW_TYPE_KEY, tenantId, 
				CommonConstant.APPEAL_ATTACHMENT_ALLOW_TYPE_DEFAULT);
		if(!ArraysUtil.contains(allowType.split(","), FileUtil.getFileSuffix(fileName))){
			return String.format("请选择以(%s)结尾后缀的文件", allowType);
		}
		File file = new File(fileName);
		int max = BaseCacheUtil.getParam(CommonConstant.APPEAL_ATTACHMENT_FILE_MAX_KEY, tenantId, 
				CommonConstant.APPEAL_ATTACHMENT_FILE_MAX_DEFAULT);
		long maxLength = max * CommonConstant.ONE_M;
		if(file.length() > maxLength){
			return String.format("一次最多只能上传%sM以内的附件文件", max);
		}
		return null;
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 */
	private String checkAddParam(AppealEntity entity) {
		if(StringUtil.isBlank(entity.getTaskId())){
			return "任务id不能为空";
		}
		if(StringUtil.isBlank(entity.getContent())){
			return "申诉理由不能为空";
		}
		return null;
	}
	
	/**
	 * 新增记录
	 * @date 2018-9-19 10:11:34
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody AppealEntity entity) {

		String checkRes = checkAddParam(entity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		appealService.insert(entity);
		return new MsgResponse().rel(true);
	}
	
	@OperateLog(oper=QualityOperation.CALC_LAST, parameterType=ParameterType.QUERY, 
			logOperation=false, injectCurntTenant=false,
			injectCurntDate=false, injectCurntUser=false)
	@ResponseBody
	@RequestMapping(value="calcLast", method=RequestMethod.GET)
	public ResultResponse calcLast(AppealVo vo){

		appealService.calcLast(vo);
		return new MsgResponse().rel(true);
	}
	
}