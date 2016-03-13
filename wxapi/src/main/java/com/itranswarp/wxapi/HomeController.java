package com.itranswarp.wxapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itranswarp.wxapi.bean.AccessToken;
import com.itranswarp.wxapi.bean.IpList;
import com.itranswarp.wxapi.util.MapUtil;

@RestController
public class HomeController {

	@Value("${weixin.app.id}")
	String appId;

	@Value("${weixin.app.secret}")
	String appSecret;

	@Autowired
	WeixinClient client;

	@RequestMapping(value = "/accessToken", method = RequestMethod.GET, produces = "application/json")
	AccessToken getAccessToken() {
		return client.getJson(AccessToken.class, "/token",
				MapUtil.createMap("appid", appId, "secret", appSecret, "grant_type", "client_credential"));
	}

	@RequestMapping(value = "/ip", method = RequestMethod.GET, produces = "application/json")
	IpList getIpList(@RequestParam(value = "access_token") String access_token) {
		return client.getJson(IpList.class, "/getcallbackip", MapUtil.createMap("access_token", access_token));
	}
}
