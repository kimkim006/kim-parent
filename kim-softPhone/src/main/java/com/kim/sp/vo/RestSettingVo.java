package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 小休配置表参数封装类
 * @date 2019-3-7 16:55:39
 * @author liubo
 */
public class RestSettingVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String departmentCode;  //组织机构编码
	private Integer isNeedApply;  //是否需要申请 1是 0否
	private Integer allowTimes;  //每天允许的小休次数
	private Long singleTimeLong;  //每次小休时长
	private Integer isActive;  //是否可用 1是 0否

	public RestSettingVo id(String id) {
		setId(id);
		return this;
	}

	public RestSettingVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setDepartmentCode(String departmentCode){  
        this.departmentCode = departmentCode;  
    }  
      
    public String getDepartmentCode(){  
        return this.departmentCode;  
    }
    
	
	public void setIsNeedApply(Integer isNeedApply){  
        this.isNeedApply = isNeedApply;  
    }  
      
    public Integer getIsNeedApply(){  
        return this.isNeedApply;  
    }
    
	
	public void setAllowTimes(Integer allowTimes){  
        this.allowTimes = allowTimes;  
    }  
      
    public Integer getAllowTimes(){  
        return this.allowTimes;  
    }
    
	
	public void setSingleTimeLong(Long singleTimeLong){  
        this.singleTimeLong = singleTimeLong;  
    }  
      
    public Long getSingleTimeLong(){  
        return this.singleTimeLong;  
    }
    
	
	public void setIsActive(Integer isActive){  
        this.isActive = isActive;  
    }  
      
    public Integer getIsActive(){  
        return this.isActive;  
    }
    
	
}