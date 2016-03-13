package com.itranswarp.wxapi.event;

public class ScanEvent extends AbstractEvent {

	public final String EventKey;
	public final String Ticket;

	public ScanEvent(long CreateTime, String FromUserName, String ToUserName, String Event, String EventKey,
			String Ticket) {
		super(CreateTime, FromUserName, ToUserName, Event);
		this.EventKey = EventKey;
		this.Ticket = Ticket;
	}

}
