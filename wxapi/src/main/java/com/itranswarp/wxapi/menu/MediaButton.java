package com.itranswarp.wxapi.menu;

public class MediaButton extends AbstractButton {

	public final String type = "media_id";

	public String media_id;

	public MediaButton() {
	}

	public MediaButton(String name, String media_id) {
		this.name = name;
		this.media_id = media_id;
	}
}
