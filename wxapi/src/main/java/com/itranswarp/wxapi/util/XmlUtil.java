package com.itranswarp.wxapi.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlUtil {

	public static String toXml(Object o) {
		XmlMapper mapper = new XmlMapper();
		try {
			return mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toXml(Object o, String rootName) {
		XmlMapper mapper = new XmlMapper();
		try {
			return mapper.writer().withRootName(rootName).writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromXml(Class<T> clazz, String s) {
		XmlMapper mapper = new XmlMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.readValue(s, clazz);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
