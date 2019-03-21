package com.kim.admin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kim.admin.common.AdminModule;
import com.kim.admin.common.AdminOperation;
import com.kim.admin.common.BeanConfig;
import com.kim.admin.entity.UserEntity;
import com.kim.admin.service.UserService;
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
import com.kim.admin.vo.UserVo;
import com.kim.common.base.BaseController;
import com.kim.common.constant.CommonConstants;
import com.kim.common.exception.BusinessException;
import com.kim.common.response.MsgCode;
import com.kim.common.response.MsgResponse;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.response.TableResponse;
import com.kim.common.util.BeanUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.DateUtil;
import com.kim.common.util.FileUtil;
import com.kim.common.util.HttpServletUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;
import com.kim.common.validation.CommonReg;
import com.kim.common.validation.ValidateEntity;
import com.kim.common.validation.ValidateUtil;
import com.kim.impexp.common.ImpexpBeanConfig;
import com.kim.impexp.excel.entity.ImportResult;
import com.kim.impexp.excel.exe.AbstractExcelImpExecutor;
import com.kim.impexp.excel.exe.ExcelImpExecutor;
import com.kim.impexp.excel.exe.ExcelImportUtil;
import com.kim.impexp.excel.exp.handler.AbstractObjectContentWriterHandler;
import com.kim.impexp.excel.exp.handler.ObjectWriterHandler;
import com.kim.impexp.excel.validation.Verifier;
import com.kim.impexp.excel.validation.VerifierAdaptor;
import com.kim.impexp.util.DownloadUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;

/**
 * 用户表控制类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
@OperateLog(module = AdminModule.USER)
@Controller
@RequestMapping("user")
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private BeanConfig beanConfig;
	@Autowired
	private ImpexpBeanConfig impexpBeanConfig;

	/**
	 * 分页查询
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.QUERY_BY_PAGE, parameterType= ParameterType.QUERY, injectCurntTenant=false)
	@ResponseBody
	@RequestMapping(value="listByPage", method=RequestMethod.GET)
	public ResultResponse listByPage(UserVo userVo){
		
		String tenantId = TokenUtil.getTenantId();
		if(!CommonConstants.DEFAULT_TENANT_ID.equals(tenantId)){
			userVo.setTenantId(tenantId);
		}
		
		return new TableResponse(userService.listByPage(userVo));
	}
	
	/**
	 * 不带分页的查询（此方法使用时请注意性能）,不包含登录信息，可以根据部门ID查询
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= AdminOperation.PAGE_DEPART_USER, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByDepart", method=RequestMethod.GET)
	public ResultResponse listByDepart(UserVo userVo){

		if(StringUtil.isBlank(userVo.getDepartmentCode())){
			return new TableResponse();
		}
		return new TableResponse(userService.listByDepartment(userVo));
	}
	
	/**
	 * 根据部门查询不在该部门的人员
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= AdminOperation.PAGE_DEPART_USER_, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listDepartUser_", method=RequestMethod.GET)
	public ResultResponse listDepartUser_(UserVo userVo){

		if(StringUtil.isBlank(userVo.getDepartmentCode())){
			return new TableResponse();
		}
		return new TableResponse(userService.listDepartUser_(userVo));
	}
	
	/**
	 * 不带分页的查询（此方法使用时请注意性能）,不包含登录信息，可以根据部门ID查询
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= AdminOperation.PAGE_ROLE_USER, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByRole", method=RequestMethod.GET)
	public ResultResponse listByRole(UserVo userVo){

		if(StringUtil.isBlank(userVo.getRoleCode())){
			return new TableResponse();
		}
		return new TableResponse(userService.listByRole(userVo));
	}
	
	/**
	 * 根据部门查询不在该部门的人员
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= AdminOperation.PAGE_ROLE_USER_, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByRole_", method=RequestMethod.GET)
	public ResultResponse listByRole_(UserVo userVo){

		if(StringUtil.isBlank(userVo.getRoleCode())){
			return new TableResponse();
		}
		return new TableResponse(userService.listByRole_(userVo));
	}

	/**
	 * 根据小组查询该小组的人员
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= AdminOperation.PAGE_GROUP_USER, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByGroup", method=RequestMethod.GET)
	public ResultResponse listByGroup(UserVo userVo){

		if(StringUtil.isBlank(userVo.getGroupCode())){
			return new TableResponse();
		}
		return new TableResponse(userService.listByGroup(userVo));
	}
	/**
	 * 根据质检小组查询不在该小组的人员
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= AdminOperation.PAGE_GROUP_USER_, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="listByGroup_", method=RequestMethod.GET)
	public ResultResponse listByGroup_(UserVo userVo){
		
		if(StringUtil.isBlank(userVo.getGroupCode())){
			return new TableResponse();
		}
		return new TableResponse(userService.listByGroup_(userVo));
	}

	/**
	 * 查询单条记录，默认根据ID查询，如有修改条件，请在此备注
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper=Operation.SINGLE_QUERY, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="find", method=RequestMethod.GET)
	public ResultResponse find(UserVo userVo){

		UserEntity userEntity = userService.find(userVo);
		if(userEntity != null){
			return new ObjectResponse(true).data(userEntity);
		}else{
			return new ObjectResponse(false).msg("该记录不存在");
		}
	}

	/**
	 * 查询用户名是否唯一
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= AdminOperation.CHECK_UNIQUE_USERNAME, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="checkUnique", method=RequestMethod.GET)
	public ResultResponse checkUnique(String username){

		UserVo userVo = new UserVo();
		userVo.setUsername(username);
		return new ObjectResponse(true).data(userService.find(userVo) != null);
	}

	/**
	 * 新增时的参数校验
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	private String checkAddParam(UserEntity userEntity) {
		
		return checkParam(userEntity);
	}
	
	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.ADD, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="add", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse add(@RequestBody UserEntity userEntity) {

		String checkRes = checkAddParam(userEntity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		userEntity.setOrigins(UserEntity.ORIGINS_DEFUALT);
		String curTenantId = TokenUtil.getTenantId();
		if(StringUtil.isNotBlank(curTenantId)){
			userEntity.setTenantId(curTenantId);
		}
		userEntity = userService.insert(userEntity);
		return new MsgResponse().rel(userEntity != null);
	}
	
	/**
	 * 新增和修改时的共用参数校验
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	private String checkParam(UserEntity userEntity) {
		if(StringUtil.isNotBlank(userEntity.getLeaveDate()) 
				&& StringUtil.isNotBlank(userEntity.getJoinDate())){
			Date joinDate = DateUtil.parseDate_YYYY_MM_DD(userEntity.getJoinDate());
			Date leaveDate = DateUtil.parseDate_YYYY_MM_DD(userEntity.getLeaveDate());
			if(leaveDate.getTime() < joinDate.getTime()){
				return "离职时间不能在入职时间之前";
			}
		}
		return null;
	}
	
	/**
	 * 修改时的参数校验
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	private String checkUpdateParam(UserEntity userEntity) {
		
		return checkParam(userEntity);
	}
	
	/**
	 * 修改记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.UPDATE, operType = OperationType.UPDATE, beanType=UserService.class,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="update", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse update(@RequestBody UserEntity userEntity) {

		String checkRes = checkUpdateParam(userEntity);
		if(checkRes != null){
			logger.error(checkRes);
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		if(StringUtil.isNotBlank(userEntity.getPath())){
			//解密保存明文头像
			String portrait = DownloadUtil.decode(userEntity.getPath(),userEntity.getSign());
			userEntity.setPortrait(portrait);
		}
		int n = userService.update(userEntity);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("该记录不存在或已删除");
	}
	
	/**
	 * 根据ID删除记录，默认为逻辑删除
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DELETE, operType = OperationType.DELETE,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="delete", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse delete(@RequestBody UserVo userVo) {

		if(StringUtil.isBlank(userVo.getUsernames())){
			logger.error("usernames为空");
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg("请选择要删除的用户");
		}
		int n = userService.delete(userVo);
		return n > 0 ? new MsgResponse(MsgCode.SUCCESS) : new MsgResponse(MsgCode.FAIL).msg("记录不存在或已删除");
	}
	
	/**
	 * 下载
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DOWNLOAD, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="download", method=RequestMethod.GET)
	public ResultResponse download(UserVo userVo){
		
		List<UserEntity> list = userService.list(userVo);
		
		return new ObjectResponse(true).data(DownloadUtil.simpleDownload("用户数据导出", header, getBodyWriterHandler(list)));
	}
	
	/**
	 * 导入时的表头
	 */
	private String[] header = new String[]{"工号", "姓名", "邮箱", "入职日期", "离职日期", "思科工号"};
	private String[] headerMapping = new String[]{
			"工号=>username", 
			"姓名=>name", 
			"邮箱=>email", 
			"入职日期=>joinDate",
			"离职日期=>leaveDate",
			"思科工号=>ciscoAgentNo"
	};
	/**
	 * 模板文件数据
	 */
	private JSONObject templateData;
	
	public List<ValidateEntity> getValidator() {
		List<ValidateEntity> list = new ArrayList<>();
		list.add(new ValidateEntity("username", "工号", true, 50));
		list.add(new ValidateEntity("name", "姓名", true, 50));
		list.add(new ValidateEntity("email", "邮箱", 50));
		list.add(new ValidateEntity("joinDate", "入职日期", CommonReg.DATE_DATETIME_STR_REG, "入职日期时间格式不正确, 2018-02-05格式").setRequire(true));
		list.add(new ValidateEntity("leaveDate", "离职日期", CommonReg.DATE_DATETIME_STR_REG, "入职日期时间格式不正确, 2018-02-05格式"));
		list.add(new ValidateEntity("ciscoAgentNo", "思科工号", CommonReg.getnMNumberReg(1, 10), "思科工号必须为10位以下长度的数字"));
		return list;
	}
	
	/**
	 * 下载模板
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 */
	@OperateLog(oper= Operation.DOWNLOAD_TEMP, parameterType= ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="downloadTemp", method=RequestMethod.GET)
	public ResultResponse downloadTemp(){
		
		if(templateData == null){
			templateData = DownloadUtil.simpleDownload("用户导入模板", header, getBodyWriterHandler(new ArrayList<>()));
		}
		return new ObjectResponse(true).data(templateData);
	}

	private AbstractObjectContentWriterHandler<UserEntity> getBodyWriterHandler(List<UserEntity> list) {
		return new AbstractObjectContentWriterHandler<UserEntity>(list){
			@Override
			public Object[] java2ObjectArr(UserEntity t) {
				return new Object[]{t.getUsername(), t.getName(), t.getEmail(), 
						t.getJoinDate(), t.getLeaveDate(), t.getCiscoAgentNo()};
			}
		};
	}
	
	private String checkAddParam(List<String> files) {
		if(CollectionUtil.isEmpty(files) || StringUtil.isBlank(files.get(0))){
			return "请选择文件";
		}
		if(!files.get(0).endsWith(ExcelImpExecutor.EXCEL_2007_$_SUFFIX)){
			return "请选择2007及以上版本的Excel文件";
		}
		File file = new File(files.get(0));
		if(file.length() > impexpBeanConfig.getImportMaxFileByteSize()){
			return String.format("一次最多只能上传%sM以内的Excel文件", impexpBeanConfig.getImportBufferSize());
		}
		
		return null;
	}
	
	/**
	 * 新增记录
	 * @date 2017-11-6 14:06:20
	 * @author bo.liu01
	 * @return
	 */
	@OperateLog(oper= Operation.IMPORT, operType = OperationType.INSERT,
			parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="importData", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse importData(@RequestParam("file") MultipartFile[] file) {

		List<String> files = HttpServletUtil.fileStreamToFile(beanConfig.getImportTmpFilePath(), file);
		String checkRes = checkAddParam(files);
		if(checkRes != null){
			logger.error(checkRes);
			if(CollectionUtil.isNotEmpty(files)){
				FileUtil.deleteFile(files.get(0));
			}
			return new MsgResponse(MsgCode.INVALID_PARAMETER).msg(checkRes);
		}
		
		try {
			return new ObjectResponse(true).data(importData(files.get(0)));
		} catch (BusinessException e) {
			logger.error("导入用户数据出错, errMsg:{}", e.getMessage());
			FileUtil.deleteFile(files.get(0));
			return new MsgResponse(e);
		} catch (Exception e) {
			logger.error("导入用户数据出错, errMsg:{}", e.getMessage());
			logger.error(e.getMessage(), e);
			FileUtil.deleteFile(files.get(0));
			return new MsgResponse().rel(false).msg("导入数据时出现了异常");
		}
	}
	
	public ImportResult importData(String fileName){
		
		List<ValidateEntity> validatorList = getValidator();
		Verifier<UserEntity> verifier = new VerifierAdaptor<UserEntity>(){
			
			@Override
			public boolean check(UserEntity entity) {
				String res = ValidateUtil.checkEntity(entity, validatorList);
				if(res != null){
					entity.addRes(res);
				}
				return StringUtil.isBlank(entity.getImportRes());
			}
		};
		
		ImportResult res = new ImportResult();
		AbstractExcelImpExecutor<UserEntity> executor = getExecutor(headerMapping, res);
		
		executor.setClazz(UserEntity.class);
		String path = ExcelImportUtil.importData(fileName, executor , verifier);
		res.setPath(DownloadUtil.encode(path));
		res.setSign(DownloadUtil.sign(path));
		res.setFail(executor.getDataList().size()- res.getSuccess());
		return res;
	}
	
	public AbstractExcelImpExecutor<UserEntity> getExecutor(String[] headerMapping, final ImportResult res) {
		return new AbstractExcelImpExecutor<UserEntity>(headerMapping, UserEntity.class) {

			@Override
			public List<UserEntity> execute(List<UserEntity> list) {
				userService.importBatch(list);
				int n = 0;
				for (UserEntity user : list) {
					if(StringUtil.isBlank(user.getImportRes())){
						n++;
					}
				}
				res.setSuccess(n);
				return list;
			}
			
			@Override
			public ObjectWriterHandler<UserEntity> getUserBodyWriterHandler(List<UserEntity> list, final String[] fields) {
				if(bodyWriterHandler != null){
					return bodyWriterHandler;
				}
				return bodyWriterHandler = new AbstractObjectContentWriterHandler<UserEntity>(list) {
					
					@Override
					public Object[] java2ObjectArr(UserEntity t) {
						try {
							Object[] r = new Object[fields.length];
							for (int j = 0; j < fields.length; j++) {
								if(StringUtil.equals(fields[j], "importRes")){
									r[j] = t.getImportRes();
								}else{
									r[j] = BeanUtil.getValue(t, fields[j]);
								}
							}
							return r;
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
							throw new RuntimeException("导出结果失败");
						}
					}
				};
			}
		};
	}
	
	@OperateLog(oper= AdminOperation.FIND_CUR_INFO, parameterType=ParameterType.QUERY)
	@ResponseBody
	@RequestMapping(value="getCurInfo", method=RequestMethod.GET)
	public ResultResponse getCurInfo(){

		return new ObjectResponse(true).data(TokenUtil.getCurInfo());
	}
	
	/**
	 * @desc: 上传用户头像
	 * @param: [file]
	 * @return: ResultResponse
	 * @auther: yonghui.wu
	 * @date: 2018/9/18 16:26
	 */
	@OperateLog(oper= Operation.UPLOAD, operType = OperationType.INSERT,parameterType= ParameterType.BODY)
	@ResponseBody
	@RequestMapping(value="uploadPortrait", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResultResponse uploadPortrait(@RequestParam("file") MultipartFile[] file) {
		List<String> files = HttpServletUtil.fileStreamToFile(beanConfig.getPortraitTmpPath(), file);
		try {
			String path = files.get(0);
			userService.uploadPortrait(TokenUtil.getUsername(), path);
			ImportResult res = new ImportResult();
			res.setPath(DownloadUtil.encode(path));
			res.setSign(DownloadUtil.sign(path));
			return new ObjectResponse(true).data(res);
		} catch (BusinessException e) {
			logger.error("上传头像数据出错, errMsg:{}", e.getMessage());
			FileUtil.deleteFile(files.get(0));
			return new MsgResponse(e);
		} catch (Exception e) {
			logger.error("上传头像数据出错, errMsg:{}", e.getMessage());
			logger.error(e.getMessage(), e);
			FileUtil.deleteFile(files.get(0));
			return new MsgResponse().rel(false).msg("导入数据时出现了异常");
		}

	}

}