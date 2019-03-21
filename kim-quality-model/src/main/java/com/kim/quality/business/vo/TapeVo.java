package com.kim.quality.business.vo;

import java.util.List;

import com.kim.common.base.BaseVo;

/**
 * 录音池表参数封装类
 * @date 2018-8-21 14:01:02
 * @author bo.liu01
 */
public class TapeVo extends BaseVo{ 

	private static final long serialVersionUID = 1L; 
	
	protected List<String> mainTaskIdList;  //任务id
	protected String mainTaskId;  //任务id
	
	protected List<String> serviceNos;  //服务号码
	protected List<String> agentNos;  //坐席思科号
	protected String startTime;//开始时间
	protected String endTime;//结束时间
	protected Integer limit; 
	protected String userType;//员工类型
	
	protected List<String> darkList;//小黑屋员工名单
	
	//弹屏小结抽取规则使用 start 
	private String typeCode;//类型编码
	private String firstCode;//一级编码
	private String secondCode;//二级编码
	private String thirdCode;//三级编码
	private String forthCode;//四级编码
	//弹屏小结抽取规则使用 end 
	
	protected String serialNumber;  //录音流水号
	protected Integer type;  //呼叫类型, 1呼入, 2呼出
	protected Integer satisfactionType;  //满意类型, 1满意, 2不满意
	protected String contractNo;  //
	protected String agentId;  //坐席工号
	protected String custPhone;  //客户电话
	protected String agentPhone;  //坐席分机号
	protected String agentNo;  //思科工号
	protected String platform;  //录音平台
	protected String serviceNo;  //服务号码
	protected Integer hangupType;  //挂断类型, 1坐席挂断, 2客户挂断
	protected String mediaType;  //媒体类型
	
	protected Integer recordTimeStart;//录音时长
	protected Integer recordTimeEnd;//录音时长
	
	public TapeVo id(String id) {
		setId(id);
		return this;
	}

	public TapeVo tenantId(String tenantId) {
		setTenantId(tenantId);
		return this;
	}

	public List<String> getMainTaskIdList() {
		return mainTaskIdList;
	}

	public void setMainTaskIdList(List<String> mainTaskIdList) {
		this.mainTaskIdList = mainTaskIdList;
	}

	public void setSerialNumber(String serialNumber){  
        this.serialNumber = serialNumber;  
    }  
      
    public String getSerialNumber(){  
        return this.serialNumber;  
    }
	
	public void setType(Integer type){  
        this.type = type;  
    }  
      
    public List<String> getAgentNos() {
		return agentNos;
	}

	public void setAgentNos(List<String> agentNos) {
		this.agentNos = agentNos;
	}

	public Integer getType(){  
        return this.type;  
    }
	
	public void setSatisfactionType(Integer satisfactionType){  
        this.satisfactionType = satisfactionType;  
    }  
      
    public Integer getSatisfactionType(){  
        return this.satisfactionType;  
    }
	
	public void setContractNo(String contractNo){  
        this.contractNo = contractNo;  
    }  
      
    public String getContractNo(){  
        return this.contractNo;  
    }
	
	public List<String> getDarkList() {
		return darkList;
	}

	public void setDarkList(List<String> darkList) {
		this.darkList = darkList;
	}

	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
      
    public String getAgentId(){  
        return this.agentId;  
    }
	
	public void setCustPhone(String custPhone){  
        this.custPhone = custPhone;  
    }  
      
    public String getCustPhone(){  
        return this.custPhone;  
    }
	
	public void setAgentPhone(String agentPhone){  
        this.agentPhone = agentPhone;  
    }  
      
    public String getAgentPhone(){  
        return this.agentPhone;  
    }
	
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getFirstCode() {
		return firstCode;
	}

	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}

	public String getSecondCode() {
		return secondCode;
	}

	public void setSecondCode(String secondCode) {
		this.secondCode = secondCode;
	}

	public String getThirdCode() {
		return thirdCode;
	}

	public void setThirdCode(String thirdCode) {
		this.thirdCode = thirdCode;
	}

	public String getForthCode() {
		return forthCode;
	}

	public void setForthCode(String forthCode) {
		this.forthCode = forthCode;
	}

	public void setAgentNo(String agentNo){  
        this.agentNo = agentNo;  
    }  
      
    public String getAgentNo(){  
        return this.agentNo;  
    }
	
	public void setPlatform(String platform){  
        this.platform = platform;  
    }  
      
    public String getPlatform(){  
        return this.platform;  
    }
	
	public void setServiceNo(String serviceNo){  
        this.serviceNo = serviceNo;  
    }  
      
    public String getServiceNo(){  
        return this.serviceNo;  
    }

	public List<String> getServiceNos() {
		return serviceNos;
	}

	public void setServiceNos(List<String> serviceNos) {
		this.serviceNos = serviceNos;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public Integer getRecordTimeStart() {
		return recordTimeStart;
	}

	public void setRecordTimeStart(Integer recordTimeStart) {
		this.recordTimeStart = recordTimeStart;
	}

	public Integer getRecordTimeEnd() {
		return recordTimeEnd;
	}

	public void setRecordTimeEnd(Integer recordTimeEnd) {
		this.recordTimeEnd = recordTimeEnd;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	public TapeVo limit(int count, int percent) {
		int n = count * percent / 100;
		setLimit(n < 1 ? 1: n);
		return this;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getMainTaskId() {
		return mainTaskId;
	}

	public void setMainTaskId(String mainTaskId) {
		this.mainTaskId = mainTaskId;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public Integer getHangupType() {
		return hangupType;
	}

	public void setHangupType(Integer hangupType) {
		this.hangupType = hangupType;
	}
	
}