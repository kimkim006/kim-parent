package com.kim.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(ExecUtil.class);
	
	public static String[] runCmds(String[] cmd){

		String[] res = new String[2];
		String charset = Charset.defaultCharset().toString();
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			InputStream err = p.getErrorStream();
			InputStream in = p.getInputStream();
			res[0] = StreamUtils.readStreamString(in, charset);
			res[1] = StreamUtils.readStreamString(err, charset);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			res[1] = e.getMessage();
		}
		return res;
	}

}
