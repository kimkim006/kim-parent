package com.kim.log.entity;

import com.kim.log.annotation.FieldDisplay;

/**
 * Created by bo.liu01 on 2017/11/13.
 */
public class BaseRes extends LoggedEntity {
	private static final long serialVersionUID = 1L;

    protected String serviceId;//服务id
    protected String name;//名称
    protected String code;//编码
    protected String url;//链接

    public void setName(String name){
        this.name = name;
    }

    @FieldDisplay("名称")
    public String getName(){
        return this.name;
    }

    public void setCode(String code){
        this.code = code;
    }

    @FieldDisplay("编码")
    public String getCode(){
        return this.code;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setUrl(String[] urls) {
        if(urls != null && urls.length > 0){
            if(urls[0].startsWith("/")){
                this.url = urls[0];
            }else {
                this.url = "/"+urls[0];
            }
        }else{
            this.url = "/";
        }
    }

    @FieldDisplay("链接")
    public String getUrl(){
        return this.url;
    }

    @FieldDisplay("服务id")
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

}
