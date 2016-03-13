package com.itranswarp.wxapi.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> parseAsMap(String s) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(s, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String toJson(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromJson(Class<T> clazz, String s) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.readValue(s, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
