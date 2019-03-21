package com.kim.common.exception;

import com.kim.common.response.MsgCode;

/**
 * @author bo.liu01
 *
 */
public class LoginException extends BaseException{
	private static final long serialVersionUID = 1L;

	public LoginException() {
        super();
    }

    public LoginException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public LoginException(MsgCode msgCode) {
        super(msgCode.getMsg());
        this.code = msgCode.getCode();
        this.msg = msgCode.getMsg();
    }

    public LoginException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public LoginException(Exception e) {
        super(e);
    }

    public LoginException(String message, Exception e) {
        super(message, e);
    }
}
