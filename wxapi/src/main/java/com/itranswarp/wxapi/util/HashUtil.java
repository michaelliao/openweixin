package com.itranswarp.wxapi.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HashUtil {

	public static String md5(String s) {
		MessageDigest md = getMessageDigest("md5");
		return bytes2HexString(md.digest(s.getBytes(StandardCharsets.UTF_8)));
	}

	public static String sha1(String s) {
		MessageDigest md = getMessageDigest("sha-1");
		return bytes2HexString(md.digest(s.getBytes(StandardCharsets.UTF_8)));
	}

	public static String sha256(String s) {
		MessageDigest md = getMessageDigest("sha-256");
		return bytes2HexString(md.digest(s.getBytes(StandardCharsets.UTF_8)));
	}

	static MessageDigest getMessageDigest(String name) {
		try {
			return MessageDigest.getInstance(name);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

	static String bytes2HexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length << 2);
		for (byte x : b) {
			int hi = (x & 0xf0) >> 4;
			int lo = x & 0x0f;
			sb.append(HEX_CHARS[hi]);
			sb.append(HEX_CHARS[lo]);
		}
		return sb.toString();
	}

	public static String hmacSha1(String data, String key) {
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
		Mac mac = null;
		try {
			mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
		byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
		return Base64.getEncoder().encodeToString(rawHmac);
	}
}
