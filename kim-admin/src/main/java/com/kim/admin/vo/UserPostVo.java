package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 用户职位表参数封装类
 * @date 2018-7-13 15:07:28
 * @author bo.liu01
 */
public class UserPostVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String username;  //用户名/工号
	private String postId;  //职务id

	public void setId(String id) {
		super.setId(id);
    }
	
	public UserPostVo setUsername(String username){  
        this.username = username;  
        return this;
    }  
      
    public String getUsername(){  
        return this.username;  
    }
	
	public UserPostVo setPostId(String postId){  
        this.postId = postId;  
        return this;
    }  
      
    public String getPostId(){  
        return this.postId;  
    }

	public UserPostVo id(String id) {
		setId(id);
		return this;
	}
	
}