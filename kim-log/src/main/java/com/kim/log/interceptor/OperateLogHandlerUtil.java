package com.kim.log.interceptor;

import java.lang.reflect.Array;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kim.common.page.PageRes;
import com.kim.common.page.PageRet;
import com.kim.common.response.ObjectResponse;
import com.kim.common.response.ResultResponse;
import com.kim.common.response.TableResponse;
import com.kim.common.util.CollectionUtil;
import com.kim.common.util.HttpServletUtil;
import com.kim.common.util.StringUtil;


/**
 * 日志拦截器处理类
 * @date 2017年3月24日
 * @author liubo04
 */
public class OperateLogHandlerUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(OperateLogHandlerUtil.class);
	
	private static final ThreadLocal<ParamSet> threadLocal = new ThreadLocal<>();
	
//	private static final String OBJECT_STR_FM = "[object]:%s";//有自定义数据的时候的格式
	private static final String INPUT_PARAMETER_FM = "[input parameter]:%s";//手工打印参数日志格式
	private static final String QUERY_PARAMETER_FM = "[query parameter]:%s";//url 参数日志格式
	private static final String BODY_PARAMETER_FM = "[body parameter]:%s";//body参数日志格式
	private static final String CONTENT_FM = "[%s][%s][%s]%s";//url,module,operation,content
	private static final String RESULT_FM = "[result]:%s";
	private static final String RESULT_FM_EL_TIME = "[result][cost:%sms]:%s";
	private static final String RESULT_FM_EL_TIME_DEV = "[result][cost:%sms]:%s [endTime]%s";
	private static final String EMPTY_JSON_STR = new JSONObject().toJSONString();
	
	private static final String LIST_RESULT_FORMAT = "list count(%s)";
	private static final String ARRAY_RESULT_FORMAT = "array length(%s)";
	
	public static ParamSet getCurrentParamSet(){
		return threadLocal.get();
	}
	
	public static void setCurrentParamSet(ParamSet paramSet){
		threadLocal.set(paramSet);
	}
	
	/**
	 * 打印web接口的入参，如果参数o为list或Array的时候，且长度超过2，则不打印对象o的内容只打印长度
	 * @param o 
	 * @date 2017年3月25日
	 * @author liubo04
	 */
	public static void logInputParam(Object o){
		logInputParam(o, false);
	}
	
	/**
	 * 打印web接口的入参
	 * @param o
	 * @param isPrintData 如果参数o为list或者Array的时候，且长度超过2，是否打印对象o，true则打印，false则不打印内容只打印长度
	 * @date 2017年3月25日
	 * @author liubo04
	 */
	public static void logInputParam(Object o, boolean isPrintData){
		if(o == null){
			print(getCurrentParamSet(), "input parameter is null");
		}else if(o instanceof List){
			printInputList((List<?>)o, isPrintData);
		}else if(o.getClass().isArray()){
			printInputArray(o, isPrintData);
		}else {
			printInputObject(o);
		}
	}
	
	private static void printInputObject(Object o){
		printInput(JSON.toJSONString(o));
	}
	
	private static void printInputList(List<?> list, boolean isPrintData){
		if(isPrintData && list.size() > 2){
			printInput(JSON.toJSONString(list));
		}else{
			printInput("list count("+list.size()+")");
		}
	}
	
	private static void printInputArray(Object o, boolean isPrintData){
		int length = Array.getLength(o);
		if(isPrintData && length > 2){
			printInput(JSON.toJSONString(o));
		}else{
			printInput("array length("+length+")");
		}
	}
	
	private static void printInput(String content){
		print(getCurrentParamSet(), String.format(INPUT_PARAMETER_FM, content));
	}
	
	/**
	 * 打印接口的入参日志
	 * @param paramSet
	 * @date 2017年3月24日
	 * @author liubo04
	 * @param paramSet 
	 */
	public static void loggingInputParam(ParamSet paramSet){
		setCurrentParamSet(paramSet);
		if(paramSet == null || !paramSet.isAutoPrint()){
			return ;
		}
		if(!paramSet.isAutoPrint()){
			logger.debug("auto printing input parameter and result is not actived");
			return ;
		}
		
		StringBuilder contentBf = new StringBuilder("[input]");
		if(paramSet.getParameterTypeList().contains(ParameterType.BODY)){
			contentBf.append(String.format(BODY_PARAMETER_FM, paramSet.getBodyParam()));
			if(StringUtil.isNotBlank(paramSet.getQueryParam()) && !EMPTY_JSON_STR.equals(paramSet.getQueryParam())){
				contentBf.append(", ").append(String.format(QUERY_PARAMETER_FM, paramSet.getQueryParam()));
			}
		}else{
			contentBf.append(String.format(QUERY_PARAMETER_FM, paramSet.getQueryParam()));
		}
//		if(StringUtil.isNotBlank(paramSet.getObjStr())){
//			contentBf.append(", ").append(String.format(OBJECT_STR_FM, paramSet.getObjStr()));
//		}
		
		if(paramSet.getElapsedTime()){//判断是否打印耗时时间
			paramSet.setStartTime(System.currentTimeMillis());
			if(paramSet.getLevel() != Level.INFO){
				contentBf.append(String.format("[startTime:%s]", paramSet.getStartTime()));
			}
		}
		print(paramSet, contentBf.toString());
	}
	
	/**
	 * 打印接口的返回结果日志
	 * @date 2017年3月24日
	 * @author liubo04
	 */
	public static void logOutputParam(Object o){
		ParamSet paramSet = getCurrentParamSet();
		if(paramSet == null || !paramSet.isAutoPrint()){
			return ;
		}
		if(o == null){
			printRes("result is null", paramSet);
		}else if(o instanceof TableResponse){
			printTableResponse((TableResponse) o, paramSet);
		}else if(o instanceof ObjectResponse){
			printObjectResponse((ObjectResponse) o, paramSet);
		}else if(o instanceof ResultResponse){
			printResultObject((ResultResponse) o, paramSet);
		}else if(o instanceof JSONObject){
			printJSONObject((JSONObject) o, paramSet);
		}else if(o instanceof PageRes){
			printResultPageRet(PageRet.convert((PageRes<?>)o), paramSet);
		}else if(o instanceof PageRet){
			printResultPageRet((PageRet<?>)o, paramSet);
		}else if(o instanceof List){
			printResultList((List<?>)o, paramSet);
		}else if(o.getClass().isArray()){
			printResultArray(o, paramSet);
		}else {
			printResultObject(o, paramSet);
		}
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static void printTableResponse(TableResponse table, ParamSet paramSet) {
		PageRet<?> pageRet = table.getData();
		List list = pageRet.getList();
		if(CollectionUtil.isNotEmpty(list) && !paramSet.isPrintResData()){
			//打印日志的时候过滤掉data
			pageRet.setList(null);
		}
		
		printResultObject(table, paramSet);
		//打印完日志，再恢复data
		pageRet.setList(list);
	}

	private static void printObjectResponse(ObjectResponse o, ParamSet paramSet) {
		Object data = o.getData();
		if(data != null && !paramSet.isPrintResData()){
			//打印日志的时候过滤掉data
			if(data instanceof List){
				int length = ((List<?>) data).size();
				if(length > 1){
					//打印日志的时候过滤掉data
					o.setData(String.format(LIST_RESULT_FORMAT, length));
				}
			}else if(data.getClass().isArray()){
				int length = Array.getLength(data);
				if(length > 1){
					//打印日志的时候过滤掉data
					o.setData(String.format(ARRAY_RESULT_FORMAT, length));
				}
			}
		}
		printResultObject(o, paramSet);
		//打印完日志，再恢复data
		o.setData(data);
	}

	private static void printResultObject(Object o, ParamSet paramSet){
		printRes(JSON.toJSONString(o), paramSet);
	}
	
	private static void printResultArray(Object o, ParamSet paramSet){
		int length = Array.getLength(o);
		if(length > 1 && !paramSet.isPrintResData()){
			printRes(String.format(ARRAY_RESULT_FORMAT, length), paramSet);
		}else{
			printResultObject(o, paramSet);
		}
	}
	
	private static void printResultList(List<?> list, ParamSet paramSet){
		if(list.size() > 1 && !paramSet.isPrintResData()){
			printRes(String.format(LIST_RESULT_FORMAT, list.size()), paramSet);
		}else{
			printResultObject(list, paramSet);
		}
	}
	
	private static <T> void printResultPageRet(PageRet<T> pageRet, ParamSet paramSet){
		List<T> data = pageRet.getList();
		if(CollectionUtil.isNotEmpty(data) && !paramSet.isPrintResData()){
			//打印日志的时候过滤掉data
			pageRet.setList(null);
		}
		
		printResultObject(data, paramSet);
		//打印完日志，再恢复data
		pageRet.setList(data);
	}
	
	private static void printJSONObject(JSONObject json, ParamSet paramSet){
		Object data = json.get("data");
		if(data != null && !paramSet.isPrintResData()){
			//打印日志的时候过滤掉data
			beforePrintDealData(json, data);
		}
		printResultObject(json, paramSet);
		//打印完日志，再恢复data
		json.put("data", data);
	}
	
	private static void beforePrintDealData(JSONObject json, Object data){
		if(data instanceof List){
			int length = ((List<?>) data).size();
			if(length > 1){
				//打印日志的时候过滤掉data
				json.put("data", String.format(LIST_RESULT_FORMAT, length));
			}
		}else if(data.getClass().isArray()){
			int length = Array.getLength(data);
			if(length > 1){
				//打印日志的时候过滤掉data
				json.put("data", String.format(ARRAY_RESULT_FORMAT, length));
			}
		}
	}
	
	/**
	 * 打印返回结果日志
	 * @param result
	 * @param paramSet
	 * @date 2017年3月25日
	 * @author liubo04
	 */
	private static void printRes(String result, ParamSet paramSet){
		if(paramSet.getElapsedTime()){//判断是否打印耗时时间
			paramSet.setEndTime(System.currentTimeMillis());
			if(paramSet.getLevel() != Level.INFO){
				print(paramSet, String.format(RESULT_FM_EL_TIME_DEV, paramSet.getEndTime()-paramSet.getStartTime(), result, paramSet.getStartTime()));
			}else{
				print(paramSet, String.format(RESULT_FM_EL_TIME, paramSet.getEndTime()-paramSet.getStartTime(), result));
			}
		}else{
			print(paramSet, String.format(RESULT_FM, result));
		}
	}
	
	/**
	 * 固定格式打印日志
	 * @param paramSet
	 * @param content
	 * @date 2017年3月25日
	 * @author liubo04
	 */
	private static void print(ParamSet paramSet, String content){
		logging(paramSet.getLevel(), getMergeContent(paramSet, String.format(CONTENT_FM, paramSet.getUrl(), paramSet.getModule(),
				paramSet.getOper(), content)));
	}
	
	private static String getMergeContent(ParamSet paramSet, String content){
		if(paramSet.isPrintIp()){
			return "["+ HttpServletUtil.getIpAddress()+"]"+content;
		}
		return content;
	}
	
	private static void logging(Level level, String content){
		switch(level){
		case DEBUG: if(logger.isDebugEnabled()){logger.debug(content);}break;
		case WARN: if(logger.isWarnEnabled()){logger.warn(content);}break;
		case ERROR: if(logger.isErrorEnabled()){logger.error(content);}break;
		default : if(logger.isInfoEnabled()){logger.info(content);}
		}
	}

}
