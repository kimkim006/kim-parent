package com.kim.quality.business.controller;

import java.util.List;

import com.kim.quality.common.BeanConfig;
import com.kim.quality.common.QualityModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kim.common.base.BaseController;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.response.TableResponse;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.FileUtil;
import com.kim.common.util.HttpServletUtil;
import com.kim.common.util.TokenUtil;
import com.kim.impexp.util.DownloadUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.quality.business.entity.AttachmentEntity;
import com.kim.quality.business.service.AttachmentService;
import com.kim.quality.business.vo.AttachmentVo;

/**
 * 附件信息表控制类
 * @date 2018-12-7 11:29:20
 * @author liubo
 */
@OperateLog(module = QualityModule.ATTACHMENT)
@Controller
@RequestMapping("attachment")
public class AttachmentController extends BaseController {
	
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private BeanConfig beanConfig;
	   
	/**
	 * 分页查询
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY)
	@ResponseBody
	//@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(AttachmentVo vo){
		
		return new TableResponse(attachmentService.listByPage(vo));
	}
	
	/**
	 * 不带分页的查询（此方法使用时请注意性能）
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 */
	/* 
	@OperateLog(oper= Operation.QUERY, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="list", method=RequestMethod.GET)
	public ResultResponse list(AttachmentVo vo){
		
		return new ObjectResponse(true).data(attachmentService.list(vo));
	}
	*/
	
	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	//@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(AttachmentVo vo){

		AttachmentEntity entity = attachmentService.find(vo);
		if(entity != null){
			return new ObjectResponse(true).data(entity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}
	
	/**
	 * 新增时的参数校验
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 * @param tenantId 
	 */
	private String checkAddParam(List<String> files, String tenantId) {
//		if(CollectionUtil.isEmpty(files) || StringUtil.isBlank(files.get(0))){
//			return "请选择文件";
//		}
//		String fileName = files.get(0);
//		String allowType = BaseCacheUtil.getParam(CommonConstant.ATTACHMENT_FILE_ALLOW_TYPE_KEY, tenantId);
//		if(StringUtil.isNotBlank(allowType) 
//			&& !ArraysUtil.contains(allowType.split(","), FileUtil.getFileSuffix(fileName))){
//			return String.format("请选择以(%s)结尾后缀的文件", allowType);
//		}
//		File file = new File(fileName);
//		String maxStr = BaseCacheUtil.getParam(CommonConstant.ATTACHMENT_FILE_MAX_KEY, tenantId);
//		int max = CommonConstant.ATTACHMENT_FILE_MAX;
//		if(StringUtil.isNotBlank(maxStr) && NumberUtil.isNumber(maxStr)){
//			max = Integer.parseInt(maxStr);
//		}
//		long maxLength = max * 1048576L;
//		if(file.length() > maxLength){
//			return String.format("一次最多只能上传%sM以内的附件文件", max);
//		}
		return null;
	}
	
	/**
	 * 新增记录
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	//@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestParam("file") MultipartFile[] file) {

		String tenantId = TokenUtil.getTenantId();
		List<String> files = HttpServletUtil.fileStreamToFile(beanConfig.getFileAttaDir(), file);
		String checkRes = checkAddParam(files, tenantId);
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
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2018-12-7 11:29:20
	 * @author liubo
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody AttachmentVo vo) {

		int n = attachmentService.delete(vo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
}