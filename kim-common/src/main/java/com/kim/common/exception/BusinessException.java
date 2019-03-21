package com.kim.common.exception;

import com.kim.common.response.MsgCode;

/**
 * @author bo.liu01
 *
 */
public class BusinessException extends BaseException{
	private static final long serialVersionUID = 1L;

	public BusinessException() {
        super();
    }

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BusinessException(MsgCode msgCode) {
        super(msgCode.getMsg());
        this.code = msgCode.getCode();
        this.msg = msgCode.getMsg();
    }

    public BusinessException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BusinessException(Exception e) {
        super(e);
    }

    public BusinessException(String message, Exception e) {
        super(message, e);
    }
}
