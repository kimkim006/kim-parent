package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 用户信息表参数封装类
 * @date 2017-11-8 15:34:55
 * @author bo.liu01
 */
public class LoginUserVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	protected String username;  //用户名
	protected String password;  //密码
	protected Integer status;  //状态，1可用，2不可用，3已注销
	protected String origins;  //用户来源
	
	public void setId(String id) {
		super.setId(id);
    }
	
	public LoginUserVo setUsername(String username){  
        this.username = username; 
        return this;
    }  
      
    public String getUsername(){  
        return this.username;  
    }
	
	public void setPassword(String password){  
        this.password = password;  
    }  
      
    public String getPassword(){  
        return this.password;  
    }
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
      
    public Integer getStatus(){  
        return this.status;  
    }

	public String getOrigins() {
		return origins;
	}

	public void setOrigins(String origins) {
		this.origins = origins;
	}
    
    
	
}