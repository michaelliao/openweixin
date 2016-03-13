package com.itranswarp.wxapi.message;

public class ReceivedVoiceMessage extends AbstractReceivedMessage {

	public final String MsgType = Message.TYPE_VOICE;

	public final String MediaId;

	public final String Format;

	public final String Recognition;

	public ReceivedVoiceMessage(long CreateTime, String FromUserName, String ToUserName, long MsgId, String MediaId,
			String Format, String Recognition) {
		super(CreateTime, FromUserName, ToUserName, MsgId);
		this.MediaId = MediaId;
		this.Format = Format;
		this.Recognition = Recognition;
	}

}
