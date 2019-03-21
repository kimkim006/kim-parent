package com.kim.admin.entity;

import com.kim.impexp.excel.entity.ImportEntity;
import com.kim.log.annotation.FieldDisplay;

/**
 * 用户信息表实体类
 * @date 2017-11-8 15:34:55
 * @author bo.liu01
 */
public class LoginUserEntity extends ImportEntity{
	
	private static final long serialVersionUID = 1L;

    /** 状态，1可用 */
	public final static int STATUS_NORMAL = 1;
    /** 状态，2不可用 */
	public final static int STATUS_UNUSABLE = 2;
    /** 状态，3已注销 */
	public final static int STATUS_LOGOFF = 3;
	
	/** 登录类型，0域账号登录 */
	public final static String LOGIN_TYPE_LDAP = "0";
	/** 登录类型，1账号密码登录 */
	public final static String LOGIN_TYPE_SIMPLE = "1";
	
	/** 用户来源,默认 */
	public static final String ORIGINS_DEFUALT = "icm";
	/** 用户来源,用户导入 */
	public static final String ORIGINS_ICM_IMPORT = "icm_import";
	/** 用户来源,域账号 */
	public static final String ORIGINS_LDAP = "ldap";
	/** 用户来源, 统一登录 */
	public static final String ORIGINS_SSO = "sso";
	
	/**操作类型，1修改密码 **/
	public static final int OPER_TYPE_CHANGE_PASSWORD = 1;
	/**操作类型，2重置密码 **/
	public static final int OPER_TYPE_RESET_PASSWORD = 2;
	
	/** 是否加载软电话 1是 **/
	public static final String LOAD_SOFT_PHONE_YES = "1";
	/** 是否加载软电话 0否 **/
	public static final String LOAD_SOFT_PHONE_NO = "0";

	protected String username;//用户名
	protected String password;//密码
	protected Integer status;//状态，1可用，2不可用，3已注销
	protected String origins;//用户来源
	protected String portrait;//头像

	protected String loadSoftPhone;//是否加载软电话 1是 0否
	protected String loginType;//登录类型
	protected String loginTime;//登录时间

	/**新密码**/
	private String newPassword;
	/**操作类型，修改密码1/重置密码2**/
	private Integer operType;
	
    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public void setUsername(String username){
        this.username = username;  
    } 
    
    @FieldDisplay("头像")
   	public String getPortrait() {
   		return portrait;
   	}

   	public void setPortrait(String portrait) {
   		this.portrait = portrait;
   	}
    
	@FieldDisplay("用户名")
    public String getUsername(){
        return this.username;  
    }
	
	public void setPassword(String password){  
        this.password = password;  
    }  
    
	@FieldDisplay("密码")
    public String getPassword(){
        return this.password;  
    }
	
	public void setStatus(Integer status){  
        this.status = status;  
    }  
    
	@FieldDisplay("状态，1可用，2不可用，3已注销")
    public Integer getStatus(){
        return this.status;  
    }

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	@FieldDisplay("用户来源")
	public String getOrigins() {
		return origins;
	}

	public void setOrigins(String origins) {
		this.origins = origins;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public String getLoadSoftPhone() {
		return loadSoftPhone;
	}

	public void setLoadSoftPhone(String loadSoftPhone) {
		this.loadSoftPhone = loadSoftPhone;
	}
}