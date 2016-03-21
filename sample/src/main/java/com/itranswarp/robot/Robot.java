package com.itranswarp.robot;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itranswarp.wxapi.util.HttpUtil;
import com.itranswarp.wxapi.util.JsonUtil;
import com.itranswarp.wxapi.util.MapUtil;

@Component
public class Robot {

	static final String TULING_URL = "http://www.tuling123.com/openapi/api";

	@Value("${robot.app.id:}")
	String apiKey;

	public RobotResponse talk(String userId, String text) throws Exception {
		return talk(userId, text, null);
	}

	public RobotResponse talk(String userId, String text, String location) throws Exception {
		Map<String, String> params = MapUtil.createMap("key", apiKey, "userid", userId, "info", text);
		if (location != null && !location.isEmpty()) {
			params.put("loc", location);
		}
		String json = HttpUtil.httpPost(TULING_URL, HttpUtil.urlEncode(params), null);
		RobotResponse resp = JsonUtil.fromJson(RobotResponse.class, json);
		if (resp.code >= 40000 && resp.code <= 49999) {
			throw new RobotException(resp.code, resp.text);
		}
		return resp;
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
