package com.itranswarp.wxapi.token;

/**
 * Simple JavaBean to deserialize from JSON string returned by weixin.
 * 
 * @author michael
 */
public class AccessToken {

	/**
	 * The access token string.
	 */
	public String access_token;

	/**
	 * Expires in seconds.
	 */
	public int expires_in;

}
