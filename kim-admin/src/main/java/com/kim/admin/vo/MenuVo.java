package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单表参数封装类
 * @date 2017-11-10 11:18:18
 * @author bo.liu01
 */
public class MenuVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String parentCode;  //父菜单
	private String name;  //名称
	private String code;  //编码
	private Integer type;  //类型, 1父菜单，2子菜单，3按钮
	private List<Integer> types;  //类型, 1父菜单，2子菜单，3按钮
	private String url;  //链接
	private String icon;  //图标
	private Integer sortNum;  //排序号
	private String path;  //路径

    public List<Integer> getTypes() {
        return types;
    }

    public void setTypes(List<Integer> types) {
        this.types = types;
    }

    public MenuVo addType(Integer type) {
        if(this.types == null){
            this.types = new ArrayList<>();
        }
        types.add(type);
        return this;
    }

    public void setParentCode(String parentCode){
        this.parentCode = parentCode;  
    }  
      
    public String getParentCode(){
        return this.parentCode;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
	
	public void setCode(String code){  
        this.code = code;  
    }  
      
    public String getCode(){  
        return this.code;  
    }
	
	public void setType(Integer type){  
        this.type = type;  
    }  
      
    public Integer getType(){  
        return this.type;  
    }
	
	public void setUrl(String url){  
        this.url = url;  
    }  
      
    public String getUrl(){  
        return this.url;  
    }
	
	public void setIcon(String icon){  
        this.icon = icon;  
    }  
      
    public String getIcon(){  
        return this.icon;  
    }
	
	public void setSortNum(Integer sortNum){  
        this.sortNum = sortNum;  
    }  
      
    public Integer getSortNum(){  
        return this.sortNum;  
    }
	
	public void setPath(String path){  
        this.path = path;  
    }  
      
    public String getPath(){  
        return this.path;  
    }

	public MenuVo tenantId(String tenantId) {
		this.tenantId = tenantId;
		return this;
	}
	
}