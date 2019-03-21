package com.kim.quality.file.controller;

import com.kim.quality.common.QualityModule;
import com.kim.quality.common.QualityOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.common.base.BaseController;
import com.kim.common.exception.BusinessException;
import com.kim.common.util.Base64Util;
import com.kim.common.util.HttpServletUtil;
import com.kim.common.util.StringUtil;
import com.kim.log.annotation.OperateLog;
import com.kim.log.interceptor.ParameterType;

/**
 * @author bo.liu01
 *
 */
@OperateLog(module = QualityModule.FILE)
@Controller
@RequestMapping("file")
public class FileController extends BaseController{
	
	@OperateLog(oper= QualityOperation.GET_AUDIO, parameterType=ParameterType.QUERY)
    @ResponseBody
    @RequestMapping(value="getAudio", method = RequestMethod.GET)
    public void getAudio(@RequestParam("f")String file, @RequestParam("s")String sign){
		if(StringUtil.isBlank(file)){
			logger.error("录音文件名不能为空");
			throw new BusinessException("录音文件名不能为空");
		}
		if(StringUtil.isBlank(sign)){
			logger.error("录音签名sign为空");
			throw new BusinessException("录音页面已过期，请刷新重试");
		}
        String fileName = Base64Util.safeUrlBase64Decode(file);
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if(fileName.endsWith("wav")){
            contentType = "audio/wav";
        }else{
            logger.warn("暂不支持该文件的播放，可能会导致前端界面无法播放录音,fileName:{}", fileName);
        }
        HttpServletUtil.outWriteFileByPath(fileName, contentType);
    }
	
}
