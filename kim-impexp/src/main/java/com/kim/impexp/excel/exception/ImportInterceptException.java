package com.kim.impexp.excel.exception;

/**
 * @author bo.liu01
 *
 */
public class ImportInterceptException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ImportInterceptException() {
        super();
    }

    public ImportInterceptException(String msg) {
        super(msg);
    }

    public ImportInterceptException(Exception e) {
        super(e);
    }

    public ImportInterceptException(String message, Exception e) {
        super(message, e);
    }
}
