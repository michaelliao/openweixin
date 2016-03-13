package com.itranswarp.wxapi.message;

public class ReceivedVideoMessage extends AbstractReceivedMessage {

	public final String MsgType = Message.TYPE_VIDEO;

	public final String MediaId;

	public final String ThumbMediaId;

	ReceivedVideoMessage(long CreateTime, String FromUserName, String ToUserName, long MsgId, String MediaId,
			String ThumbMediaId) {
		super(CreateTime, FromUserName, ToUserName, MsgId);
		this.MediaId = MediaId;
		this.ThumbMediaId = ThumbMediaId;
	}

}
