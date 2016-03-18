package com.itranswarp.wxapi.menu;

public class ViewButton extends AbstractButton {

	public final String type = "view";

	public String url;

	public ViewButton() {
	}

	public ViewButton(String name, String url) {
		this.name = name;
		this.url = url;
	}

}
