package com.kim.admin.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kim.common.base.BaseController;
import com.kim.common.exception.BusinessException;
import com.kim.common.util.HttpServletUtil;
import com.kim.common.util.StringUtil;
import com.kim.impexp.util.DownloadUtil;

/**
 * @author bo.liu01
 *
 */
@Controller
@RequestMapping("common")
public class CommonController extends BaseController{
	
	@ResponseBody
	@RequestMapping(value = "download", method=RequestMethod.GET)
	public void download(@RequestParam("f")String file, @RequestParam("s")String sign) {
		if(StringUtil.isBlank(file)){
			logger.error("下载文件名不能为空");
			throw new BusinessException("下载文件名不能为空");
		}
		if(StringUtil.isBlank(sign)){
			logger.error("下载签名sign为空");
			throw new BusinessException("下载页面已过期，请刷新重试");
		}
		HttpServletUtil.outWriteFileByPath(DownloadUtil.decode(file, sign));
	}
	
	@ResponseBody
	@RequestMapping(value = "img", method=RequestMethod.GET)
	public void img(@RequestParam("f")String file, @RequestParam("s")String sign) {
		if(StringUtil.isBlank(file)){
			logger.error("图片文件名不能为空");
			throw new BusinessException("图片文件名不能为空");
		}
		if(StringUtil.isBlank(sign)){
			logger.error("图片签名sign为空");
			throw new BusinessException("图片签名sign不能为空");
		}
		HttpServletUtil.outWriteFileByPath(DownloadUtil.decode(file, sign), MediaType.IMAGE_JPEG_VALUE);
	}


}
