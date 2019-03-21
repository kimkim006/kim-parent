package com.kim.admin.vo;

/**
 * 用户表参数封装类
 * @date 2017-11-6 14:06:20
 * @author bo.liu01
 */
public class UserVo extends LoginUserVo{

	private static final long serialVersionUID = 1L; 
	
	private String usernames;  //
	
	private String name;  //真实姓名
	private String phone;  //电话
	private String email;  //邮箱
	private String joinDate;//入职日期
	private String leaveDate;//离职日期

	private String departmentCode;  //部门ID
	private String roleCode;  //角色编码
	private String groupCode;  //质检小组编码
	private Integer groupUserType;  //小组成员类型, 0组员, 1组长

	public UserVo setUsername(String username){  
        this.username = username; 
        return this;
    }  
	
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public void setName(String name){
        this.name = name;  
    }  
      
    public String getName(){  
        return this.name;  
    }
	
	public void setPhone(String phone){  
        this.phone = phone;  
    }  
      
    public String getPhone(){  
        return this.phone;  
    }
	
	public void setEmail(String email){  
        this.email = email;  
    }  
      
    public String getEmail(){  
        return this.email;  
    }

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getUsernames() {
		return usernames;
	}

	public void setUsernames(String usernames) {
		this.usernames = usernames;
	}

	public UserVo id(String id) {
		setId(id);
		return this;
	}

	public UserVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Integer getGroupUserType() {
		return groupUserType;
	}

	public void setGroupUserType(Integer groupUserType) {
		this.groupUserType = groupUserType;
	}
}