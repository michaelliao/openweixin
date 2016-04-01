package com.itranswarp.wxapi.token.cache;

import com.itranswarp.wxapi.exception.WeixinAccessTokenException;

public class SimpleAccessTokenCache implements AccessTokenCache {

	/**
	 * Should refresh in 30 min:
	 */
	static final long SHOULD_REFRESH_IN = 30 * 60 * 1000;

	AccessTokenBean accessTokenBean;

	@Override
	public void setAccessToken(String accessToken, int expiresInSeconds) {
		this.accessTokenBean = new AccessTokenBean(accessToken, System.currentTimeMillis() + expiresInSeconds * 1000L);
	}

	@Override
	public String getAccessToken() {
		AccessTokenBean bean = this.accessTokenBean;
		if (bean == null || System.currentTimeMillis() > bean.expiresAt) {
			throw new WeixinAccessTokenException("Missing access token or already expired.");
		}
		return bean.accessToken;
	}

	@Override
	public boolean shouldRefresh() {
		return System.currentTimeMillis() - this.accessTokenBean.expiresAt < SHOULD_REFRESH_IN;
	}

	static class AccessTokenBean {

		final String accessToken;
		final long expiresAt;

		public AccessTokenBean(String accessToken, long expiresAt) {
			this.accessToken = accessToken;
			this.expiresAt = expiresAt;
		}
	}
}
