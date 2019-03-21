package com.kim.log.entity;

import com.kim.log.annotation.FieldDisplay;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 资源表实体类
 * @date 2017-11-10 17:57:35
 * @author bo.liu01
 */
public class Resource extends BaseRes {
	
	private static final long serialVersionUID = 1L; 
	
    protected String moduleCode;//模块
    protected String method;//请求方法
    protected String classMethod;//请求方法

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public void setModuleCode(String moduleCode){
        this.moduleCode = moduleCode;  
    }  
    
	@FieldDisplay("模块")
    public String getModuleCode(){
        return this.moduleCode;  
    }
	
	public void setMethod(String method){
        this.method = method;  
    }

	public void setMethod(RequestMethod[] methods){
	    if(methods != null && methods.length > 0){
            StringBuilder sb = new StringBuilder();
            for(RequestMethod md : methods){
                sb.append(md.name()).append(",");
            }
            if(sb.length() > 0){
                sb.deleteCharAt(sb.length() -1);
            }
            this.method = sb.toString();
        }else{
            this.method = "*";
        }
    }

	@FieldDisplay("请求方法")
    public String getMethod(){
        return this.method;  
    }

}