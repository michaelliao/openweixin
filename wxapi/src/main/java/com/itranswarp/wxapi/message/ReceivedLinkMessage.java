package com.itranswarp.wxapi.message;

public class ReceivedLinkMessage extends AbstractReceivedMessage {

	public final String MsgType = Message.TYPE_LINK;

	public final String Url;

	public final String Title;

	public final String Description;

	ReceivedLinkMessage(long CreateTime, String FromUserName, String ToUserName, long MsgId, String Url, String Title,
			String Description) {
		super(CreateTime, FromUserName, ToUserName, MsgId);
		this.Url = Url;
		this.Title = Title;
		this.Description = Description;
	}

}
