package com.itranswarp.wxapi.message;

public class RepliedImageMessage extends AbstractRepliedMessage {

	public final Image Image;

	public RepliedImageMessage(String FromUserName, String ToUserName, String MediaId) {
		super(Message.TYPE_IMAGE, FromUserName, ToUserName);
		this.Image = new Image(MediaId);
	}

	public static class Image {

		public final String MediaId;

		public Image(String mediaId) {
			MediaId = mediaId;
		}
	}
}
