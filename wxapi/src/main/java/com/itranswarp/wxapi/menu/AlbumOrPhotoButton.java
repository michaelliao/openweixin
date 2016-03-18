package com.itranswarp.wxapi.menu;

public class AlbumOrPhotoButton extends AbstractButton {

	public final String type = "pic_photo_or_album";

	public String key;

	public AlbumOrPhotoButton() {
	}

	public AlbumOrPhotoButton(String name, String key) {
		this.name = name;
		this.key = key;
	}
}
