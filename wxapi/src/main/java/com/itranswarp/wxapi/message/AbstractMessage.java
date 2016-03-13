package com.itranswarp.wxapi.message;

abstract class AbstractReceivedMessage {

	public final long CreateTime;

	public final String FromUserName;

	public final String ToUserName;

	public final long MsgId;

	AbstractReceivedMessage(long CreateTime, String FromUserName, String ToUserName, long MsgId) {
		this.CreateTime = CreateTime;
		this.FromUserName = FromUserName;
		this.ToUserName = ToUserName;
		this.MsgId = MsgId;
	}

}
