package com.kim.common.exception;

import com.kim.common.response.MsgCode;

/**
 * @author bo.liu01
 *
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	protected int code = MsgCode.FAIL.getCode();
    protected String msg = MsgCode.FAIL.getMsg();

    public BaseException() {
    }

    public BaseException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public BaseException setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
