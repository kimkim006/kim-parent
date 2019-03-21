package com.kim.sp.vo;

import com.kim.common.base.BaseVo;

/**
 * 随路数据表参数封装类
 * @date 2019-3-7 15:41:35
 * @author liubo
 */
public class RoadDataVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String recordId;  //录音Id
	private String phone;  //手机号
	private String idNum;  //身份证号
	private String isPhoneChecked;  //手机验证是否通过
	private String isIdNumChecked;  //身份证验证是否通过
	private String roadData;  //原始随路数据
	private Integer isBindWx;  //是否绑定微信 1是, 0否
	private String serviceId;  //服务ID
	private String isUrge;  //是否m0催收
	private String name;  //用户姓名
	private String username;  //用户工号
	private String agentId;  //话务工号

	public RoadDataVo id(String id) {
		setId(id);
		return this;
	}

	public RoadDataVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setRecordId(String recordId){  
        this.recordId = recordId;  
    }  
      
    public String getRecordId(){  
        return this.recordId;  
    }
    
	
	public void setPhone(String phone){  
        this.phone = phone;  
    }  
      
    public String getPhone(){  
        return this.phone;  
    }
    
	
	public void setIdNum(String idNum){  
        this.idNum = idNum;  
    }  
      
    public String getIdNum(){  
        return this.idNum;  
    }
    
	
	public void setIsPhoneChecked(String isPhoneChecked){  
        this.isPhoneChecked = isPhoneChecked;  
    }  
      
    public String getIsPhoneChecked(){  
        return this.isPhoneChecked;  
    }
    
	
	public void setIsIdNumChecked(String isIdNumChecked){  
        this.isIdNumChecked = isIdNumChecked;  
    }  
      
    public String getIsIdNumChecked(){  
        return this.isIdNumChecked;  
    }
    
	
	public void setRoadData(String roadData){  
        this.roadData = roadData;  
    }  
      
    public String getRoadData(){  
        return this.roadData;  
    }
    
	
	public void setIsBindWx(Integer isBindWx){  
        this.isBindWx = isBindWx;  
    }  
      
    public Integer getIsBindWx(){  
        return this.isBindWx;  
    }
    
	
	public void setServiceId(String serviceId){  
        this.serviceId = serviceId;  
    }  
      
    public String getServiceId(){  
        return this.serviceId;  
    }
    
	
	public void setIsUrge(String isUrge){  
        this.isUrge = isUrge;  
    }  
      
    public String getIsUrge(){  
        return this.isUrge;  
    }
    
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
    
	
	public void setUsername(String username){  
        this.username = username;  
    }  
      
    public String getUsername(){  
        return this.username;  
    }
    
	
	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
      
    public String getAgentId(){  
        return this.agentId;  
    }
    
	
}