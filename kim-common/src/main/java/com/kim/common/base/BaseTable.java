package com.kim.common.base;

import com.kim.common.util.DateUtil;
import com.kim.common.util.TokenUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by bo.liu01 on 2017/10/30.
 */
public class BaseTable extends BaseObject {

    private static final long serialVersionUID = 6805277017469099069L;
    
    /** 正常数据 */
    public static final int ATV_FLAG_YES = 1;
    /** 已打了删除标记，并在每天晚上会自动移动到对应的his表 */
    public static final int ATV_FLAG_NO = 0;
    /** 已打了删除标记，但并不会自动移动到对应的his表 */
    public static final int ATV_FLAG_NO_HIS = 2;

    protected String id;// 主键ID.
    protected String tenantId;//租户ID
    protected String remark; //备注

    protected String operUser; //操作人账号
    protected String operName; //操作人名字
    protected String operTime; //操作时间

    protected Integer atvFlag = ATV_FLAG_YES;

    //表后缀，用于分表情况
    protected String tableSuffix;
    
    public BaseTable(){
    	
    }
    
    public BaseTable(boolean init){
    	if(init){
    		setTenantId(TokenUtil.getTenantId());
    	}
    }
    
    /**
     * 复制 operName, operUser, operTime字段
     * @param baseTable
     */
    public void copyOperField(BaseTable baseTable){
    	setOperName(baseTable.getOperName());
		setOperUser(baseTable.getOperUser());
		setOperTime(baseTable.getOperTime());
    }
    
    /**
     * 复制 operName, operUser, operTime, tenantId 字段
     * @param baseTable
     */
    public void copyBaseField(BaseTable baseTable){
    	setOperName(baseTable.getOperName());
    	setOperUser(baseTable.getOperUser());
    	setOperTime(baseTable.getOperTime());
    	setTenantId(baseTable.getTenantId());
    }
    
    /**
     * 初始化 operName, operUser, operTime, tenantId 字段
     * @param baseTable
     */
    public void initBaseField(){
    	setOperName(TokenUtil.getCurrentName());
    	setOperUser(TokenUtil.getUsername());
    	setOperTime(DateUtil.getCurrentTime());
    	setTenantId(TokenUtil.getTenantId());
    }
    
    @JsonIgnore
    public String getTableSuffix() {
        return tableSuffix;
    }

    public void setTableSuffix(String tableSuffix) {
        this.tableSuffix = tableSuffix;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public BaseTable setTenantId(String tenantId){  
        this.tenantId = tenantId;  
        return this;
    }  
    
    public String getTenantId(){
        return this.tenantId;  
    }

    public String getOperUser() {
        return operUser;
    }

    public void setOperUser(String operUser) {
        this.operUser = operUser;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getOperTime() {
        return operTime;
    }

    public void setOperTime(String operTime) {
        this.operTime = operTime;
    }

    public Integer getAtvFlag() {
        return atvFlag;
    }

    public void setAtvFlag(Integer atvFlag) {
        this.atvFlag = atvFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
