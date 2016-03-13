package com.itranswarp.wxapi.message;

public class ReceivedLocationMessage extends AbstractReceivedMessage {

	public final String MsgType = Message.TYPE_LOCATION;

	public final String Label;

	public final double Location_X;

	public final double Location_Y;

	public final long Scale;

	ReceivedLocationMessage(long CreateTime, String FromUserName, String ToUserName, long MsgId, String Label,
			double Location_X, double Location_Y, long Scale) {
		super(CreateTime, FromUserName, ToUserName, MsgId);
		this.Label = Label;
		this.Location_X = Location_X;
		this.Location_Y = Location_Y;
		this.Scale = Scale;
	}

}
