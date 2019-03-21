package com.kim.common.response;

import com.kim.common.base.BaseObject;
import com.kim.common.exception.BaseException;

/**
 * Created by bo.liu01 on 2017/10/30.
 */
public class ResultResponse extends BaseObject {

    private static final long serialVersionUID = 4539343756018673380L;

    protected int code = MsgCode.FAIL.getCode();
    protected String msg;

    public ResultResponse() {
    }

    public ResultResponse(BaseException e) {
        super();
        setCode(e.getCode());
        setMsg(e.getMsg());
    }

    public ResultResponse(MsgCode msgResponse) {
        this.code = msgResponse.getCode();
        this.msg = msgResponse.getMsg();
    }

    public ResultResponse code(int code) {
        setCode(code);
        return this;
    }

    public ResultResponse msg(String msg) {
        setMsg(msg);
        return this;
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

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
