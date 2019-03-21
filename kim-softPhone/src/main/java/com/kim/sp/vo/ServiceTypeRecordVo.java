package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 服务与服务类型关联表参数封装类
 * @date 2019-3-14 20:02:22
 * @author liubo
 */
public class ServiceTypeRecordVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String serviceId;  //
	private String serviceTypeId;  //服务类型id
	private String serviceTypeName;  //服务类型名称
	private String parentTypeId;  //
	private Integer serviceLevel;  //

	public ServiceTypeRecordVo id(String id) {
		setId(id);
		return this;
	}

	public ServiceTypeRecordVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setServiceId(String serviceId){  
        this.serviceId = serviceId;  
    }  
      
    public String getServiceId(){  
        return this.serviceId;  
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
    
	
	public void setParentTypeId(String parentTypeId){  
        this.parentTypeId = parentTypeId;  
    }  
      
    public String getParentTypeId(){  
        return this.parentTypeId;  
    }
    
	
	public void setServiceLevel(Integer serviceLevel){  
        this.serviceLevel = serviceLevel;  
    }  
      
    public Integer getServiceLevel(){  
        return this.serviceLevel;  
    }
    
	
}