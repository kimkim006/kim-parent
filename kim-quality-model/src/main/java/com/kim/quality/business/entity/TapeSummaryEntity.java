package com.kim.quality.business.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 录音小结关联表实体类
 * @date 2018-11-14 16:19:30
 * @author bo.liu01
 */
public class TapeSummaryEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	private String date;//数据日期
	private String serialNumber;//录音流水号
	private String typeCode;//类型编码
	private String firstCode;//一级编码
	private String secondCode;//二级编码
	private String thirdCode;//三级编码
	private String forthCode;//四级编码
	private String originTypeCode;//原始类型编码
	private String originFirstCode;//原始一级编码
	private String originSecondCode;//原始二级编码
	private String originThirdCode;//原始三级编码
	private String originForthCode;//原始四级编码
	
	private String custIdCard;//客户身份证
	private String ivrVerify;//ivr验证
	private Integer ivrVerifyCode;//ivr验证编码
	
	private String source;//来源
	
	//非数据库字段
	private String typeName;//类型名称
	private String firstName;//一级名称
	private String secondName;//二级名称
	private String thirdName;//三级名称
	private String forthName;//四级名称
	
	public TapeSummaryEntity id(String id) {
		setId(id);
		return this;
	}

	public TapeSummaryEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setSerialNumber(String serialNumber){  
        this.serialNumber = serialNumber;  
    }  
    
	@FieldDisplay("录音流水号")
    public String getSerialNumber(){
        return this.serialNumber;  
    }
	
	public void setTypeCode(String typeCode){  
        this.typeCode = typeCode;  
    }  
    
	@FieldDisplay("类型编码")
    public String getTypeCode(){
        return this.typeCode;  
    }
	
	public void setOriginTypeCode(String originTypeCode){  
        this.originTypeCode = originTypeCode;  
    }  
    
	@FieldDisplay("原始类型编码")
    public String getOriginTypeCode(){
        return this.originTypeCode;  
    }
	
	public void setFirstCode(String firstCode){  
        this.firstCode = firstCode;  
    }  
    
	@FieldDisplay("一级编码")
    public String getFirstCode(){
        return this.firstCode;  
    }
	
	public void setSecondCode(String secondCode){  
        this.secondCode = secondCode;  
    }  
    
	@FieldDisplay("二级编码")
    public String getSecondCode(){
        return this.secondCode;  
    }
	
	public void setThirdCode(String thirdCode){  
        this.thirdCode = thirdCode;  
    }  
    
	@FieldDisplay("三级编码")
    public String getThirdCode(){
        return this.thirdCode;  
    }
	
	public void setForthCode(String forthCode){  
        this.forthCode = forthCode;  
    }  
    
	@FieldDisplay("四级编码")
    public String getForthCode(){
        return this.forthCode;  
    }
	
	public void setOriginFirstCode(String originFirstCode){  
        this.originFirstCode = originFirstCode;  
    }  
    
	@FieldDisplay("原始一级编码")
    public String getOriginFirstCode(){
        return this.originFirstCode;  
    }
	
	public void setOriginSecondCode(String originSecondCode){  
        this.originSecondCode = originSecondCode;  
    }  
    
	@FieldDisplay("原始二级编码")
    public String getOriginSecondCode(){
        return this.originSecondCode;  
    }
	
	public void setOriginThirdCode(String originThirdCode){  
        this.originThirdCode = originThirdCode;  
    }  
    
	@FieldDisplay("原始三级编码")
    public String getOriginThirdCode(){
        return this.originThirdCode;  
    }
	
	public void setOriginForthCode(String originForthCode){  
        this.originForthCode = originForthCode;  
    }  
    
	@FieldDisplay("原始四级编码")
    public String getOriginForthCode(){
        return this.originForthCode;  
    }
	
	public void setSource(String source){  
        this.source = source;  
    }  
    
	@FieldDisplay("来源")
    public String getSource(){
        return this.source;  
    }

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCustIdCard() {
		return custIdCard;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getForthName() {
		return forthName;
	}

	public void setForthName(String forthName) {
		this.forthName = forthName;
	}

	public void setCustIdCard(String custIdCard) {
		this.custIdCard = custIdCard;
	}

	public String getIvrVerify() {
		return ivrVerify;
	}

	public void setIvrVerify(String ivrVerify) {
		this.ivrVerify = ivrVerify;
	}

	public Integer getIvrVerifyCode() {
		return ivrVerifyCode;
	}

	public void setIvrVerifyCode(Integer ivrVerifyCode) {
		this.ivrVerifyCode = ivrVerifyCode;
	}
	
}