package com.kim.common.response;

/**
 * Created by bo.liu01 on 2017/9/29.
 */
public enum MsgCode {

	//失败
    FAIL(10001, "失败", false),
    INVALID_PARAMETER(1002, "参数有误", false),
    
    //系统问题
    SYSTEM_ERROR(1010, "系统繁忙", false),
    SYSTEM_EXCEPTION(1011, "系统异常", false),
    SERVICE_UNVALIABLE(1012, "服务不可用", false),
    SERVICE_TIMEOUT(1013, "服务请求超时", false),

    //登录授权问题
    UNAUTHORIZED(4001, "未授权", false),
    INCORRECT_USER_PSWW(4002, "用户验证失败", false),
    UN_LOGIN_IN(4003, "用户未登录", false),
    UNKNOWN_USER(4004, "用户信息不存在", false),
    
    //成功
    SUCCESS(1000, "成功", MsgCodeType.SUCCESS, true);

    private int code;
    private String msg;
    private boolean rel;
    private MsgCodeType type;

    MsgCode(int code, String msg, boolean rel) {
        this.code = code;
        this.msg = msg;
        this.rel = rel;
        this.type = MsgCodeType.FAIL;
    }
    
    MsgCode(int code, String msg, MsgCodeType type, boolean rel) {
    	this.code = code;
    	this.msg = msg;
    	this.rel = rel;
    	this.type = type;
    }
    
    public static MsgCode parseMsgCode(int code){
    	MsgCode[] values = MsgCode.values();
    	for (MsgCode msgCode : values) {
			if(msgCode.getCode() == code){
				return msgCode;
			}
		}
    	return null;
    }

    public boolean isRel() {
        return rel;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public MsgCodeType getType() {
		return type;
	}

	public enum MsgCodeType{
    	SUCCESS, FAIL
    }
    
}
