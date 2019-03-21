package com.kim.quality.business.entity;

import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 小结表实体类
 * @date 2018-11-14 16:19:30
 * @author bo.liu01
 */
public class SummaryEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 来源, 由icm系统创建 */
	public static final String SOURCE_ICM = "icm";
	/** 来源, 佰仟 */
	public static final String SOURCE_BQ = "bq";
	/** 来源, 买买提 */
	public static final String SOURCE_MMT = "mmt";
	/** 来源, 租赁 */
	public static final String SOURCE_ZL = "zl";
	
	private String date;//数据日期
	private String parentCode;//父编码
	private String code;//编码
	private String name;//名称
	private String originParentCode;//原始父编码
	private String originCode;//原始编码
	private Integer level;//层级, 0服务类型, 1-4表示来电N级
	private String source;//来源
	
	public static String getSourceName(String source){
		switch (source) {
		case SOURCE_BQ: return "佰仟";
		case SOURCE_MMT: return "买买提";
		case SOURCE_ZL: return "租赁";
		default:return source;
		}
	}
	
	public SummaryEntity id(String id) {
		setId(id);
		return this;
	}

	public SummaryEntity tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}
	
	public void setParentCode(String parentCode){  
        this.parentCode = parentCode;  
    }  
    
	@FieldDisplay("父编码")
    public String getParentCode(){
        return this.parentCode;  
    }
	
	public void setCode(String code){  
        this.code = code;  
    }  
    
	@FieldDisplay("编码")
    public String getCode(){
        return this.code;  
    }
	
	public void setName(String name){  
        this.name = name;  
    }  
    
	@FieldDisplay("名称")
    public String getName(){
        return this.name;  
    }
	
	public void setOriginParentCode(String originParentCode){  
        this.originParentCode = originParentCode;  
    }  
    
	@FieldDisplay("原始父编码")
    public String getOriginParentCode(){
        return this.originParentCode;  
    }
	
	public void setOriginCode(String originCode){  
        this.originCode = originCode;  
    }  
    
	@FieldDisplay("原始编码")
    public String getOriginCode(){
        return this.originCode;  
    }
	
	public void setSource(String source){  
        this.source = source;  
    }  
    
	@FieldDisplay("来源")
    public String getSource(){
        return this.source;  
    }

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}