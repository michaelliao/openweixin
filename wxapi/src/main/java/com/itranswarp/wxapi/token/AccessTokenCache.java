package com.itranswarp.wxapi.token;

public interface AccessTokenCache {

	void setAccessToken(String accessToken, int expiresInSeconds);

	String getAccessToken();

	boolean shouldRefresh();

}
