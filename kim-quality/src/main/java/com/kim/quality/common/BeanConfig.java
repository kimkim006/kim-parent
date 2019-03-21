package com.kim.quality.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kim.common.config.CommonBeanConfig;

@Component
public class BeanConfig extends CommonBeanConfig{
	
    /** 录音文件临时目录  */
    @Value("${file.tmp.dir}")	
    private String fileTmpDir;
    
    /** 附件文件目录  */
    @Value("${file.atta.dir}")	
    private String fileAttaDir;

    public String getFileTmpDir() {
		return fileTmpDir;
	}

	public String getFileAttaDir() {
		return fileAttaDir;
	}
    
}
