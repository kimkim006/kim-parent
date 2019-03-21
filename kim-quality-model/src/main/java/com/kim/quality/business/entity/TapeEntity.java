package com.kim.quality.business.entity;

import com.kim.common.util.StringUtil;
import com.kim.common.util.UUIDUtils;
import com.kim.log.annotation.FieldDisplay;
import com.kim.log.entity.LoggedEntity;

/**
 * 录音池表实体类
 * @date 2018-8-21 14:01:02
 * @author bo.liu01
 */
public class TapeEntity extends LoggedEntity{
	
	private static final long serialVersionUID = 1L; 
	
	/** 呼叫类型名称 */
	public static final String TYPE_NAME_IN = "呼入";
	/** 呼叫类型名称 */
	public static final String TYPE_NAME_OUT = "呼出";
	
	/** 呼叫类型, 1呼入 */
	public static final int TYPE_IN = 1;
	/** 呼叫类型, 2呼出 */
	public static final int TYPE_OUT = 2;
	
	protected String syncId;//转储id
	protected String serialNumber;//录音流水号
	protected Integer type;//呼叫类型, 1呼入, 2呼出
	protected String direction;//呼叫方式，中文显示
	protected Integer satisfactionType;//满意类型, 1满意, 2不满意
	protected String contractNo;//合同编号
	protected String busiGroupCode;//业务小组
	protected String custName;//客户姓名
	protected String agentId;//坐席工号
	protected String custPhone;//客户电话
	protected String agentPhone;//坐席分机号
	protected String agentName;//坐席姓名
	protected String agentNo;//思科工号
	protected Integer recordTime;//录音时长
	protected String startTime;//录音开始时间
	protected String endTime;//录音结束时间
	protected Integer hangupType;//挂断类型, 1坐席挂断, 2客户挂断
	protected String platform;//录音平台
	protected String serviceNo;//服务号码
	protected String mediaType;//媒体类型
	protected String address;//录音地址
	protected String localpath;//
	protected String localfile;//
	protected String recserverip;//
	
	protected String localAddress;//通过本地的文件映射表关联的地址
	protected String sign;//localAddress地址的签名
	
	//使用mybatis自动映射属性，对象必须不能为空
	protected IvrInfoEntity ivrInfo = new IvrInfoEntity();//ivr验证信息
	protected TapeSummaryEntity summary = new TapeSummaryEntity();//弹屏小结
	
	protected boolean ivrIsLoad = false;
	protected boolean summaryIsLoad = false;
	
	/**
	 * 产生唯一主键值
	 */
	public TapeEntity createId(){
		if(StringUtil.isBlank(id)){
			this.id = UUIDUtils.getUuid();
		}
		return this;
	}
	
	public TapeEntity id(String id) {
		setId(id);
		return this;
	}

	public TapeEntity tenantId(String tenantId) {
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
	
	public void setType(Integer type){  
        this.type = type;  
    }  
    
	@FieldDisplay("呼叫类型, 1呼入, 2呼出")
    public Integer getType(){
        return this.type;  
    }
	
	public void setSatisfactionType(Integer satisfactionType){  
        this.satisfactionType = satisfactionType;  
    }  
    
	@FieldDisplay("满意类型, 1满意, 2不满意")
    public Integer getSatisfactionType(){
        return this.satisfactionType;  
    }
	
	public void setContractNo(String contractNo){  
        this.contractNo = contractNo;  
    }  
    
	@FieldDisplay("")
    public String getContractNo(){
        return this.contractNo;  
    }
	
	public void setCustName(String custName){  
        this.custName = custName;  
    }  
    
	@FieldDisplay("客户姓名")
    public String getCustName(){
        return this.custName;  
    }
	
	public void setAgentId(String agentId){  
        this.agentId = agentId;  
    }  
    
	@FieldDisplay("坐席工号")
    public String getAgentId(){
        return this.agentId;  
    }
	
	public void setCustPhone(String custPhone){  
        this.custPhone = custPhone;  
    }  
    
	@FieldDisplay("客户电话")
    public String getCustPhone(){
        return this.custPhone;  
    }
	
	public void setAgentPhone(String agentPhone){  
        this.agentPhone = agentPhone;  
    }  
    
	@FieldDisplay("坐席分机号")
    public String getAgentPhone(){
        return this.agentPhone;  
    }
	
	public void setAgentName(String agentName){  
        this.agentName = agentName;  
    }  
    
	@FieldDisplay("坐席姓名")
    public String getAgentName(){
        return this.agentName;  
    }
	
	public void setAgentNo(String agentNo){  
        this.agentNo = agentNo;  
    }  
    
	@FieldDisplay("思科工号")
    public String getAgentNo(){
        return this.agentNo;  
    }
	
	public void setRecordTime(Integer recordTime){  
        this.recordTime = recordTime;  
    }  
    
	@FieldDisplay("录音时长")
    public Integer getRecordTime(){
        return this.recordTime;  
    }
	
	public void setStartTime(String startTime){  
        this.startTime = startTime;  
    }  
    
	@FieldDisplay("录音开始时间")
    public String getStartTime(){
        return this.startTime;  
    }
	
	public void setEndTime(String endTime){  
        this.endTime = endTime;  
    }  
    
	public String getSign() {
		return sign;
	}

	public boolean getIvrIsLoad() {
		return ivrIsLoad;
	}

	public void setIvrIsLoad(boolean ivrIsLoad) {
		this.ivrIsLoad = ivrIsLoad;
	}

	public boolean getSummaryIsLoad() {
		return summaryIsLoad;
	}

	public void setSummaryIsLoad(boolean summaryIsLoad) {
		this.summaryIsLoad = summaryIsLoad;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@FieldDisplay("录音结束时间")
    public String getEndTime(){
        return this.endTime;  
    }
	
	public String getLocalAddress() {
		return localAddress;
	}

	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	public void setDirection(String direction){  
        this.direction = direction;  
    }  
    
	@FieldDisplay("呼叫方式")
    public String getDirection(){
        return this.direction;  
    }
	
	public void setPlatform(String platform){  
        this.platform = platform;  
    }  
    
	@FieldDisplay("录音平台")
    public String getPlatform(){
        return this.platform;  
    }
	
	public void setServiceNo(String serviceNo){  
        this.serviceNo = serviceNo;  
    }  
    
	@FieldDisplay("服务号码")
    public String getServiceNo(){
        return this.serviceNo;  
    }
	
	public void setMediaType(String mediaType){  
        this.mediaType = mediaType;  
    }  
    
	@FieldDisplay("媒体类型")
    public String getMediaType(){
        return this.mediaType;  
    }
	
	public void setAddress(String address){  
        this.address = address;  
    }  
    
	@FieldDisplay("录音地址")
    public String getAddress(){
        return this.address;  
    }
	
	public void setLocalpath(String localpath){  
        this.localpath = localpath;  
    }  
    
	@FieldDisplay("")
    public String getLocalpath(){
        return this.localpath;  
    }
	
	public void setLocalfile(String localfile){  
        this.localfile = localfile;  
    }  
    
	@FieldDisplay("")
    public String getLocalfile(){
        return this.localfile;  
    }
	
	public void setRecserverip(String recserverip){  
        this.recserverip = recserverip;  
    }  
    
	@FieldDisplay("")
    public String getRecserverip(){
        return this.recserverip;  
    }

	public String getBusiGroupCode() {
		return busiGroupCode;
	}

	public void setBusiGroupCode(String busiGroupCode) {
		this.busiGroupCode = busiGroupCode;
	}

	public Integer getHangupType() {
		return hangupType;
	}

	public void setHangupType(Integer hangupType) {
		this.hangupType = hangupType;
	}

	public String getSyncId() {
		return syncId;
	}

	public void setSyncId(String syncId) {
		this.syncId = syncId;
	}

	public IvrInfoEntity getIvrInfo() {
		return ivrInfo;
	}

	public void setIvrInfo(IvrInfoEntity ivrInfo) {
		this.ivrInfo = ivrInfo;
	}

	public TapeSummaryEntity getSummary() {
		return summary;
	}

	public void setSummary(TapeSummaryEntity summary) {
		this.summary = summary;
	}

}