package com.itranswarp.wxapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.itranswarp.wxapi.exception.WeixinException;
import com.itranswarp.wxapi.util.HttpUtil;
import com.itranswarp.wxapi.util.JsonUtil;
import com.itranswarp.wxapi.util.MapUtil;

@Component
public class WeixinClient {

	final Log log = LogFactory.getLog(getClass());

	static final String WEIXIN_URL = "https://api.weixin.qq.com/cgi-bin";

	boolean debug = true;

	public <T> T getJson(Class<T> clazz, String uri, Map<String, String> data) {
		String json;
		try {
			json = HttpUtil.httpGet(WEIXIN_URL + uri, data, null);
		} catch (IOException e) {
			throw new WeixinException(e);
		}
		if (debug) {
			log.info("Weixin >>> " + json);
		}
		if (json.contains("\"errcode\"")) {
			JsonError err = JsonUtil.fromJson(JsonError.class, json);
			throw new WeixinException(err.errcode, err.errmsg);
		}
		return JsonUtil.fromJson(clazz, json);
	}

	static final Map<String, String> CONTENT_TYPE_JSON = MapUtil.createMap("Content-Type", "application/json");

	static final Map<String, String> CONTENT_TYPE_WWW_FORM = MapUtil.createMap("Content-Type", "application/x-www-form-urlencoded");

	static final Map<String, String> CONTENT_TYPE_MULTIPART = MapUtil.createMap("Content-Type", "multipart/form-data");

	public <T> T postJson(Class<T> clazz, String uri, Object data) {
		String json;
		try {
			json = HttpUtil.httpPost(WEIXIN_URL + uri, JsonUtil.toJson(data), CONTENT_TYPE_JSON);
		} catch (IOException e) {
			throw new WeixinException(e);
		}
		if (debug) {
			log.info("Weixin >>> " + json);
		}
		if (json.contains("\"errcode\"")) {
			JsonError err = JsonUtil.fromJson(JsonError.class, json);
			throw new WeixinException(err.errcode, err.errmsg);
		}
		return JsonUtil.fromJson(clazz, json);
	}

	public <T> T postForm(Class<T> clazz, String uri, Map<String, Object> params) {
		InputStream data = encodeFormData(params);
		String json;
		try {
			json = HttpUtil.httpPost(WEIXIN_URL + uri, data, null);
		} catch (IOException e) {
			throw new WeixinException(e);
		}
		if (debug) {
			log.info("Weixin >>> " + json);
		}
		if (json.contains("\"errcode\"")) {
			JsonError err = JsonUtil.fromJson(JsonError.class, json);
			throw new WeixinException(err.errcode, err.errmsg);
		}
		return JsonUtil.fromJson(clazz, json);
	}

	InputStream encodeFormData(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	public static class JsonError {
		public int errcode;
		public String errmsg;
	}
}
