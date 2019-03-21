package com.kim.log.entity;

import com.kim.common.base.BaseEntity;
import com.kim.log.annotation.FieldDisplay;

/**
 * Created by bo.liu01 on 2017/11/1.
 */
public class LoggedEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@FieldDisplay("id")
    public String getId() {
        return id;
    }
	
	@FieldDisplay("租户编码")
    public String getTenantId(){
        return this.tenantId;  
    }

    @FieldDisplay("操作人账号")
    public String getOperUser() {
        return operUser;
    }

    @FieldDisplay("操作人名字")
    public String getOperName() {
        return operName;
    }

    @FieldDisplay("操作时间")
    public String getOperTime() {
        return operTime;
    }

    @FieldDisplay("备注")
    public String getRemark() {
        return remark;
    }

}
