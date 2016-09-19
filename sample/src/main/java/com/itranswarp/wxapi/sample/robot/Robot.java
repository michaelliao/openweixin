package com.itranswarp.wxapi.sample.robot;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itranswarp.wxapi.util.HashUtil;
import com.itranswarp.wxapi.util.HttpUtil;
import com.itranswarp.wxapi.util.JsonUtil;
import com.itranswarp.wxapi.util.MapUtil;

@Component
public class Robot {

	static final String TULING_URL = "http://www.tuling123.com/openapi/api";

	static final Map<String, String> HEADERS = MapUtil.createMap("Content-Type", "application/json");

	@Value("${robot.app.key}")
	String apiKey;

	@Value("${robot.app.secret}")
	String apiSecret;

	public RobotResponse talk(String userId, String text) throws Exception {
		return talk(userId, text, null);
	}

	public RobotResponse talk(String userId, String text, String location) throws Exception {
		Map<String, String> params = MapUtil.createMap("userid", userId, "info", text);
		if (location != null && !location.isEmpty()) {
			params.put("loc", location);
		}
		long timestamp = System.currentTimeMillis();
		String jsonData = JsonUtil.toJson(params);
		String aesKey = HashUtil.md5(apiSecret + timestamp + apiKey);
		String encryptedData = aesEncrypt(aesKey, jsonData);
		Map<String, Object> postData = MapUtil.createMap("key", apiKey, "timestamp", timestamp, "data", encryptedData);
		String json = HttpUtil.httpPost(TULING_URL, JsonUtil.toJson(postData), HEADERS);
		RobotResponse resp = JsonUtil.fromJson(RobotResponse.class, json);
		if (resp.code >= 40000 && resp.code <= 49999) {
			throw new RobotException(resp.code, resp.text);
		}
		return resp;
	}

	String aesEncrypt(String aesKey, String data) throws GeneralSecurityException {
		Key key = new SecretKeySpec(aesKey.getBytes(), "AES");
		IvParameterSpec iv = new IvParameterSpec(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		byte[] encryptData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(encryptData);
	}

	public static class RobotResponse {

		public static int CODE_TEXT = 100000;
		public static int CODE_LINK = 200000;
		public static int CODE_NEWS = 302000;
		public static int CODE_COOK = 308000;
		public static int CODE_SONG = 313000;
		public static int CODE_POEM = 314000;

		public int code;
		public String text;
		public String url;
		public Item[] list;
		public Func function;
	}

	public static class Item {
		public String article;
		public String source;
		public String icon;
		public String info;
		public String detailurl;
	}

	public static class Func {
		public String song;
		public String singer;
		public String author;
		public String name;
	}

}
