package com.itranswarp.wxapi.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.itranswarp.wxapi.WeixinClient;

@Component
public class Scheduler {

	@Autowired
	WeixinClient weixinClient;

	// refresh access token by timer:
	@Scheduled(initialDelay = WeixinClient.REFRESH_TOKEN_IN_MILLIS, fixedRate = WeixinClient.REFRESH_TOKEN_IN_MILLIS)
	public void refreshAccessToken() {
		weixinClient.refreshAccessToken();
	}
}
