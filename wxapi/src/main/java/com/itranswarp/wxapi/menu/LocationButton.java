package com.itranswarp.wxapi.menu;

public class LocationButton extends AbstractButton {

	public final String type = "location_select";

	public String key;

	public LocationButton() {
	}

	public LocationButton(String name, String key) {
		this.name = name;
		this.key = key;
	}
}
