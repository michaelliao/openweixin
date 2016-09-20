package com.itranswarp.wxapi.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itranswarp.wxapi.WeixinClient;
import com.itranswarp.wxapi.qrcode.QRCode;
import com.itranswarp.wxapi.qrcode.ShortUrl;

@RestController
public class QRCodeController extends AbstractController {

	@Autowired
	WeixinClient client;

	@RequestMapping(value = "/qrcode/createTemp", method = RequestMethod.GET)
	public QRCode createQR(@RequestParam(value = "sceneId") int sceneId,
			@RequestParam(value = "expires", defaultValue = "604800") int expires) {
		return client.createQRCode(sceneId, expires);
	}

	@RequestMapping(value = "/shortUrl", method = RequestMethod.GET)
	public ShortUrl shortUrl(@RequestParam(value = "url") String url) {
		return client.shortUrl(url);
	}
}
