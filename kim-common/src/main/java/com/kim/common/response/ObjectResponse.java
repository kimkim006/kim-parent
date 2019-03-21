package com.kim.common.response;

/**
 * Created by bo.liu01 on 2017/10/30.
 */
public class ObjectResponse extends MsgResponse {

	private static final long serialVersionUID = -1443988810591413219L;
	
	private Object data;

    public ObjectResponse(boolean rel) {
        super();
        rel(rel);
    }

    public ObjectResponse data(Object data) {
        this.data = data;
        return this;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
