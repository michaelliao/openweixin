package com.itranswarp.wxapi.qrcode;

public class QRCodeTicket {

	public String action_name = "QR_LIMIT_SCENE";
	public Scene action_info;

	public QRCodeTicket(int scene_id) {
		this.action_info = new Scene(scene_id);
	}

	public static class Scene {
		// weixin only support 1~100000:
		public int scene_id;

		public Scene(int scene_id) {
			this.scene_id = scene_id;
		}

	}
}
