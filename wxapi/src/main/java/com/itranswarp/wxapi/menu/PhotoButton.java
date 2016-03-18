package com.itranswarp.wxapi.menu;

public class PhotoButton extends AbstractButton {

	public final String type = "pic_sysphoto";

	public String key;

	public PhotoButton() {
	}

	public PhotoButton(String name, String key) {
		this.name = name;
		this.key = key;
	}
}
