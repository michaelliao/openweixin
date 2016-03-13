package com.itranswarp.wxapi.event;

public class SubscribeEvent extends AbstractEvent {

	public final String EventKey;
	public final String Ticket;

	public SubscribeEvent(long CreateTime, String FromUserName, String ToUserName, String Event, String EventKey,
			String Ticket) {
		super(CreateTime, FromUserName, ToUserName, Event);
		this.EventKey = EventKey;
		this.Ticket = Ticket;
	}

}
