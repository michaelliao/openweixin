package com.itranswarp.wxapi.event;

public abstract class AbstractEvent {

	public static final String EVENT_SUBSCRIBE = "subscribe";
	public static final String EVENT_UNSUBSCRIBE = "unsubscribe";
	public static final String EVENT_SCAN = "SCAN";

	public final long CreateTime;

	public final String FromUserName;

	public final String ToUserName;

	public final String Event;

	AbstractEvent(long CreateTime, String FromUserName, String ToUserName, String Event) {
		this.CreateTime = CreateTime;
		this.FromUserName = FromUserName;
		this.ToUserName = ToUserName;
		this.Event = Event;
	}
}
