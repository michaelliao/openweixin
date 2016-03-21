package com.itranswarp.robot;

public class RobotException extends RuntimeException {

	private int code = 0;

	public int getCode() {
		return code;
	}

	public RobotException() {
		super();
	}

	public RobotException(int code) {
		super();
		this.code = code;
	}

	public RobotException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public RobotException(int code, String message) {
		super(message);
		this.code = code;
	}

	public RobotException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public RobotException(String message, Throwable cause) {
		super(message, cause);
	}

	public RobotException(String message) {
		super(message);
	}

	public RobotException(Throwable cause) {
		super(cause);
	}

}
