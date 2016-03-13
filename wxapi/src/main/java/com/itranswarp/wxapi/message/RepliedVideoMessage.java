package com.itranswarp.wxapi.message;

public class RepliedVideoMessage extends AbstractRepliedMessage {

	public final Video Video;

	public RepliedVideoMessage(String FromUserName, String ToUserName, String MediaId, String Title,
			String Description) {
		super(Message.TYPE_VIDEO, FromUserName, ToUserName);
		this.Video = new Video(MediaId, Title, Description);
	}

	public static class Video {

		public final String MediaId;
		public final String Title;
		public final String Description;

		public Video(String MediaId, String Title, String Description) {
			this.MediaId = MediaId;
			this.Title = Title;
			this.Description = Description;
		}
	}
}
