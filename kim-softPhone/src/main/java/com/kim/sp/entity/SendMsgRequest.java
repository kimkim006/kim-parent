package com.kim.sp.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kim.common.base.BaseObject;
import com.kim.common.util.CollectionUtil;

/**
 * 短信发送接口实体类
 */
public class SendMsgRequest extends BaseObject{

	private static final long serialVersionUID = 324975790657696033L;

	/** 业务系统代码 */
    private String appCode;

	/** 发送类型 */
	private String businessType;

	/** 模板编号 */
	private String templCode;

	/** 业务系统请求批次号 */
	private String businessNo;

	/** 定时日期 */
	private String atTime;

	/** 手机号码 */
	private String phoneNums;

	/** 参数 */
	private List<Object> paramsBody;

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getTemplCode() {
		return templCode;
	}

	public void setTemplCode(String templCode) {
		this.templCode = templCode;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getAtTime() {
		return atTime;
	}

	public void setAtTime(String atTime) {
		this.atTime = atTime;
	}

	public String getPhoneNums() {
		return phoneNums;
	}

	public void setPhoneNums(String phoneNums) {
		this.phoneNums = phoneNums;
	}

	public Map<String, Object> getParamsBody() {
		if(CollectionUtil.isEmpty(paramsBody)){
			return Collections.emptyMap();
		}
		Map<String, Object> map = new HashMap<>();
		int n = 0;
		for (Object object : paramsBody) {
			map.put("value"+n, object);
			n++;
		}
		return map;
	}

	public void setParamsBody(List<Object> paramsBody) {
		this.paramsBody = paramsBody;
	}
	
	public void addParams(Object object) {
		if(this.paramsBody == null){
			this.paramsBody = new ArrayList<>();
		}
		this.paramsBody.add(object);
	}

}
