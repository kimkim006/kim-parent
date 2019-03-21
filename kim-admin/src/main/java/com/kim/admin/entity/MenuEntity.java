package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单表实体类
 * @date 2017-11-10 11:18:18
 * @author bo.liu01
 */
public class MenuEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L;

    /** 类型, 1父菜单 */
	public static final int TYPE_MENU_DIR = 1;
    /** 类型, 2子菜单 */
	public static final int TYPE_MENU = 2;
    /** 类型, 3按钮 */
	public static final int TYPE_BUTTON = 3;

	private String parentName;//父菜单名称
	private String parentCode;//父菜单
	private String name;//名称
	private String permissionName;//权限名称提示，只有按钮才使用该字段
	private String code;//编码
	private Integer type;//类型, 1父菜单，2子菜单，3按钮
	private String url;//链接
	private String icon;//图标
	private Integer sortNum;//排序号
	
	private String fullPath;//完成路径
	private boolean disabled;//在前端树中是否禁用

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    private List<MenuEntity> children = new ArrayList<>();

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<MenuEntity> getChildren() {
        return children;
    }

    public void setChildren(List<MenuEntity> children) {
        this.children = children;
    }

    public void add(MenuEntity menu) {
        this.children.add(menu);
    }

    public void setParentCode(String parentCode){
        this.parentCode = parentCode;  
    }  
    
	@FieldDisplay("父菜单")
    public String getParentCode(){
        return this.parentCode;  
    }
	
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
	
	public void setType(Integer type){  
        this.type = type;  
    }  
    
	@FieldDisplay("类型, 1父菜单，2子菜单，3按钮")
    public Integer getType(){
        return this.type;  
    }
	
	public void setUrl(String url){  
        this.url = url;  
    }  
    
	@FieldDisplay("链接")
    public String getUrl(){
        return this.url;  
    }
	
	public void setIcon(String icon){  
        this.icon = icon;  
    }  
    
	@FieldDisplay("图标")
    public String getIcon(){
        return this.icon;  
    }
	
	public void setSortNum(Integer sortNum){  
        this.sortNum = sortNum;  
    }  
    
	@FieldDisplay("排序号")
    public Integer getSortNum(){
        return this.sortNum;  
    }
	
	@FieldDisplay("完整路径")
	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

}