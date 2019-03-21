package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 资源表参数封装类
 * @date 2017-11-10 17:57:35
 * @author bo.liu01
 */
public class ResourceVo extends BaseVo{ 

	private static final long serialVersionUID = 1L;
    /** 菜单编码是否使用反向条件,1使用 */
	public static final int MENU_CODE_EXPECT_YES = 1;
    /** 菜单编码是否使用反向条件,0不使用 */
	public static final int MENU_PATH_EXPECT_NO = 0;

	private String name;  //名称
	private String code;  //编码
	private String moduleCode;  //模块
    private String url;  //链接
    private String method;  //请求方法

    private String menuCode;  //菜单编码
    private int menuCodeExpect = MENU_PATH_EXPECT_NO;  //菜单编码是否使用反向条件,默认不使用，1则使用

    public int getMenuCodeExpect() {
        return menuCodeExpect;
    }

    public void setMenuCodeExpect(int menuCodeExpect) {
        this.menuCodeExpect = menuCodeExpect;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
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
	
	public void setModuleCode(String moduleCode){  
        this.moduleCode = moduleCode;  
    }  
      
    public String getModuleCode(){  
        return this.moduleCode;  
    }
	
	public void setUrl(String url){  
        this.url = url;  
    }  
      
    public String getUrl(){  
        return this.url;  
    }
	
	public void setMethod(String method){  
        this.method = method;  
    }  
      
    public String getMethod(){  
        return this.method;  
    }
	
}