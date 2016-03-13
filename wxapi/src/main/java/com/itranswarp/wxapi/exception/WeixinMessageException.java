package com.itranswarp.wxapi.exception;

public class WeixinMessageException extends WeixinException {

	public WeixinMessageException() {
	}

	public WeixinMessageException(String message) {
		super(message);
	}

	public WeixinMessageException(int errCode, String message) {
		super(errCode, message);
	}

	public WeixinMessageException(Throwable cause) {
		super(cause);
	}

	public WeixinMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeixinMessageException(int errCode, String message, Throwable cause) {
		super(errCode, message, cause);
	}

}
