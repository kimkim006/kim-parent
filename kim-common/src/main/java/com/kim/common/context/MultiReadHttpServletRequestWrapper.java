package com.kim.common.context;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import jodd.io.StreamUtil;


/**
 * @Description 可以多次post数据获取的包装
 * @date 2016年7月27日
 * @author liubo04
 */
public class MultiReadHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	private ThreadLocal<byte[]> threadLocalBody = new ThreadLocal<>(); 

	public MultiReadHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		threadLocalBody.set(StreamUtil.readBytes(request.getInputStream()));
	}
	
	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(threadLocalBody.get());
		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return bais.read();
			}

			public boolean isFinished() {
				return true;
			}

			public boolean isReady() {
				return true;
			}

			public void setReadListener(ReadListener readListener) {
			}
		};
	}

}