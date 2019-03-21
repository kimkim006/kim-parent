package com.kim.log.interceptor;

import com.kim.log.entity.OperateLogsEntity;
import com.kim.log.operatelog.OperModule;
import com.kim.log.operatelog.Operation;
import com.kim.log.operatelog.OperationType;
import com.kim.common.util.StringUtil;
import org.slf4j.event.Level;

import java.util.List;

public class ParamSet {

    private List<ParameterType> parameterTypeList;
    private String queryParam;
    private String bodyParam;

    private String objStr;
    private String url;
    private String module;
    private String oper;
    private OperationType operType;
    private Level level;
    private boolean printResData = false;
    private boolean autoPrint = true;

    private boolean logOperation;
    private String beanAndMethod;
    private Class<?> beanType;
    private String methodName;
    private String[] conKey;
    private String[] priKey;
    private String extraContent;
    private boolean injectCurntUser;
    private boolean injectCurntTenant;
    private boolean injectCurntDate;
    private boolean elapsedTime;
    private boolean printIp;
    private long startTime;//毫秒
    private long endTime;//毫秒

    private String saveLable;

    private OperateLogsEntity operateLog;
    private boolean isSubmit = false;

    private Object[] methodParams;//controller方法的参数



    public String getBodyParam() {
        return bodyParam;
    }

    public void setBodyParam(String bodyParam) {
        this.bodyParam = bodyParam;
    }

    public List<ParameterType> getParameterTypeList() {
        return parameterTypeList;
    }

    public void setParameterTypeList(List<ParameterType> parameterTypeList) {
        this.parameterTypeList = parameterTypeList;
    }

    public String getUrl() {
        return url;
    }

    public Level getLevel() {
        return level;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getObjStr() {
        return objStr;
    }

    public void setObjStr(String objStr) {
        this.objStr = objStr;
    }

    public boolean isPrintResData() {
        return printResData;
    }

    public void setPrintResData(boolean printResData) {
        this.printResData = printResData;
    }

    public boolean isAutoPrint() {
        return autoPrint;
    }

    public void setAutoPrint(boolean autoPrint) {
        this.autoPrint = autoPrint;
    }

    public String getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(String queryParam) {
        this.queryParam = queryParam;
    }

    public boolean isLogOperation() {
        return logOperation;
    }

    public void setLogOperation(boolean logOperation) {
        this.logOperation = logOperation;
    }

    public String getBeanAndMethod() {
        return beanAndMethod;
    }

    public void setBeanAndMethod(String beanAndMethod) {
        this.beanAndMethod = beanAndMethod;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public void setBeanType(Class<?> beanType) {
        this.beanType = beanType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String[] getConKey() {
        return conKey;
    }

    public void setConKey(String[] conKey) {
        this.conKey = conKey;
    }

    public String getModule() {
        return StringUtil.isBlank(module) ? OperModule.UNDEFINED : module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOper() {
        return StringUtil.isBlank(oper) ? Operation.UNDEFINED : oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public OperationType getOperType() {
        return this.operType;
    }

    public void setOperType(OperationType operType) {
        this.operType = operType;
    }

    public String getSaveLable() {
        return saveLable;
    }

    public void setSaveLable(String saveLable) {
        this.saveLable = saveLable;
    }

    public boolean isInjectCurntUser() {
        return injectCurntUser;
    }

    public void setInjectCurntUser(boolean injectCurntUser) {
        this.injectCurntUser = injectCurntUser;
    }

    public boolean isInjectCurntDate() {
        return injectCurntDate;
    }

    public void setInjectCurntDate(boolean injectCurntDate) {
        this.injectCurntDate = injectCurntDate;
    }

    public OperateLogsEntity getOperateLog() {
        return operateLog;
    }

    public void setOperateLog(OperateLogsEntity operateLog) {
        this.operateLog = operateLog;
    }

    public String[] getPriKey() {
        return priKey;
    }

    public void setPriKey(String[] priKey) {
        this.priKey = priKey;
    }

    public String getExtraContent() {
        return extraContent;
    }

    public void setExtraContent(String extraContent) {
        this.extraContent = extraContent;
    }

    public boolean isSubmit() {
        return isSubmit;
    }

    public void setSubmit(boolean isSubmit) {
        this.isSubmit = isSubmit;
    }

    public boolean getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(boolean elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(Object[] methodParams) {
        this.methodParams = methodParams;
    }

    public boolean isPrintIp() {
        return printIp;
    }

    public void setPrintIp(boolean printIp) {
        this.printIp = printIp;
    }

	public boolean isInjectCurntTenant() {
		return injectCurntTenant;
	}

	public void setInjectCurntTenant(boolean injectCurntTenant) {
		this.injectCurntTenant = injectCurntTenant;
	}

}
