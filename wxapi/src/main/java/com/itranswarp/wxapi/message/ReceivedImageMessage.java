package com.itranswarp.wxapi.message;

public class ReceivedImageMessage extends AbstractReceivedMessage {

	public final String MsgType = Message.TYPE_IMAGE;

	public final String PicUrl;

	public final String MediaId;

	ReceivedImageMessage(long CreateTime, String FromUserName, String ToUserName, long MsgId, String PicUrl,
			String MediaId) {
		super(CreateTime, FromUserName, ToUserName, MsgId);
		this.PicUrl = PicUrl;
		this.MediaId = MediaId;
	}

}
