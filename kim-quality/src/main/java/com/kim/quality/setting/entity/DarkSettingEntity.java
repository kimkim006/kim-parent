package com.kim.quality.setting.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 抽检小黑屋表实体类
 * @date 2018-11-21 17:34:22
 * @author bo.liu01
 */
public class DarkSettingEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 是否启用, 1启用 */
	public static final int ACTIVE_YES = 1;
	/** 是否启用, 0停用*/
	public static final int ACTIVE_NO = 1;
	
	private String name;//坐席姓名
	private String groupName;//业务小组名称
	private String groupCode;//业务小组编码
	
	private String username;//工号
	private String startTime;//开始时间
	private String endTime;//结束时间
	private Integer active;//是否启用, 1启用, 0停用
	
	public DarkSettingEntity id(String id) {
		setId(id);
		return this;
	}

	public DarkSettingEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setUsername(String username){  
        this.username = username;  
    }  
    
	@FieldDisplay("工号")
    public String getUsername(){
        return this.username;  
    }
	
	public void setStartTime(String startTime){  
        this.startTime = startTime;  
    }  
    
	@FieldDisplay("开始时间")
    public String getStartTime(){
        return this.startTime;  
    }
	
	public void setEndTime(String endTime){  
        this.endTime = endTime;  
    }  
    
	@FieldDisplay("结束时间")
    public String getEndTime(){
        return this.endTime;  
    }
	
	public void setActive(Integer active){  
        this.active = active;  
    }  
    
	@FieldDisplay("是否启用, 1启用, 0停用")
    public Integer getActive(){
        return this.active;  
    }

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}