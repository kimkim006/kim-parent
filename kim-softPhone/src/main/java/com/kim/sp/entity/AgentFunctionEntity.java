package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 话务工号功能码表实体类
 * @date 2019-3-7 16:35:07
 * @author liubo
 */
public class AgentFunctionEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 功能编码 */
	private String funcCode;
	/** 功能名称 */
	private String funcName;
	/** 功能值 */
	private String funcValue;
	/** 功能值类型 */
	private String funcValueType;
	/** 平台类型 */
	private String platformType;
	/** 话务平台id */
	private String platformId;
	
	public AgentFunctionEntity id(String id) {
		setId(id);
		return this;
	}

	public AgentFunctionEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setFuncCode(String funcCode){  
        this.funcCode = funcCode;  
    }  
    
	@FieldDisplay("功能编码")
    public String getFuncCode(){
        return this.funcCode;  
    }
	
	public void setFuncName(String funcName){  
        this.funcName = funcName;  
    }  
    
	@FieldDisplay("功能名称")
    public String getFuncName(){
        return this.funcName;  
    }
	
	public void setFuncValue(String funcValue){  
        this.funcValue = funcValue;  
    }  
    
	@FieldDisplay("功能值")
    public String getFuncValue(){
        return this.funcValue;  
    }
	
	public void setPlatformType(String platformType){  
        this.platformType = platformType;  
    }  
    
	@FieldDisplay("平台类型")
    public String getPlatformType(){
        return this.platformType;  
    }
	
	public void setPlatformId(String platformId){  
        this.platformId = platformId;  
    }  
    
	@FieldDisplay("话务平台id")
    public String getPlatformId(){
        return this.platformId;  
    }

	public String getFuncValueType() {
		return funcValueType;
	}

	public void setFuncValueType(String funcValueType) {
		this.funcValueType = funcValueType;
	}
	
}