package com.itranswarp.wxapi.exception;

public class WeixinSecurityException extends WeixinException {

	public WeixinSecurityException() {
	}

	public WeixinSecurityException(String message) {
		super(message);
	}

	public WeixinSecurityException(int errCode, String message) {
		super(errCode, message);
	}

	public WeixinSecurityException(Throwable cause) {
		super(cause);
	}

	public WeixinSecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeixinSecurityException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}

}
