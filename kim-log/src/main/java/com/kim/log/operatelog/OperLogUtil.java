package com.kim.log.operatelog;

import java.lang.reflect.Field;
import java.util.List;

import com.kim.log.entity.OperateLogsEntity;
import com.kim.log.handler.FieldConvert;
import com.kim.log.handler.FieldConvertRegister;
import com.kim.log.handler.FieldDisplayHandler;
import com.kim.log.interceptor.OperateLogHandlerUtil;
import com.kim.log.interceptor.ParamSet;
import com.kim.log.service.OperateLogsService;
import com.kim.log.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.kim.common.context.ContextHolder;
import com.kim.common.exception.BusinessException;
import com.kim.common.util.BeanUtil;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.DateUtil;
import com.kim.common.util.HttpServletUtil;
import com.kim.common.util.StringUtil;
import com.kim.common.util.TokenUtil;


/**
 * 操作日志处理工具类
 * @date 2017年3月25日
 * @author liubo04
 */
public class OperLogUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(OperLogUtil.class);
	
	private static OperateLogsService<OperateLogsEntity> operateLogsService;
	
	@SuppressWarnings("unchecked")
	private static OperateLogsService<OperateLogsEntity> getOperateLogsService(){
		if(operateLogsService == null){
			operateLogsService = HttpServletUtil.getBean(OperateLogsService.class);
		}
		return operateLogsService;
	}
	
	/**
	 * 添加操作日志
	 * @param content
	 * @date 2017年4月26日
	 * @author liubo04
	 */
	public static void addLog(String content){
		addLogThreadLocal(content);
		insertLog();
	}
	
	/**
	 * 添加操作日志
	 * @param content
	 * @return
	 * @date 2017年3月26日
	 * @author liubo04
	 */
	public static boolean addLogThreadLocal(String content){
		ParamSet paramSet = OperateLogHandlerUtil.getCurrentParamSet();
		if(paramSet == null){
			logger.error("未开启操作日志记录功能");
			return false;
		}
		if(StringUtil.isBlank(content) && StringUtil.isBlank(paramSet.getExtraContent())){
			logger.error("日志内容为空");
			return false;
		}
		OperateLogsEntity operateLog = new OperateLogsEntity();
		operateLog.setIpAddress(HttpServletUtil.getIpAddress());
		operateLog.setModuleName(paramSet.getModule());
		operateLog.setOperationName(paramSet.getOper());
		StringBuilder contentHeader = new StringBuilder()
				.append("[").append(paramSet.getModule()).append("]")
				.append("[").append(paramSet.getOper()).append("]");
		if(StringUtil.isNotBlank(paramSet.getExtraContent())){
			contentHeader.append("[ext:").append(paramSet.getExtraContent()).append("]");
		}
		contentHeader.append(content);
		operateLog.setContent(contentHeader.toString());
		operateLog.setOperTime(DateUtil.getCurrentTime());
		operateLog.setOperUser(TokenUtil.getUsername());
		operateLog.setOperName(TokenUtil.getCurrentName());
		paramSet.setOperateLog(operateLog);
		return true;
	}
	
	/**
	 * 将操作日志保存
	 * @return
	 * @date 2017年4月8日
	 * @author liubo04
	 */
	public static boolean insertLog(){
		ParamSet paramSet = OperateLogHandlerUtil.getCurrentParamSet();
		if(paramSet == null){
			logger.info("未开启操作日志记录功能");
			return false;
		}
		if(paramSet.isSubmit()){
			return true;
		}
		OperateLogsEntity operateLog = paramSet.getOperateLog();
		if(operateLog == null){
			logger.debug("没有需要保存的操作日志");
			return false;
		}
		int n = getOperateLogsService().insert(operateLog);
		if(n > 0){
			paramSet.setSubmit(true);//已经提交过了
		}
		return n > 0;
	}
	
	/**
	 * 新增操作的日志
	 * @param obj 新增的记录
	 * @return
	 * @date 2017年3月26日
	 * @author liubo04
	 */
	public static boolean addInsertLog(Object obj){
		ParamSet paramSet = OperateLogHandlerUtil.getCurrentParamSet();
		if(paramSet == null){
			logger.error("未开启操作日志记录功能");
			return false;
		}
		if(obj == null){
			logger.error("日志处理对象为空");
			return false;
		}
		String[] priKeys = getPriKeys(obj, paramSet);
		List<Field> fields = fieldConvert.getFields(obj, priKeys, OperationType.INSERT);
		if(CollectionUtil.isEmpty(fields)){
			fields = BeanUtil.getBusinessFields(obj.getClass());
		}
		StringBuilder content = new StringBuilder("新增记录：{");

		buildContent(obj, fields, content);
		content.deleteCharAt(content.length()-1);
		content.append(" }");
		return addLogThreadLocal(content.toString());
	}

	private static void buildContent(Object obj, List<Field> fields, StringBuilder content) {
		for (Field field : fields) {
			Object v1 = null;
			if(FieldDisplayHandler.checkAnnoMapping(field, OperationType.INSERT)){
				v1 = fieldConvert.convertValue(obj, field);
			}else{
				v1 = BeanUtil.getValue(obj, field);
			}
			if(v1 == null){
				continue;
			}
			content.append("【").append(fieldConvert.convert(obj, field)).append("】:[").append(v1).append("];");
		}
	}

	/**
	 * 批量新增操作的日志
	 * @param objList 新增的记录
	 * @return
	 * @date 2017年3月26日
	 * @author liubo04
	 */
	public static boolean addInsertLogs(List<? extends Object> objList){
		ParamSet paramSet = OperateLogHandlerUtil.getCurrentParamSet();
		if(paramSet == null){
			logger.error("未开启操作日志记录功能");
			return false;
		}
		if(CollectionUtil.isEmpty(objList)){
			logger.error("日志处理对象为空");
			return false;
		}
		Object obj = objList.get(0);
		String[] priKeys = getPriKeys(obj , paramSet);
		List<Field> fields = fieldConvert.getFields(obj, priKeys, OperationType.INSERT);
		if(CollectionUtil.isEmpty(fields)){
			fields = BeanUtil.getBusinessFields(obj.getClass());
		}
		StringBuilder content = new StringBuilder("新增记录：[");
		for (Object _obj : objList) {
			StringBuilder _content = new StringBuilder();
			buildContent(_obj, fields, _content);
			if(_content.length() > 0){
				_content.deleteCharAt(_content.length()-1);
				content.append("{").append(_content).append(" }");
			}
			content.append(",");
		}
		content.deleteCharAt(content.length()-1);
		content.append("]");
		return addLogThreadLocal(content.toString());
	}

	private static String[] getPriKeys(Object obj, ParamSet paramSet) {
		String[] priKeys = paramSet.getPriKey();
//		if(priKeys == null || priKeys.length == 0){
//			List<Field> fds = BeanUtil.getBusinessFields(obj.getClass());
//			priKeys = new String[fds.size()];
//			int n = 0;
//			for (Field field : fds) {
//				priKeys[n++]=field.getName();
//			}
//		}
		return priKeys;
	}
	
	private static Class<?>[] getTypeClass(int length){
		Class<?>[] arr = new Class<?>[length];
		for (int i = 0; i < length; i++) {
			arr[i]=String.class;
		}
		return arr;
	}
	
	/**
	 * 修改操作的日志
	 * @param obj 修改后的新记录
	 * @return
	 * @date 2017年3月26日
	 * @author liubo04
	 */
	public static boolean addUpdateLog(Object obj){
		ParamSet paramSet = OperateLogHandlerUtil.getCurrentParamSet();
		if(paramSet == null){
			logger.error("未开启操作日志记录功能");
			return false;
		}
		if(obj == null){
			logger.error("日志处理对象为空");
			return false;
		}
		
		try {
			Object oldObj = getOrigObject(obj, paramSet);
			return addUpdateLog(oldObj, obj);
		} catch (Exception e) {
			logger.info("update object:{}", JSON.toJSONString(obj));
			logger.error(e.getMessage(), e);
			addErrOperLog();
			return false;
		}
		
	}

	private static void addErrOperLog() {
		addLogThreadLocal("记录操作日志时出现异常，详情请查看日志, TraceID："+ ContextHolder.getTraceId());
	}
	
	/**
	 * 批量修改操作的日志
	 * @param objList 修改后的新记录
	 * @return
	 * @date 2017年3月26日
	 * @author liubo04
	 */
	public static boolean addUpdateLogs(List<? extends Object> objList){
		ParamSet paramSet = OperateLogHandlerUtil.getCurrentParamSet();
		if(paramSet == null){
			logger.error("未开启操作日志记录功能");
			return false;
		}
		if(CollectionUtil.isEmpty(objList)){
			logger.error("日志处理对象为空");
			return false;
		}
		
		StringBuilder content = new StringBuilder("修改记录:[");
		try {
			for (Object obj : objList) {
				String con = ObjectUtil.compare(getOrigObject(obj , paramSet), obj);
				if(StringUtil.isNotBlank(con)){
					content.append("{").append(con).append(" }");
				}
				content.append(",");
			}
			content.deleteCharAt(content.length()-1);
			content.append("]");
			return addLogThreadLocal(content.toString());
		} catch (Exception e) {
			logger.info("update object list:{}", JSON.toJSONString(objList));
			logger.error(e.getMessage(), e);
			addErrOperLog();
			return false;
		}
		
	}
	
	private static FieldConvert fieldConvert = FieldConvertRegister.DEF.getFieldConvert();
	
	/**
	 * 修改操作的日志
	 * @param srcObj 修改前的原记录
	 * @param destObj 修改后的新记录
	 * @return
	 * @date 2017年3月26日
	 * @author liubo04
	 */
	public static boolean addUpdateLog(Object srcObj, Object destObj){
		
		return addLogThreadLocal("{"+ ObjectUtil.compare(srcObj, destObj)+" }");
	}
	
	/**
	 * 删除操作的日志
	 * @param obj 删除的原记录
	 * @return
	 * @date 2017年3月26日
	 * @author liubo04
	 */
	public static boolean addDeleteLog(Object obj){
		ParamSet paramSet = OperateLogHandlerUtil.getCurrentParamSet();
		if(paramSet == null){
			logger.error("未开启操作日志记录功能");
			return false;
		}
		if(obj == null){
			logger.error("日志处理对象为空");
			return false;
		}
		
		try {
			//obj = getOrigObject(obj, paramSet);
//			
//			String[] priKeys = getPriKeys(obj, paramSet);
//			
//			List<Field> fields = fieldConvert.getFields(obj, priKeys, OperationType.DELETE);
//			if(CollectionUtil.isEmpty(fields)){
//				fields = BeanUtil.getBusinessFields(obj.getClass());
//			}
			;
			StringBuilder content = new StringBuilder("删除记录：{");
			for (String key : paramSet.getConKey()) {
				content.append("【").append(key).append("】:[").append(BeanUtil.getValue(obj, key)).append("],");
			}
//			for (Field field : fields) {
//				Object v1 = null;
//				if(FieldDisplayHandler.checkAnnoMapping(field, OperationType.DELETE)){
//					v1 = fieldConvert.convertValue(obj, field);
//				}else{
//					v1 = BeanUtil.getValue(obj, field);
//				}
//				if(v1 == null){
//					continue;
//				}
//				content.append("【").append(fieldConvert.convert(obj, field)).append("】:[").append(v1).append("]; ");
//			}
			content.deleteCharAt(content.length()-1);
			content.append(" }");
			
			return addLogThreadLocal(content.toString());
		} catch (Exception e) {
			logger.info("delete object:{}", JSON.toJSONString(obj));
			logger.error(e.getMessage(), e);
			addErrOperLog();
			return false;
		}
		
	}
	
	/**
	 * 批量删除操作的日志
	 * @param objList 删除的原记录
	 * @return
	 * @date 2017年3月26日
	 * @author liubo04
	 */
	public static boolean addDeleteLogs(List<? extends Object> objList){
		ParamSet paramSet = OperateLogHandlerUtil.getCurrentParamSet();
		if(paramSet == null){
			logger.error("未开启操作日志记录功能");
			return false;
		}
		if(CollectionUtil.isEmpty(objList)){
			logger.error("日志处理对象为空");
			return false;
		}
		
		try {
			Object obj = objList.get(0);
			obj = getOrigObject(obj, paramSet);
			
			String[] priKeys = getPriKeys(obj, paramSet);
			
			List<Field> fields = fieldConvert.getFields(obj, priKeys, OperationType.DELETE);
			if(CollectionUtil.isEmpty(fields)){
				fields = BeanUtil.getBusinessFields(obj.getClass());
			}
			StringBuilder content = new StringBuilder("删除记录：[");
			for (Object _obj : objList) {
				StringBuilder _content = new StringBuilder();
				for (Field field : fields) {
					Object v1 = null;
					if(FieldDisplayHandler.checkAnnoMapping(field, OperationType.DELETE)){
						v1 = fieldConvert.convertValue(_obj, field);
					}else{
						v1 = BeanUtil.getValue(_obj, field);
					}
					if(v1 == null){
						continue;
					}
					_content.append("【").append(fieldConvert.convert(_obj, field)).append("】:[").append(v1).append("];");
				}
				if(_content.length() > 0){
					_content.deleteCharAt(_content.length()-1);
					content.append("{").append(_content).append(" }");
				}
				content.append(",");
			}
			content.deleteCharAt(content.length()-1);
			content.append("]");
			
			return addLogThreadLocal(content.toString());
		} catch (Exception e) {
			logger.info("delete object list:{}", JSON.toJSONString(objList));
			logger.error(e.getMessage(), e);
			addErrOperLog();
			return false;
		}
	}

	/**
	 * 根据条件获取旧数据
	 * @param obj
	 * @param paramSet
	 * @return
	 * @date 2017年6月23日
	 * @author liubo04
	 */
	private static Object getOrigObject(Object obj, ParamSet paramSet) {
		Object oldObj = null;
		try {
			if(StringUtil.isNotBlank(paramSet.getBeanAndMethod())){
				oldObj = BeanUtil.getMethodInvokeByBeanId(paramSet.getBeanAndMethod(), 
						BeanUtil.getValues(obj, paramSet.getConKey()), getTypeClass(paramSet.getConKey().length));
				
			}else if(paramSet.getBeanType() != null && StringUtil.isNotBlank(paramSet.getMethodName())){
				oldObj = BeanUtil.getMethodInvokeByBeanType(paramSet.getBeanType(), paramSet.getMethodName(), 
						BeanUtil.getValues(obj, paramSet.getConKey()), getTypeClass(paramSet.getConKey().length));
				
			}else{
				logger.error("logging parameter is missing, paramSet:{}", JSON.toJSONString(paramSet));
				return null;
			}
		} catch (Exception e) {
			logger.error("操作日志记录获取原数据时异常, 记录操作日志失败", e);
			return null;
		}
		if(oldObj == null){
			logger.error("要删除的原数据不存在");
			throw new BusinessException("该记录未找到");
		}
		return oldObj;

	}
	
	public static boolean addOtherLog(Object obj){
		ParamSet paramSet = OperateLogHandlerUtil.getCurrentParamSet();
		if(paramSet == null){
			logger.error("未开启操作日志记录功能");
			return false;
		}
		return addLogThreadLocal(null);
	}

}
