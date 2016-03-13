package com.itranswarp.wxapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itranswarp.wxapi.WeixinClient;
import com.itranswarp.wxapi.qrcode.QRCode;
import com.itranswarp.wxapi.qrcode.QRCodeTicket;
import com.itranswarp.wxapi.qrcode.ShortUrl;
import com.itranswarp.wxapi.qrcode.TempQRCodeTicket;
import com.itranswarp.wxapi.util.HttpUtil;
import com.itranswarp.wxapi.util.MapUtil;

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

	@RequestMapping(value = "/shortUrl", method = RequestMethod.GET)
	public ShortUrl shortUrl(@RequestParam(value = "accessToken") String accessToken,
			@RequestParam(value = "url") String url) {
		ShortUrl su = client.postJson(ShortUrl.class, "/shorturl?access_token=" + HttpUtil.urlEncode(accessToken),
				MapUtil.createMap("action", "long2short", "long_url", url));
		su.long_url = url;
		return su;
	}
}
