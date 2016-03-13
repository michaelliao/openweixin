package com.itranswarp.wxapi.message;

abstract class AbstractRepliedMessage {

	public final String MsgType;
	public final String ToUserName;
	public final String FromUserName;
	public final long CreateTime;

	AbstractRepliedMessage(String MsgType, String FromUserName, String ToUserName) {
		this.MsgType = MsgType;
		this.FromUserName = FromUserName;
		this.ToUserName = ToUserName;
		CreateTime = System.currentTimeMillis() / 1000;
	}
}
