package com.kim.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kim.common.config.CommonBeanConfig;
import com.kim.common.context.ContextHolder;
import com.kim.common.exception.BusinessException;

import jodd.io.FileUtil;

public class HttpServletUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpServletUtil.class);
	
	private static final String UNKNOWN = "unknown";
	public static final String CHARSET = "utf-8";
	public static final String FILE_SUFFIX_SEPARATOR = ".";
	public static final String CONCATOR = "_";
	
	private HttpServletUtil(){}

	public static HttpServletRequest getOriginalRequest() {

		ServletRequestAttributes reqAttr = 
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(reqAttr == null){
			return null;
		}
		return reqAttr.getRequest();
	}

	public static HttpServletResponse getOriginalResponse() {
		
		return ContextHolder.getResponse();
	}

	/**
	 * 获取当前的web应用上下文
	 * 
	 * @return
	 * @date 2017年5月2日
	 * @author liubo04
	 */
	public static WebApplicationContext getCurrentWebApplicationContext() {
		HttpServletRequest request = getOriginalRequest();
		if(request == null){
			return null;
		}
		return WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	}

	/**
	 * 根据beanname获取Bean组件
	 * 
	 * @param beanName
	 * @return
	 * @date 2017年5月2日
	 * @author liubo04
	 */
	public static Object getBean(String beanName) {
		ApplicationContext wac = ContextHolder.getApplicationContext();
		if (wac == null) {
			logger.info("当前应用上下文为空");
			return null;
		}
		return wac.getBean(beanName);
	}

	/**
	 * 根据beanType获取Bean组件
	 * 
	 * @param clazz
	 * @return
	 * @date 2017年5月2日
	 * @author liubo04
	 */
	public static <T> T getBean(Class<T> clazz) {
		ApplicationContext wac = ContextHolder.getApplicationContext();
		if (wac == null) {
			logger.info("当前应用上下文为空");
			return null;
		}
		return wac.getBean(clazz);
	}
	
	/**
	 * 获取当前请求的客户端真实IP地址
	 * 
	 * @return
	 * @date 2017年4月1日
	 * @author liubo04
	 */
	public static String getIpAddress() {
		HttpServletRequest request = getOriginalRequest();
		if (request == null){
			return "";
		}
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 返回值
	 * 
	 * @param str
	 * @date 2016年12月2日
	 * @author liubo04
	 */
	public static void outWrite(String str) {
		try {
			HttpServletResponse response = getOriginalResponse();
			if (response != null) {
				response.setCharacterEncoding(CHARSET);
				response.getWriter().write(str);
			} else {
				logger.error("response is null");
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("返回结果失败");
		}
	}

	public static void outWrite(Object obj) {
		outWrite(JSON.toJSONString(obj));
	}

	/**
	 * 输出文件（前端下载文件）
	 * 
	 * @param fileName 下载的文件名称
	 * @param filePath 路径（不包含文件）
	 * @date 2016年11月9日
	 * @author liubo04
	 */
	public static void outWriteFile(String fileName, String filePath, String contentType) {
		outWriteFile(fileName, filePath, null, contentType);
	}

	/**
	 * 输出文件（前端下载文件）
	 * 
	 * @param filePath 路径,完整的文件路径
	 * @date 2016年11月9日
	 * @author liubo04
	 */
	public static void outWriteFileByPath(String filePath) {
		outWriteFileByPath(filePath, null);
	}
	
	/**
	 * 输出文件（前端下载文件）
	 * 
	 * @param filePath 路径,完整的文件路径
	 * @date 2016年11月9日
	 * @author liubo04
	 */
	public static void outWriteFileByPath(String filePath, String contentType) {
		outWriteFileByPath(filePath, null, contentType);
	}

	/**
	 * 输出文件（前端下载文件）
	 * 
	 * @param filePath 路径,完整的文件路径
	 * @param displayFileName 输出到前端的文件名称
	 * @date 2016年11月9日
	 * @author liubo04
	 */
	public static void outWriteFileByPath(String filePath, String displayFileName, String contentType) {
		if (!filePath.contains(File.separator)) {
			outWriteFile(filePath, "", displayFileName);
		} else {
			int li = filePath.lastIndexOf(File.separator);
			outWriteFile(filePath.substring(li + 1), filePath.substring(0, li), displayFileName, contentType);
		}
	}

	/**
	 * 输出文件（前端下载文件）
	 * 
	 * @param fileName 下载的文件名称
	 * @param filePath 路径（不包含文件）
	 * @param displayFileName 输出到前端的文件名称
	 * @date 2016年11月9日
	 * @author liubo04
	 */
	public static void outWriteFile(String fileName, String filePath, String displayFileName, String contentType) {
		File file = new File(filePath + File.separator + fileName);
		if (!file.exists()) {
			logger.error("path:{}{}{}", filePath, File.separator, fileName);
			throw new BusinessException("未找到文件");
		}
		if (StringUtils.isBlank(displayFileName)) {
			displayFileName = fileName;
		}
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			outWriteFile(in, displayFileName, contentType);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("未找到文件");
		} finally {
			close(in);
		}
	}

	/**
	 * 输出文件（前端下载文件）
	 * 
	 * @param is 要下载的文件流
	 * @param displayFileName 输出到前端的文件名称
	 * @date 2016年11月9日
	 * @author liubo04
	 */
	public static void outWriteFile(InputStream is, String displayFileName, String contentType) {
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		try {
			HttpServletResponse response = getOriginalResponse();
			// 解决中文乱码和不显示问题
			displayFileName = new String(displayFileName.getBytes(CHARSET), "ISO8859-1");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + URLEncoder.encode(displayFileName, "ISO8859-1"));
			response.setContentType(
					StringUtil.isBlank(contentType) ? MediaType.APPLICATION_OCTET_STREAM.getSubtype() : contentType);

			in = new BufferedInputStream(is);
			out = new BufferedOutputStream(response.getOutputStream());
			int total = in.available();
			response.setHeader("Content-Length", String.valueOf(total));
			Long pos = getPartialPos();
			if(pos != null){
				//支持断点续传
				response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
				//Content-Range: bytes 0-800/801 //801:文件总大小
				response.setHeader("Content-Range", "bytes 0-"+(total-1) + "/"+total);
				response.setHeader("Accept-Ranges", "bytes");
				in.skip(pos);
			}

			byte[] data = new byte[1024*10];
			int len = 0;
			while (-1 != (len = in.read(data, 0, data.length))) {
				out.write(data, 0, len);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("下载文件异常");
		} finally {
			close(in);
			close(is);
			close(out);
		}
	}

	private static Long getPartialPos(){
		HttpServletRequest request = getOriginalRequest();
		if(request == null){
			return 0L;
		}
		String rangeStr = request.getHeader("Range");
		rangeStr = StringUtil.isBlank(rangeStr) ? request.getHeader("range") : rangeStr;
		if (rangeStr == null) {
			return null;
		}
		Long pos = null;
		try {
			pos = Long.parseLong(rangeStr.replaceAll("bytes=", "")
					.replaceAll("-", ""));
		} catch (NumberFormatException e) {
			logger.error("{} is not Number!", rangeStr);
		}
		return pos;
	}

	public static void close(Closeable c){
		try {
			if (c != null) {
				c.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取表单提交的参数
	 * 
	 * @param clazz
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static <T> T getFormParamter(Class<T> clazz) {
		return JSONObject.toJavaObject(getFormParamter(), clazz);
	}

	/**
	 * 获取表单提交的参数
	 * 
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static JSONObject getFormParamter() {
		HttpServletRequest request = null;
		return getFormParamter(request);
	}

	/**
	 * 获取表单提交的参数，并转换成对象
	 * 
	 * @param request
	 * @param clazz
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static <T> T getFormParamter(HttpServletRequest request, Class<T> clazz) {
		return JSONObject.toJavaObject(getFormParamter(request), clazz);
	}

	/**
	 * 获取表单提交的参数
	 * 
	 * @param requestPara
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static JSONObject getFormParamter(HttpServletRequest requestPara) {
		HttpServletRequest request = null;
		if (requestPara == null) {
			request = getOriginalRequest();
		} else {
			request = requestPara;
		}

		JSONObject json = new JSONObject();
		if (request == null) {
			return json;
		}
		Enumeration<String> paramNames = request.getParameterNames();
		String[] values = null;
		while (paramNames.hasMoreElements()) {
			String name = paramNames.nextElement();
			values = request.getParameterValues(name);
			if (values == null || values.length == 0) {
				continue;
			}
			if (name.contains("\\.")) {
				throw new BusinessException("暂不支持表单name多级对象");
			}
			if (values.length == 1) {
				json.put(name, values[0]);
			} else {
				json.put(name, values);
			}
		}
		return json;
	}

	public static JSONObject getParamter() {
		HttpServletRequest request = null;
		return getParamter(request);
	}

	/**
	 * 获取参数
	 * 
	 * @return
	 * @date 2016年10月19日
	 * @author liubo04
	 */
	public static JSONObject getParamter(HttpServletRequest requestPara) {
		JSONObject params = new JSONObject();
		HttpServletRequest request = null;
		if (requestPara == null) {
			request = getOriginalRequest();
		} else {
			request = requestPara;
		}
		if (request != null) {
			transferParam(params, request);
		}
		return params;
	}

	private static void transferParam(Map<String, Object> params, HttpServletRequest request) {
		if(params!= null){
			Map<?, ?> requestParams = request.getParameterMap();
			for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
				}
				params.put(name, valueStr);
			}
		}
	}

	/**
	 * 将地址参数转成对象
	 * 
	 * @param clazz
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static <T> T getParamter(HttpServletRequest request, Class<T> clazz) {
		return JSONObject.toJavaObject(getParamter(request), clazz);
	}

	/**
	 * 将地址参数转成对象
	 * 
	 * @param clazz
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static <T> T getParamter(Class<T> clazz) {
		return JSONObject.toJavaObject(getParamter(), clazz);
	}

	/**
	 * 将body中的数据转换成json对象
	 * 
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static JSONObject getParamterBody() {
		return JSONObject.parseObject(getParamterBodyString());
	}

	/**
	 * 将body中的数据转成对象
	 * 
	 * @param clazz
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static <T> T getParamterBody(Class<T> clazz) {
		return JSONObject.parseObject(getParamterBodyString(), clazz);
	}

	/**
	 * 获取body中的数据
	 * 
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static String getParamterBodyString(HttpServletRequest requestPara) {
		HttpServletRequest request;
		if (requestPara == null) {
			request = getOriginalRequest();
		} else {
			request = requestPara;
		}
		try {
			if (request != null) {
				return StreamUtils.readStreamString(request.getInputStream(), CHARSET);
			} else {
				return null;
			}
		} catch (IOException e) {
			logger.error("获取字符流数据失败", e);
			return null;
		}
	}

	/**
	 * 获取body中的数据
	 * 
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static String getParamterBodyString() {
		return getParamterBodyString((HttpServletRequest) null);
	}

	/**
	 * 获取body中的字节数据
	 * 
	 * @return
	 * @date 2017年3月18日
	 * @author liubo04
	 */
	public static byte[] getParamterBodyByte() {
		try {
			HttpServletRequest request = getOriginalRequest();
			if (request == null) {
				return null;
			}
			return StreamUtils.readStreamByte(request.getInputStream());
		} catch (IOException e) {
			logger.error("获取字节流数据失败", e);
			return null;
		}
	}
	
	public static List<String> fileStreamToFile(String dirPath, MultipartFile[] files) {
		return convertFile(dirPath, Arrays.asList(files));
	}

	/**
	 * 将request请求中的文件流写进临时目录中，生成临时文件
	 * 
	 * @return
	 * @date 2016年12月2日
	 * @author liubo04
	 */
	public static List<String> fileStreamToFile() {
		String dirPath = null;
		Map<String, Object> params = null;
		return fileStreamToFile(dirPath, params);
	}

	public static MultipartHttpServletRequest getMultipartHttpServletRequest() {
		HttpServletRequest request = getOriginalRequest();
		if(request == null ) {
			return null;
		}
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		return multipartResolver.resolveMultipart(request);
	}
	
	/**
	 * 将request请求中的文件流写进临时目录中，生成临时文件
	 * 
	 * @param dirPath
	 * @return
	 * @date 2016年12月2日
	 * @author liubo04
	 */
	public static List<String> fileStreamToFile(String dirPath, Map<String, Object> params) {
		HttpServletRequest request = getOriginalRequest();
		if(request == null) {
			return null;
		}
		
		MultipartHttpServletRequest multiRequest;
		if(request instanceof MultipartHttpServletRequest){
			multiRequest = ((MultipartHttpServletRequest) request);
		}else{
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (!multipartResolver.isMultipart(request)) {
				logger.error("多文件上传请求异常.");
				throw new BusinessException("文件上传请求异常");
			}

			if (StringUtils.isBlank(dirPath)) {
				logger.error("临时目录不能为空");
				throw new BusinessException("临时目录不能为空");
			}
			// 转换成多部分request
			multiRequest = multipartResolver.resolveMultipart(request);
		}
		
		transferParam(params, multiRequest);
		return convertFile(dirPath, multiRequest.getFileMap().values());
	}

	private static List<String> convertFile(String dirPath, Collection<MultipartFile> files) {
		
		if(StringUtil.isBlank(dirPath)){
			CommonBeanConfig bean = getBean(CommonBeanConfig.class);
			if(bean != null){
				dirPath = bean.getImportTmpFilePath();
			}else {
				throw new BusinessException("获取文件列表发生未知异常");
			}
		}
		try {
			FileUtil.mkdir(dirPath);
		} catch (IOException e) {
			logger.error("创建文件目录异常", e);
			throw new BusinessException("获取文件列表发生未知异常");
		}
		List<String> filePathList = new ArrayList<>();
		int i = 1;
		try {
			for (MultipartFile file : files) {
				if (file == null) {
					logger.error("第 {}个上传的文件为空", i);
					continue;
				}
				String filename = file.getOriginalFilename();
				if (StringUtils.isBlank(filename)) {
					logger.error("第 {}个上传的文件名为空", i);
					continue;
				}

				String newFileName = fileNameDateFormat(filename);
				String fileSavePath = dirPath + File.separatorChar + newFileName;
				logger.info("第{}个上传文件：{},重命名后的文件：{},文件位置：{}", i, filename, newFileName, fileSavePath);
				File fileTmp = new File(fileSavePath);
				if(!fileTmp.exists() && !fileTmp.createNewFile()){
					logger.error("创建临时文件失败, fileTmp:{}", fileTmp);
					throw new BusinessException("上传文件异常, 提示:创建临时文件失败!");
				}
				file.transferTo(fileTmp);
				filePathList.add(fileSavePath);
				i++;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("上传文件异常");
		}
		return filePathList;
	}

	public static String fileNameDateFormat(String filename) {
		// 文件命名格式 XXX_YYYYMMDDHHmmss.后缀
		if (filename.indexOf(FILE_SUFFIX_SEPARATOR) != -1) {
			int lastIndex = filename.lastIndexOf(FILE_SUFFIX_SEPARATOR);
			return filename.substring(0, lastIndex) + CONCATOR + DateUtil.formatDate(new Date(), DateUtil.PATTERN_YYYYMMDDHHMMSS)
					+ filename.substring(lastIndex);
		} else {
			return filename + CONCATOR + DateUtil.formatDate(new Date(), DateUtil.PATTERN_YYYYMMDDHHMMSS);
		}
	}

}
