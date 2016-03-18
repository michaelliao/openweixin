package com.itranswarp.wxapi.menu;

public class ScanButton extends AbstractButton {

	public final String type = "scancode_waitmsg";

	public String key;

	public ScanButton() {
	}

	public ScanButton(String name, String key) {
		this.name = name;
		this.key = key;
	}
}
