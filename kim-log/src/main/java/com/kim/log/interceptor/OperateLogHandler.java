package com.kim.log.interceptor;

import com.alibaba.fastjson.JSON;
import com.kim.log.operatelog.OperModule;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.log.annotation.OperateLog;
import com.kim.log.operatelog.OperLogUtil;
import com.kim.common.util.BeanUtil;
import com.kim.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class OperateLogHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(OperateLogHandler.class);
	
	/**
	 * 保存到数据库日志表
	 * @date 2017年4月26日
	 * @author liubo04
	 */
	public static void handOperLogSubmit(){
		OperLogUtil.insertLog();
	}
	
	/**
	 * 处理操作日志收集
	 * @param paramSet
	 * @date 2017年3月28日
	 * @author liubo04
	 */
	public static void handOperationLog(ParamSet paramSet) {
		if(!paramSet.isLogOperation()){
			logger.debug("未开启记录操作日志");
			return ;
		}
		
//		JSONObject paramJson = null;
//		if(paramSet.getParameterTypeList().contains(ParameterType.BODY)){
//			paramJson = JSON.parseObject(paramSet.getBodyParam());
//		}else{
//			paramJson = JSON.parseObject(paramSet.getQueryParam());
//		}
		
		Object[] pms = paramSet.getMethodParams();
		if(pms == null || pms.length == 0){
			logger.debug("方法无参数，不支持自动操作日志收集，请手动收集");
			return;
		}
		if(pms.length > 1){
			logger.warn("方法有多个参数，注意参数的顺序，自动操作日志只取第一个参数作为操作日志对象数据源");
		}
		
//		Object obj = JSON.toJavaObject(paramJson, pms[0].getClass());
		Object obj = pms[0];
		
		switch(paramSet.getOperType()){
		case SAVE:
			addSaveLog(paramSet, obj);break;
		case INSERT:
			OperLogUtil.addInsertLog(obj);break;
		case UPDATE:
			OperLogUtil.addUpdateLog(obj);break;
		case DELETE:
			OperLogUtil.addDeleteLog(obj);break;
		case OTHER:
			OperLogUtil.addOtherLog(obj);break;
		default:logger.debug("暂不支持查询操作日志记录");
		}
		
	}
	
	private static void addSaveLog(ParamSet paramSet, Object obj){
		Object val = BeanUtil.getValue(obj, paramSet.getSaveLable());
		if(val == null || StringUtil.isBlank(val.toString())){
			paramSet.setOper(Operation.ADD);
			paramSet.setOperType(OperationType.INSERT);
			OperLogUtil.addInsertLog(obj);
		}else{
			paramSet.setOper(Operation.UPDATE);
			paramSet.setOperType(OperationType.UPDATE);
			OperLogUtil.addUpdateLog(obj);
		}
	}
	
	/**
	 * 合并方法和类上面的注解
	 * @param clazzOperateLog
	 * @param methodOperateLog
	 * @return
	 * @date 2017年3月28日
	 * @author liubo04
	 */
	protected static ParamSet mergeOperateLog(OperateLog clazzOperateLog, OperateLog methodOperateLog){
		if(methodOperateLog == null){
			return null;
		}
		if(clazzOperateLog == null){
			return operateLogConvert(methodOperateLog);
		}
		ParamSet paramSet = operateLogConvert(methodOperateLog);
		if(StringUtil.equalsIgnoreCase(OperModule.UNDEFINED, methodOperateLog.module())){
			paramSet.setModule(clazzOperateLog.module());
		}
		return paramSet;
	}
	
	private static ParamSet operateLogConvert(OperateLog operateLog){
		
		ParamSet paramSet = new ParamSet();
		paramSet.setModule(operateLog.module());
		paramSet.setOper(operateLog.oper());
		paramSet.setOperType(operateLog.operType());
		paramSet.setLevel(operateLog.level());
		paramSet.setParameterTypeList(Arrays.asList(operateLog.parameterType()));
		paramSet.setObjStr(JSON.toJSONString(operateLog.obj()));
		paramSet.setPrintResData(operateLog.printResData());
		paramSet.setAutoPrint(operateLog.autoPrint());
		
		paramSet.setLogOperation(operateLog.logOperation());
		paramSet.setBeanAndMethod(operateLog.beanAndMethod());
		paramSet.setBeanType(operateLog.beanType());
		paramSet.setMethodName(operateLog.methodName());
		paramSet.setConKey(operateLog.conKey());
		paramSet.setPriKey(operateLog.priKey());
		paramSet.setExtraContent(operateLog.extraContent());
		paramSet.setSaveLable(operateLog.saveLable());
		paramSet.setInjectCurntDate(operateLog.injectCurntDate());
		paramSet.setInjectCurntUser(operateLog.injectCurntUser());
		paramSet.setInjectCurntTenant(operateLog.injectCurntTenant());
		paramSet.setElapsedTime(operateLog.elapsedTime());
		paramSet.setPrintIp(operateLog.printIp());
		return paramSet;
	}
	
}
