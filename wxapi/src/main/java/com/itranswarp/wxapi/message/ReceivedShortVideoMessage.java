package com.itranswarp.wxapi.message;

public class ReceivedShortVideoMessage extends AbstractReceivedMessage {

	public final String MsgType = Message.TYPE_SHORT_VIDEO;

	public final String MediaId;

	public final String ThumbMediaId;

	ReceivedShortVideoMessage(long CreateTime, String FromUserName, String ToUserName, long MsgId, String MediaId,
			String ThumbMediaId) {
		super(CreateTime, FromUserName, ToUserName, MsgId);
		this.MediaId = MediaId;
		this.ThumbMediaId = ThumbMediaId;
	}

}
