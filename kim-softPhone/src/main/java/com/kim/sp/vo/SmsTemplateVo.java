package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 短信模板表参数封装类
 * @date 2019-3-11 14:31:51
 * @author liubo
 */
public class SmsTemplateVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String templateType;  //模板类型
	private String templateName;  //模板名称
	private String templCode;  //模板编号
	private String businessCode;  //业务编码
	private String sendType;  //发送类型：验证码类（yz），催收类（cs），通知类（tz），营销类（yx），语音类（audio）
	private String content;  //模板内容
	private Integer isActive;  //是否可用

	public SmsTemplateVo id(String id) {
		setId(id);
		return this;
	}

	public SmsTemplateVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setTemplateType(String templateType){  
        this.templateType = templateType;  
    }  
      
    public String getTemplateType(){  
        return this.templateType;  
    }
    
	
	public void setTemplateName(String templateName){  
        this.templateName = templateName;  
    }  
      
    public String getTemplateName(){  
        return this.templateName;  
    }
    
	
	public void setTemplCode(String templCode){  
        this.templCode = templCode;  
    }  
      
    public String getTemplCode(){  
        return this.templCode;  
    }
    
	
	public void setBusinessCode(String businessCode){  
        this.businessCode = businessCode;  
    }  
      
    public String getBusinessCode(){  
        return this.businessCode;  
    }
    
	
	public void setSendType(String sendType){  
        this.sendType = sendType;  
    }  
      
    public String getSendType(){  
        return this.sendType;  
    }
    
	
	public void setContent(String content){  
        this.content = content;  
    }  
      
    public String getContent(){  
        return this.content;  
    }
    
	
	public void setIsActive(Integer isActive){  
        this.isActive = isActive;  
    }  
      
    public Integer getIsActive(){  
        return this.isActive;  
    }
    
	
}