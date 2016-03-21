package com.itranswarp.wxapi.token.cache;

public interface AccessTokenCache {

	void setAccessToken(String accessToken, int expiresInSeconds);

	String getAccessToken();

	boolean shouldRefresh();

}
