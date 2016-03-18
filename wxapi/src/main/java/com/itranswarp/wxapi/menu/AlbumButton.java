package com.itranswarp.wxapi.menu;

public class AlbumButton extends AbstractButton {

	public final String type = "pic_weixin";

	public String key;

	public AlbumButton() {
	}

	public AlbumButton(String name, String key) {
		this.name = name;
		this.key = key;
	}
}
