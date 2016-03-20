package com.itranswarp.wxapi.qrcode;

public class TempQRCodeTicket extends QRCodeTicket {

	public int expire_seconds;

	public TempQRCodeTicket(int scene_id, int expire_seconds) {
		super(scene_id);
		super.action_name = "QR_SCENE";
		this.expire_seconds = expire_seconds;
	}

}
