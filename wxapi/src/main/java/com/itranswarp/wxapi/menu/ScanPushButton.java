package com.itranswarp.wxapi.menu;

public class ScanPushButton extends AbstractButton {

	public final String type = "scancode_push";

	public String key;

	public ScanPushButton() {
	}

	public ScanPushButton(String name, String key) {
		this.name = name;
		this.key = key;
	}
}
