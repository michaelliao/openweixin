package com.itranswarp.wxapi.menu;

public class ClickButton extends AbstractButton {

	public final String type = "click";

	public String key;

	public ClickButton() {
	}

	public ClickButton(String name, String key) {
		this.name = name;
		this.key = key;
	}
}
