package com.itranswarp.wxapi;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.itranswarp.wxapi.exception.WeixinException;
import com.itranswarp.wxapi.exception.WeixinSecurityException;
import com.itranswarp.wxapi.util.HashUtil;

@Controller
public class AbstractController {

	final Log log = LogFactory.getLog(getClass());

	@Autowired
	WeixinClient client;

	@Value("${weixin.app.id}")
	String appId;

	@Value("${weixin.app.secret}")
	String appSecret;

	@Value("${weixin.app.token}")
	String token;

	@Value("${weixin.app.aeskey}")
	String strAesKey;

	byte[] aesKey;
	byte[] ivParamSpec;

	@PostConstruct
	public void init() {
		if (this.strAesKey == null || this.strAesKey.length() != 43) {
			throw new IllegalArgumentException("Invalid property: weixin.app.aeskey");
		}
		aesKey = Base64.getDecoder().decode(strAesKey);
		ivParamSpec = Arrays.copyOfRange(aesKey, 0, 16);
	}

	protected void validateSignature(HttpServletRequest request) {
		String timestamp = request.getParameter("timestamp");
		validateTimestamp(timestamp);
		String nonce = request.getParameter("nonce");
		String signature = request.getParameter("signature");
		String[] arr = { token, nonce, timestamp };
		Arrays.sort(arr);
		String sha1 = HashUtil.sha1(String.join("", arr));
		if (!sha1.equals(signature)) {
			throw new WeixinSecurityException("Bad signature");
		}
	}

	protected boolean isAESEncryptedMessage(HttpServletRequest request) {
		return "aes".equals(request.getParameter("encrypt_type"));
	}

	protected String decryptMessage(HttpServletRequest request, String encryptedData) {
		String timestamp = request.getParameter("timestamp");
		validateTimestamp(timestamp);
		String nonce = request.getParameter("nonce");
		String msg_signature = request.getParameter("msg_signature");
		String[] arr = { token, nonce, timestamp, encryptedData };
		Arrays.sort(arr);
		String sha1 = HashUtil.sha1(String.join("", arr));
		if (!sha1.equals(msg_signature)) {
			throw new WeixinSecurityException("Bad signature");
		}
		try {
			return decrypt(encryptedData);
		} catch (GeneralSecurityException e) {
			throw new WeixinException(e);
		}
	}

	String decrypt(String data) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec keySpec = new SecretKeySpec(this.aesKey, "AES");
		IvParameterSpec iv = new IvParameterSpec(this.ivParamSpec);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
		byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(data));
		byte[] bytes = pkcs7Decode(decrypted);
		byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
		int xmlLength = recoverNetworkBytesOrder(networkOrder);
		String fromAppId = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), StandardCharsets.UTF_8);
		if (!fromAppId.equals(this.appId)) {
			throw new WeixinSecurityException("Invalid app id");
		}
		return new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), StandardCharsets.UTF_8);
	}

	void validateTimestamp(String timestamp) {
		long ts = Long.parseLong(timestamp);
		if (Math.abs(System.currentTimeMillis() / 1000 - ts) > 60) {
			throw new WeixinException("Bad timestamp");
		}
	}

	static byte[] pkcs7Decode(byte[] decrypted) {
		int pad = (int) decrypted[decrypted.length - 1];
		if (pad < 1 || pad > 32) {
			pad = 0;
		}
		return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
	}

	static int recoverNetworkBytesOrder(byte[] orderBytes) {
		int sourceNumber = 0;
		for (int i = 0; i < 4; i++) {
			sourceNumber <<= 8;
			sourceNumber |= orderBytes[i] & 0xff;
		}
		return sourceNumber;
	}
}
