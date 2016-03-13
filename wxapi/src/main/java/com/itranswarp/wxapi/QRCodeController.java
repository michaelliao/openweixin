package com.itranswarp.wxapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itranswarp.wxapi.qrcode.QRCode;
import com.itranswarp.wxapi.qrcode.QRCodeTicket;
import com.itranswarp.wxapi.qrcode.TempQRCodeTicket;
import com.itranswarp.wxapi.util.HttpUtil;

@RestController
public class QRCodeController extends AbstractController {

	@Autowired
	WeixinClient client;

	@RequestMapping(value = "/qrcode/createTemp", method = RequestMethod.GET)
	public QRCode tempQR(@RequestParam(value = "accessToken") String accessToken,
			@RequestParam(value = "expires", defaultValue = "604800") long expires,
			@RequestParam(value = "sceneId") int sceneId) {
		TempQRCodeTicket ticket = new TempQRCodeTicket(sceneId, 3600);

		QRCode code = client.postJson(QRCode.class, "/qrcode/create?access_token=" + HttpUtil.urlEncode(accessToken),
				ticket);
		return code;
	}

	@RequestMapping(value = "/qrcode/create", method = RequestMethod.GET)
	public QRCode permQR(@RequestParam(value = "accessToken") String accessToken,
			@RequestParam(value = "sceneId") int sceneId) {
		QRCodeTicket ticket = new QRCodeTicket(sceneId);
		QRCode code = client.postJson(QRCode.class, "/qrcode/create?access_token=" + HttpUtil.urlEncode(accessToken),
				ticket);
		return code;
	}

}
