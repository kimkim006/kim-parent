package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 弹屏服务类型表参数封装类
 * @date 2019-3-14 20:02:22
 * @author liubo
 */
public class ServiceTypeVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String serviceTypeId;  //服务类型id
	private String serviceTypeName;  //服务类型名称
	private String createOrganId;  //
	private String type;  //
	private String parentTypeId;  //
	private String serviceTypeKind;  //
	private Integer serviceLevel;  //
	private Integer displayNo;  //
	private String isActive;  //是否可用 1是 0否

	public ServiceTypeVo id(String id) {
		setId(id);
		return this;
	}

	public ServiceTypeVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setServiceTypeId(String serviceTypeId){  
        this.serviceTypeId = serviceTypeId;  
    }  
      
    public String getServiceTypeId(){  
        return this.serviceTypeId;  
    }
    
	
	public void setServiceTypeName(String serviceTypeName){  
        this.serviceTypeName = serviceTypeName;  
    }  
      
    public String getServiceTypeName(){  
        return this.serviceTypeName;  
    }
    
	
	public void setCreateOrganId(String createOrganId){  
        this.createOrganId = createOrganId;  
    }  
      
    public String getCreateOrganId(){  
        return this.createOrganId;  
    }
    
	
	public void setType(String type){  
        this.type = type;  
    }  
      
    public String getType(){  
        return this.type;  
    }
    
	
	public void setParentTypeId(String parentTypeId){  
        this.parentTypeId = parentTypeId;  
    }  
      
    public String getParentTypeId(){  
        return this.parentTypeId;  
    }
    
	
	public void setServiceTypeKind(String serviceTypeKind){  
        this.serviceTypeKind = serviceTypeKind;  
    }  
      
    public String getServiceTypeKind(){  
        return this.serviceTypeKind;  
    }
    
	
	public void setServiceLevel(Integer serviceLevel){  
        this.serviceLevel = serviceLevel;  
    }  
      
    public Integer getServiceLevel(){  
        return this.serviceLevel;  
    }
    
	
	public void setDisplayNo(Integer displayNo){  
        this.displayNo = displayNo;  
    }  
      
    public Integer getDisplayNo(){  
        return this.displayNo;  
    }
    
	
	public void setIsActive(String isActive){  
        this.isActive = isActive;  
    }  
      
    public String getIsActive(){  
        return this.isActive;  
    }
    
	
}