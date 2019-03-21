package com.kim.quality.setting.vo;

import java.util.List;

import com.kim.common.base.BaseVo;

/**
 * 抽检小黑屋表参数封装类
 * @date 2018-11-21 17:34:22
 * @author bo.liu01
 */
public class DarkSettingVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private List<String> ids;  //
	
	private String date;  //日期
	private String busiGroup;  //小组
	private String username;  //工号
	private String startTime;  //开始时间
	private String endTime;  //结束时间
	private Integer active;  //是否启用, 1启用, 0停用

	public DarkSettingVo id(String id) {
		setId(id);
		return this;
	}

	public DarkSettingVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setUsername(String username){  
        this.username = username;  
    }  
      
    public String getUsername(){  
        return this.username;  
    }
	
	public void setStartTime(String startTime){  
        this.startTime = startTime;  
    }  
      
    public String getStartTime(){  
        return this.startTime;  
    }
	
	public void setEndTime(String endTime){  
        this.endTime = endTime;  
    }  
      
    public String getEndTime(){  
        return this.endTime;  
    }
	
	public void setActive(Integer active){  
        this.active = active;  
    }  
      
    public Integer getActive(){  
        return this.active;  
    }

	public String getBusiGroup() {
		return busiGroup;
	}

	public void setBusiGroup(String busiGroup) {
		this.busiGroup = busiGroup;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}