package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 短信模板表实体类
 * @date 2019-3-11 14:31:51
 * @author liubo
 */
public class SmsTemplateEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 模板类型 */
	private String templateType;
	/** 模板名称 */
	private String templateName;
	/** 模板编号 */
	private String templCode;
	/** 业务编码 */
	private String businessCode;
	/** 发送类型：验证码类（yz），催收类（cs），通知类（tz），营销类（yx），语音类（audio） */
	private String sendType;
	/** 模板内容 */
	private String content;
	/** 是否可用 */
	private Integer isActive;
	
	public SmsTemplateEntity id(String id) {
		setId(id);
		return this;
	}

	public SmsTemplateEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setTemplateType(String templateType){  
        this.templateType = templateType;  
    }  
    
	@FieldDisplay("模板类型")
    public String getTemplateType(){
        return this.templateType;  
    }
	
	public void setTemplateName(String templateName){  
        this.templateName = templateName;  
    }  
    
	@FieldDisplay("模板名称")
    public String getTemplateName(){
        return this.templateName;  
    }
	
	public void setTemplCode(String templCode){  
        this.templCode = templCode;  
    }  
    
	@FieldDisplay("模板编号")
    public String getTemplCode(){
        return this.templCode;  
    }
	
	public void setBusinessCode(String businessCode){  
        this.businessCode = businessCode;  
    }  
    
	@FieldDisplay("业务编码")
    public String getBusinessCode(){
        return this.businessCode;  
    }
	
	public void setSendType(String sendType){  
        this.sendType = sendType;  
    }  
    
	@FieldDisplay("发送类型：验证码类（yz），催收类（cs），通知类（tz），营销类（yx），语音类（audio）")
    public String getSendType(){
        return this.sendType;  
    }
	
	public void setContent(String content){  
        this.content = content;  
    }  
    
	@FieldDisplay("模板内容")
    public String getContent(){
        return this.content;  
    }
	
	public void setIsActive(Integer isActive){  
        this.isActive = isActive;  
    }  
    
	@FieldDisplay("是否可用")
    public Integer getIsActive(){
        return this.isActive;  
    }
	
}