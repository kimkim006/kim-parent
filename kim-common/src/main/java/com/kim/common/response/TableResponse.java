package com.kim.common.response;

import com.kim.common.page.PageRes;
import com.kim.common.page.PageRet;

/**
 * Created by bo.liu01 on 2017/10/30.
 */
public class TableResponse extends MsgResponse {

	private static final long serialVersionUID = 845671878201173134L;
	
	protected PageRet<?> data;

    public TableResponse() {
        super();
        this.data = new PageRet<>();
    }

    public TableResponse(PageRet<?> data) {
        data(data);
    }

    public TableResponse(PageRes<?> pageRes) {
    	this(PageRet.convert(pageRes));
    }

    public TableResponse data(PageRet<?> pageRet) {
        if(pageRet != null){
            data = pageRet;
            this.rel(true);
        }else{
        	this.rel(false);
        }
        return this;
    }
    
    public TableResponse data(PageRes<?> pageRes) {
    	this.data(PageRet.convert(pageRes));
    	return this;
    }

    public PageRet<?> getData() {
        return data;
    }

    public void setData(PageRet<?> data) {
        this.data = data;
    }
}
