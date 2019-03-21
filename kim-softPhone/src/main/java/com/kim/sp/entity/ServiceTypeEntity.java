package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 弹屏服务类型表实体类
 * @date 2019-3-14 20:02:22
 * @author liubo
 */
public class ServiceTypeEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 服务类型id */
	private String serviceTypeId;
	/** 服务类型名称 */
	private String serviceTypeName;
	/**  */
	private String createOrganId;
	/**  */
	private String type;
	/**  */
	private String parentTypeId;
	/**  */
	private String serviceTypeKind;
	/**  */
	private Integer serviceLevel;
	/**  */
	private Integer displayNo;
	/** 是否可用 1是 0否 */
	private String isActive;
	
	public ServiceTypeEntity id(String id) {
		setId(id);
		return this;
	}

	public ServiceTypeEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
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
	
	public void setCreateOrganId(String createOrganId){  
        this.createOrganId = createOrganId;  
    }  
    
	@FieldDisplay("")
    public String getCreateOrganId(){
        return this.createOrganId;  
    }
	
	public void setType(String type){  
        this.type = type;  
    }  
    
	@FieldDisplay("")
    public String getType(){
        return this.type;  
    }
	
	public void setParentTypeId(String parentTypeId){  
        this.parentTypeId = parentTypeId;  
    }  
    
	@FieldDisplay("")
    public String getParentTypeId(){
        return this.parentTypeId;  
    }
	
	public void setServiceTypeKind(String serviceTypeKind){  
        this.serviceTypeKind = serviceTypeKind;  
    }  
    
	@FieldDisplay("")
    public String getServiceTypeKind(){
        return this.serviceTypeKind;  
    }
	
	public void setServiceLevel(Integer serviceLevel){  
        this.serviceLevel = serviceLevel;  
    }  
    
	@FieldDisplay("")
    public Integer getServiceLevel(){
        return this.serviceLevel;  
    }
	
	public void setDisplayNo(Integer displayNo){  
        this.displayNo = displayNo;  
    }  
    
	@FieldDisplay("")
    public Integer getDisplayNo(){
        return this.displayNo;  
    }
	
	public void setIsActive(String isActive){  
        this.isActive = isActive;  
    }  
    
	@FieldDisplay("是否可用 1是 0否")
    public String getIsActive(){
        return this.isActive;  
    }
	
}