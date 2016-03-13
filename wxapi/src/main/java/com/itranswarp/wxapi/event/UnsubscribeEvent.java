package com.itranswarp.wxapi.event;

public class UnsubscribeEvent extends AbstractEvent {

	public final String EventKey;
	public final String Ticket;

	public UnsubscribeEvent(long CreateTime, String FromUserName, String ToUserName, String Event, String EventKey,
			String Ticket) {
		super(CreateTime, FromUserName, ToUserName, Event);
		this.EventKey = EventKey;
		this.Ticket = Ticket;
	}

}
