package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 服务与服务类型关联表实体类
 * @date 2019-3-14 20:02:22
 * @author liubo
 */
public class ServiceTypeRecordEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/**  */
	private String serviceId;
	/** 服务类型id */
	private String serviceTypeId;
	/** 服务类型名称 */
	private String serviceTypeName;
	/**  */
	private String parentTypeId;
	/**  */
	private Integer serviceLevel;
	
	public ServiceTypeRecordEntity id(String id) {
		setId(id);
		return this;
	}

	public ServiceTypeRecordEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setServiceId(String serviceId){  
        this.serviceId = serviceId;  
    }  
    
	@FieldDisplay("")
    public String getServiceId(){
        return this.serviceId;  
    }
	
	public void setServiceTypeId(String serviceTypeId){  
        this.serviceTypeId = serviceTypeId;  
    }  
    
	@FieldDisplay("服务类型id")
    public String getServiceTypeId(){
        return this.serviceTypeId;  
    }
	
	public void setServiceTypeName(String serviceTypeName){  
        this.serviceTypeName = serviceTypeName;  
    }  
    
	@FieldDisplay("服务类型名称")
    public String getServiceTypeName(){
        return this.serviceTypeName;  
    }
	
	public void setParentTypeId(String parentTypeId){  
        this.parentTypeId = parentTypeId;  
    }  
    
	@FieldDisplay("")
    public String getParentTypeId(){
        return this.parentTypeId;  
    }
	
	public void setServiceLevel(Integer serviceLevel){  
        this.serviceLevel = serviceLevel;  
    }  
    
	@FieldDisplay("")
    public Integer getServiceLevel(){
        return this.serviceLevel;  
    }
	
}