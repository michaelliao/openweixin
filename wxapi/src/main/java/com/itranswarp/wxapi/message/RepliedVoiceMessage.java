package com.itranswarp.wxapi.message;

public class RepliedVoiceMessage extends AbstractRepliedMessage {

	public final Voice Voice;

	public RepliedVoiceMessage(String FromUserName, String ToUserName, String MediaId) {
		super(Message.TYPE_VOICE, FromUserName, ToUserName);
		this.Voice = new Voice(MediaId);
	}

	public static class Voice {

		public final String MediaId;

		public Voice(String mediaId) {
			MediaId = mediaId;
		}
	}
}
