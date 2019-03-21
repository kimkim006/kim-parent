package com.kim.common.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Map.Entry;

import com.kim.common.exception.BusinessException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by bo.liu01 on 2017/10/24.
 */
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    
    public static final String CHARSET = "UTF-8";

    /**
     * 下载网络文件
     * @param httpUrl
     * @param fileName
     * @return
     */
    public static void downloadFile(String httpUrl, String fileName) throws IOException {

        URL url = null;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e) {
            logger.error(e.getMessage(), e);
            logger.error("下载链接错误, url:{}", httpUrl);
            throw new BusinessException("请检查下载链接是正确", e);
        }
        FileOutputStream fs = null;
        InputStream inStream = null;
        try {
            URLConnection conn = url.openConnection();
            inStream = conn.getInputStream();
            File file = new File(getFileDir(fileName));
            if(!file.exists()){
                file.mkdirs();
            }
            fs = new FileOutputStream(fileName);

            byte[] buffer = new byte[1024];
            int byteReadCount;
            while ((byteReadCount = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteReadCount);
            }
            logger.debug("文件下载完成! fileName:{}", fileName);
        } catch (FileNotFoundException e) {
        	logger.error("不存在的目录或文件, fileName:{}", fileName);
            logger.error(e.getMessage(), e);
            throw new BusinessException("不存在的目录或文件："+fileName, e);
        } catch (IOException e) {
        	logger.error("文件下载失败, url:{}, fileName:{}", httpUrl, fileName);
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            close(fs);
            close(inStream);
        }
    }

    private static String getFileDir(String fileName){
    	int i1 = fileName.lastIndexOf('/');
		int i2 = fileName.lastIndexOf('\\');
        return fileName.substring(0, (i1 > i2 ? i1 : i2));
    }

    public static void close(Closeable c){
        if(c != null){
            try {
                c.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
    
    public static boolean download(String url, String fileName) throws IOException {  
    	if(StringUtil.isBlank(url) || StringUtil.isBlank(fileName)){
    		logger.error("下载文件时地址为空");
    		return false;
    	}
    	url = url.replace("\\", "/");
    	FileOutputStream fileout=null;
    	InputStream is = null;
    	CloseableHttpClient httpclient = null;
        try {  
        	httpclient = HttpClients.createDefault();
            HttpGet httpget = new HttpGet(url);  
            HttpResponse response = httpclient.execute(httpget);  
  
            HttpEntity entity = response.getEntity();  
            is = entity.getContent();  
            File file = new File(fileName);  
            file.getParentFile().mkdirs();  
            fileout = new FileOutputStream(file);  
            byte[] buffer=new byte[2048];  
            int ch = 0;  
            while ((ch = is.read(buffer)) != -1) {  
                fileout.write(buffer,0,ch);  
            }  
            fileout.flush();  
            return true;
        } catch (IOException e) {
        	logger.error("文件下载失败, url:{}, fileName:{}", url, fileName);
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            close(fileout);
            close(is);
            close(httpclient);
        }  
    }  
    
	private static void addHeaders(HttpMessage method, Map<String, String> header) {
		method.addHeader("Content-Type", "application/json");
		method.addHeader("Accept-Charset", CHARSET);
		if(CollectionUtil.isNotEmpty(header)){
			for (Entry<String, String> entries : header.entrySet()) {
				method.addHeader(entries.getKey(), entries.getValue());
			}
		}
	}
	
	private static <T> ResponseHandler<T> newResponseHandler(final Class<T> clazz) {
		return new ResponseHandler<T>() {
			@Override
			public T handleResponse(HttpResponse response) throws IOException {
				final String responseString = IOUtils.toString(response.getEntity().getContent(), CHARSET);
				logger.info("responseString:{}", responseString);
				if(StringUtil.isNotBlank(responseString)){
					try {
						return JSON.parseObject(responseString, clazz);
					} catch (Exception e) {
						return null;
					}
				}
				return null;
			}
		};
	}
	
	private static <T> T callWithBody(String url, HttpEntityEnclosingRequestBase method, Object requestBody, 
			Map<String, String> header, Class<T> clazz) throws IOException {
		
		if(requestBody != null){
			String body = JSON.toJSONString(requestBody);
			logger.info("request url:{}, method:{}, body:{}", url, method.getMethod(), body);
			method.setEntity(new StringEntity(body, CHARSET));
		}
		
		return execute(url, method, header, clazz);
	}
	
	private static <T> T execute(String url, HttpRequestBase method, Map<String, String> header, Class<T> clazz) throws IOException{
		
		if(StringUtil.isBlank(url)){
			logger.error("http请求URL不能为空!");
			throw new IOException("http请求URL不能为空!");
		}
		method.setURI(URI.create(url));
		addHeaders(method, header);
		return HttpClientBuilder.create().build().execute(method, newResponseHandler(clazz));
	}
	
	/* POST请求方法 -- start -- */ 
	public static <T> T post(String url, Object requestBody, Map<String, String> header, Class<T> clazz) throws IOException {
		return callWithBody(url, new HttpPost(), requestBody, header, clazz);
	}
	
	public static JSONObject post(String url, Object requestBody, Map<String, String> header) throws IOException {
		return callWithBody(url, new HttpPost(), requestBody, header, JSONObject.class);
	}
	
	public static <T> T post(String url, Object requestBody, Class<T> clazz) throws IOException {
		return callWithBody(url, new HttpPost(), requestBody, null, clazz);
	}
	
	public static JSONObject post(String url, Object requestBody) throws IOException {
		return callWithBody(url, new HttpPost(), requestBody, null, JSONObject.class);
	}
	/* POST请求方法 -- end -- */ 

	/* PUT请求方法 -- start -- */ 
	public static <T> T put(String url, Object requestBody, Map<String, String> header, Class<T> clazz) throws IOException {
		return callWithBody(url, new HttpPut(), requestBody, header, clazz);
	}
	
	public static JSONObject put(String url, Object requestBody, Map<String, String> header) throws IOException {
		return callWithBody(url, new HttpPut(), requestBody, header, JSONObject.class);
	}
	
	public static <T> T put(String url, Object requestBody, Class<T> clazz) throws IOException {
		return callWithBody(url, new HttpPut(), requestBody, null, clazz);
	}
	
	public static JSONObject put(String url, Object requestBody) throws IOException {
		return callWithBody(url, new HttpPut(), requestBody, null, JSONObject.class);
	}
	/* PUT请求方法 -- end -- */ 
	
	/* GET请求方法 -- start -- */ 
	public static <T> T get(String url, Map<String, String> header, Class<T> clazz) throws IOException {
		HttpGet method = new HttpGet();
		logger.info("request url:{}, method:{}", url, method.getMethod());
		
		return execute(url, method, header, clazz);
	}
	
	public static <T> T get(String url, Class<T> clazz) throws IOException {
		return get(url, null, clazz);
	}
	
	public static JSONObject get(String url, Map<String, String> headers) throws IOException {
		return get(url, headers, JSONObject.class);
	}
	
	public static JSONObject get(String url) throws IOException {
		return get(url, null, JSONObject.class);
	}
	/* GET请求方法 -- end -- */ 

}
