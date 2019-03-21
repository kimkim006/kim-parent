package com.kim.admin.entity;

import com.kim.log.annotation.FieldDisplay;

/**
 * 用户表实体类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
public class UserEntity extends LoginUserEntity{
	
	private static final long serialVersionUID = 1L;

	protected String name;//姓名
    protected String phone;//电话
    protected String email;//邮箱
    protected String joinDate;//入职日期
    protected String leaveDate;//离职日期
    
    private String departCode;//部门编码
    private String departName;//部门名称
    private String postId;//职务id
    
    private Integer type;//组内成员类型, 0组员, 1组长
    
    private String rightTenantId;//实际操作的租户id，超级管理员新增和修改用户的时候使用
	private String path;
	private String sign;  //头像加密签名
	
	private String ciscoAgentNo;  //思科工号

	public void setName(String name){
        this.name = name;  
    }  
    
	@FieldDisplay("真实姓名")
    public String getName(){
        return this.name;  
    }
	
	public void setPhone(String phone){  
        this.phone = phone;  
    }  
    
	@FieldDisplay("电话")
    public String getPhone(){
        return this.phone;  
    }
	
	public void setEmail(String email){  
        this.email = email;  
    }  
    
	@FieldDisplay("邮箱")
    public String getEmail(){
        return this.email;  
    }

	@FieldDisplay("入职日期")
	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	@FieldDisplay("离职日期")
	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getDepartCode() {
		return departCode;
	}

	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getRightTenantId() {
		return rightTenantId;
	}

	public void setRightTenantId(String rightTenantId) {
		this.rightTenantId = rightTenantId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getCiscoAgentNo() {
		return ciscoAgentNo;
	}

	public void setCiscoAgentNo(String ciscoAgentNo) {
		this.ciscoAgentNo = ciscoAgentNo;
	}
}