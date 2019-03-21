package com.kim.sp.entity;


import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 小休配置表实体类
 * @date 2019-3-7 16:55:39
 * @author liubo
 */
public class RestSettingEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L;
	
	/** 是否需要申请 1是 */
	public static final int IS_NEED_APPLY_YES = 1;
	/** 是否需要申请 0否  */
	public static final int IS_NEED_APPLY_NO = 0;
	
	/** 组织机构编码 */
	private String departmentCode;
	/** 是否需要申请 1是 0否 */
	private Integer isNeedApply;
	/** 每天允许的小休次数 */
	private Integer allowTimes;
	/** 每次小休时长 */
	private Long singleTimeLong;
	/** 是否可用 1是 0否 */
	private Integer isActive;
	
	public RestSettingEntity id(String id) {
		setId(id);
		return this;
	}

	public RestSettingEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setDepartmentCode(String departmentCode){  
        this.departmentCode = departmentCode;  
    }  
    
	@FieldDisplay("组织机构编码")
    public String getDepartmentCode(){
        return this.departmentCode;  
    }
	
	public void setIsNeedApply(Integer isNeedApply){  
        this.isNeedApply = isNeedApply;  
    }  
    
	@FieldDisplay("是否需要申请 1是 0否")
    public Integer getIsNeedApply(){
        return this.isNeedApply;  
    }
	
	public void setAllowTimes(Integer allowTimes){  
        this.allowTimes = allowTimes;  
    }  
    
	@FieldDisplay("每天允许的小休次数")
    public Integer getAllowTimes(){
        return this.allowTimes;  
    }
	
	public void setSingleTimeLong(Long singleTimeLong){  
        this.singleTimeLong = singleTimeLong;  
    }  
    
	@FieldDisplay("每次小休时长")
    public Long getSingleTimeLong(){
        return this.singleTimeLong;  
    }
	
	public void setIsActive(Integer isActive){  
        this.isActive = isActive;  
    }  
    
	@FieldDisplay("是否可用 1是 0否")
    public Integer getIsActive(){
        return this.isActive;  
    }
	
}