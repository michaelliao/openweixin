package com.itranswarp.wxapi.message;

public class ReceivedTextMessage extends AbstractReceivedMessage {

	public final String MsgType = Message.TYPE_TEXT;

	public final String Content;

	ReceivedTextMessage(long CreateTime, String FromUserName, String ToUserName, long MsgId, String Content) {
		super(CreateTime, FromUserName, ToUserName, MsgId);
		this.Content = Content;
	}

}
