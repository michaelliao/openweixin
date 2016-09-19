package com.itranswarp.wxapi.message;

import com.itranswarp.wxapi.event.AbstractEvent;
import com.itranswarp.wxapi.event.ScanEvent;
import com.itranswarp.wxapi.event.SubscribeEvent;
import com.itranswarp.wxapi.event.UnsubscribeEvent;
import com.itranswarp.wxapi.exception.WeixinMessageException;

public class Message {

	static final String TYPE_TEXT = "text";

	static final String TYPE_IMAGE = "image";

	static final String TYPE_VOICE = "voice";

	static final String TYPE_VIDEO = "video";

	static final String TYPE_SHORT_VIDEO = "shortvideo";

	static final String TYPE_LOCATION = "location";

	static final String TYPE_LINK = "link";

	static final String TYPE_EVENT = "event";

	static final String EVENT_SUBSCRIBE = "subscribe";

	public String ToUserName;

	public String FromUserName;

	public long CreateTime;

	public String MsgType;

	public long MsgId;

	// for text message:
	public String Content;

	// for image message:
	public String PicUrl;

	// for image / voice / video / short video message:
	public String MediaId;

	// for voice message:
	public String Format;

	public String Recognition;

	// for video / short video message:
	public String ThumbMediaId;

	// for location message:
	public String Label;

	public double Location_X;

	public double Location_Y;

	public long Scale;

	// for link message:
	public String Url;

	public String Title;

	public String Description;

	// for event:
	public String Event;

	public String EventKey;

	public String Ticket;

	public double Latitude;

	public double Longitude;

	public double Precision;

	public boolean isTextMessage() {
		return TYPE_TEXT.equals(this.MsgType);
	}

	public boolean isImageMessage() {
		return TYPE_IMAGE.equals(this.MsgType);
	}

	public boolean isVoiceMessage() {
		return TYPE_VOICE.equals(this.MsgType);
	}

	public boolean isVideoMessage() {
		return TYPE_VIDEO.equals(this.MsgType);
	}

	public boolean isShortVideoMessage() {
		return TYPE_SHORT_VIDEO.equals(this.MsgType);
	}

	public boolean isLocationMessage() {
		return TYPE_LOCATION.equals(this.MsgType);
	}

	public boolean isLinkMessage() {
		return TYPE_LINK.equals(this.MsgType);
	}

	public boolean isEvent() {
		return TYPE_EVENT.equals(this.MsgType);
	}

	/**
	 * 把消息转换为相应的事件类型
	 * 
	 * @return AbstractEvent的子类
	 */
	public AbstractEvent asEvent() {
		if (isEvent()) {
			switch (this.Event) {
			case AbstractEvent.EVENT_SUBSCRIBE:
				return new SubscribeEvent(CreateTime, FromUserName, ToUserName, Event, EventKey, Ticket);
			case AbstractEvent.EVENT_UNSUBSCRIBE:
				return new UnsubscribeEvent(CreateTime, FromUserName, ToUserName, Event, EventKey, Ticket);
			case AbstractEvent.EVENT_SCAN:
				return new ScanEvent(CreateTime, FromUserName, ToUserName, Event, EventKey, Ticket);
			default:
				throw new WeixinMessageException("Cannot handle new type of event from " + this);
			}
		}
		throw new WeixinMessageException("Cannot cast to event from " + this);
	}

	/**
	 * 把消息转换为文本消息
	 * 
	 * @return ReceivedTextMessage
	 */
	public ReceivedTextMessage asTextMessage() {
		if (isTextMessage()) {
			return new ReceivedTextMessage(CreateTime, FromUserName, ToUserName, MsgId, Content);
		}
		throw new WeixinMessageException("Cannot cast to text message from " + this);
	}

	/**
	 * 把消息转换为图片消息
	 * 
	 * @return ReceivedImageMessage
	 */
	public ReceivedImageMessage asImageMessage() {
		if (isImageMessage()) {
			return new ReceivedImageMessage(CreateTime, FromUserName, ToUserName, MsgId, PicUrl, MediaId);
		}
		throw new WeixinMessageException("Cannot cast to image message from " + this);
	}

	/**
	 * 把消息转换为语音消息
	 * 
	 * @return ReceivedVoiceMessage
	 */
	public ReceivedVoiceMessage asVoiceMessage() {
		if (isVoiceMessage()) {
			return new ReceivedVoiceMessage(CreateTime, FromUserName, ToUserName, MsgId, MediaId, Format, Recognition);
		}
		throw new WeixinMessageException("Cannot cast to voice message from " + this);
	}

	/**
	 * 把消息转换为视频消息
	 * 
	 * @return ReceivedVideoMessage
	 */
	public ReceivedVideoMessage asVideoMessage() {
		if (isVideoMessage()) {
			return new ReceivedVideoMessage(CreateTime, FromUserName, ToUserName, MsgId, MediaId, ThumbMediaId);
		}
		throw new WeixinMessageException("Cannot cast to video message from " + this);
	}

	/**
	 * 把消息转换为小视频消息
	 * 
	 * @return ReceivedShortVideoMessage
	 */
	public ReceivedShortVideoMessage asShortVideoMessage() {
		if (isShortVideoMessage()) {
			return new ReceivedShortVideoMessage(CreateTime, FromUserName, ToUserName, MsgId, MediaId, ThumbMediaId);
		}
		throw new WeixinMessageException("Cannot cast to short video message from " + this);
	}

	/**
	 * 把消息转换为位置消息
	 * 
	 * @return ReceivedLocationMessage
	 */
	public ReceivedLocationMessage asLocationMessage() {
		if (isLocationMessage()) {
			return new ReceivedLocationMessage(CreateTime, FromUserName, ToUserName, MsgId, Label, Location_X,
					Location_Y, Scale);
		}
		throw new WeixinMessageException("Cannot cast to location message from " + this);
	}

	/**
	 * 把消息转换为链接消息
	 * 
	 * @return ReceivedLinkMessage
	 */
	public ReceivedLinkMessage asLinkMessage() {
		if (isLinkMessage()) {
			return new ReceivedLinkMessage(CreateTime, FromUserName, ToUserName, MsgId, Url, Title, Description);
		}
		throw new WeixinMessageException("Cannot cast to link message from " + this);
	}

	/**
	 * 创建一个MessageBuilder对象
	 * 
	 * @param FromUserName
	 *            发送方
	 * @param ToUserName
	 *            接收方
	 * @return MessageBuilder
	 */
	public static MessageBuilder buildMessage(String FromUserName, String ToUserName) {
		return new MessageBuilder(FromUserName, ToUserName);
	}

	public String toString() {
		return "<Message: MsgType=" + this.MsgType + ", MsgId=" + this.MsgId + ">";
	}

	public static class MessageBuilder {
		final String FromUserName;
		final String ToUserName;

		MessageBuilder(String FromUserName, String ToUserName) {
			this.FromUserName = FromUserName;
			this.ToUserName = ToUserName;
		}

		public RepliedTextMessage toTextMessage(String Content) {
			return new RepliedTextMessage(FromUserName, ToUserName, Content);
		}

		public RepliedImageMessage toImageMessage(String MediaId) {
			return new RepliedImageMessage(FromUserName, ToUserName, MediaId);
		}

		public RepliedVoiceMessage toVoiceMessage(String MediaId) {
			return new RepliedVoiceMessage(FromUserName, ToUserName, MediaId);
		}

		public RepliedVideoMessage toVideoMessage(String MediaId, String Title, String Description) {
			return new RepliedVideoMessage(FromUserName, ToUserName, MediaId, Title, Description);
		}

	}
}
