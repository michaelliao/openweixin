package com.itranswarp.wxapi.exception;

/**
 * Base exception for all Weixin-related exception.
 * 
 * @author Michael Liao
 */
public class WeixinException extends RuntimeException {

	int errCode = (-1);

	public int getErrCode() {
		return errCode;
	}

	public WeixinException() {
	}

	public WeixinException(String message) {
		super(message);
	}

	public WeixinException(int errCode, String message) {
		super(message);
		this.errCode = errCode;
	}

	public WeixinException(Throwable cause) {
		super(cause);
	}

	public WeixinException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeixinException(int errCode, String message, Throwable cause) {
		super(message, cause);
		this.errCode = errCode;
	}
}
