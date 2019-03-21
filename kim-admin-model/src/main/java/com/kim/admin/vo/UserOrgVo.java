package com.kim.admin.vo;

import com.kim.common.base.BaseVo;

/**
 * 组织人员关系表参数封装类
 * @date 2018-9-4 14:40:25
 * @author yonghui.wu
 */
public class UserOrgVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	private String itemCode;  //用户工号或组编码
	private Integer codeType;  //item类型，1用户工号, 2组编码
	private String upperSuperior;  //上级领导
	
	/**附加条件**/
	private String itemCodes;   //小组编码
	private String status;      //上级领导选择状态
	private String name;        //用户姓名或小组编码名称
	
	public UserOrgVo id(String id) {
		setId(id);
		return this;
	}

	public UserOrgVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public void setItemCode(String itemCode){  
        this.itemCode = itemCode;  
    }  
      
    public String getItemCode(){  
        return this.itemCode;  
    }
	
	public void setCodeType(Integer codeType){  
        this.codeType = codeType;  
    }  
      
    public Integer getCodeType(){  
        return this.codeType;  
    }
	
	public void setUpperSuperior(String upperSuperior){  
        this.upperSuperior = upperSuperior;  
    }  
      
    public String getUpperSuperior(){  
        return this.upperSuperior;  
    }

	public String getItemCodes() {
		return itemCodes;
	}

	public void setItemCodes(String itemCodes) {
		this.itemCodes = itemCodes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}