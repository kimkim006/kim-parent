package com.kim.impexp.excel.entity;

import com.kim.common.base.BaseObject;

/**
 * 导入数据后的结果
 * @author bo.liu01
 *
 */
public class ImportResult extends BaseObject{

	private static final long serialVersionUID = 1L;
	/** 导入成功的条数 */
	private int success;
	/** 导入失败的条数 */
	private int fail;
	/** 导入结果下载文件位置 */
	private String path;
	/** 下载文件签名 */
	private String sign;
	
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public int getFail() {
		return fail;
	}
	public void setFail(int fail) {
		this.fail = fail;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	

}
