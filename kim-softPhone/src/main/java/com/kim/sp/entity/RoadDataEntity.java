package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 随路数据表实体类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
public class RoadDataEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 录音Id */
	private String recordId;
	/** 手机号 */
	private String phone;
	/** 身份证号 */
	private String idNum;
	/** 手机验证是否通过 */
	private String isPhoneChecked;
	/** 身份证验证是否通过 */
	private String isIdNumChecked;
	/** 原始随路数据 */
	private String roadData;
	/** 是否绑定微信 1是, 0否 */
	private Integer isBindWx;
	/** 服务ID */
	private String serviceId;
	/** 是否m0催收 */
	private String isUrge;
	/** 用户姓名 */
	private String name;
	/** 用户工号 */
	private String username;
	/** 话务工号 */
	private String agentId;
	
	public RoadDataEntity id(String id) {
		setId(id);
		return this;
	}

	public RoadDataEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setRecordId(String recordId){  
        this.recordId = recordId;  
    }  
    
	@FieldDisplay("录音Id")
    public String getRecordId(){
        return this.recordId;  
    }
	
	public void setPhone(String phone){  
        this.phone = phone;  
    }  
    
	@FieldDisplay("手机号")
    public String getPhone(){
        return this.phone;  
    }
	
	public void setIdNum(String idNum){  
        this.idNum = idNum;  
    }  
    
	@FieldDisplay("身份证号")
    public String getIdNum(){
        return this.idNum;  
    }
	
	public void setIsPhoneChecked(String isPhoneChecked){  
        this.isPhoneChecked = isPhoneChecked;  
    }  
    
	@FieldDisplay("手机验证是否通过")
    public String getIsPhoneChecked(){
        return this.isPhoneChecked;  
    }
	
	public void setIsIdNumChecked(String isIdNumChecked){  
        this.isIdNumChecked = isIdNumChecked;  
    }  
    
	@FieldDisplay("身份证验证是否通过")
    public String getIsIdNumChecked(){
        return this.isIdNumChecked;  
    }
	
	public void setRoadData(String roadData){  
        this.roadData = roadData;  
    }  
    
	@FieldDisplay("原始随路数据")
    public String getRoadData(){
        return this.roadData;  
    }
	
	public void setIsBindWx(Integer isBindWx){  
        this.isBindWx = isBindWx;  
    }  
    
	@FieldDisplay("是否绑定微信 1是, 0否")
    public Integer getIsBindWx(){
        return this.isBindWx;  
    }
	
	public void setServiceId(String serviceId){  
        this.serviceId = serviceId;  
    }  
    
	@FieldDisplay("服务ID")
    public String getServiceId(){
        return this.serviceId;  
    }
	
	public void setIsUrge(String isUrge){  
        this.isUrge = isUrge;  
    }  
    
	@FieldDisplay("是否m0催收")
    public String getIsUrge(){
        return this.isUrge;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
    
	@FieldDisplay("用户姓名")
    public String getName(){
        return this.name;  
    }
	
	public void setUsername(String username){  
        this.username = username;  
    }  
    
	@FieldDisplay("用户工号")
    public String getUsername(){
        return this.username;  
    }
	
	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
    
	@FieldDisplay("话务工号")
    public String getAgentId(){
        return this.agentId;  
    }
	
}