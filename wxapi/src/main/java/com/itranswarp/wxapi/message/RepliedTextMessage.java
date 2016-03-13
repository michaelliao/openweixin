package com.itranswarp.wxapi.message;

public class RepliedTextMessage extends AbstractRepliedMessage {

	public final String Content;

	public RepliedTextMessage(String FromUserName, String ToUserName, String Content) {
		super(Message.TYPE_TEXT, FromUserName, ToUserName);
		this.Content = Content;
	}

}
