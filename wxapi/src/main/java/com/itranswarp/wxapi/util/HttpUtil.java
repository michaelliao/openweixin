package com.itranswarp.wxapi.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpUtil {

	static final Log log = LogFactory.getLog(HttpUtil.class);

	static final int CONN_TIMEOUT = 3000;
	static final int READ_TIMEOUT = 2000;

	static final Map<String, String> contentTypeMap;

	static final String[] EMPTY_STRING_ARRAY = new String[0];

	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put(".txt", "text/plain");
		map.put(".html", "text/html");
		map.put(".htm", "text/html");
		map.put(".css", "text/css");
		map.put(".xml", "text/xml");

		map.put(".jpg", "image/jpeg");
		map.put(".jpe", "image/jpeg");
		map.put(".jpeg", "image/jpeg");
		map.put(".png", "image/png");
		map.put(".git", "image/gif");
		map.put(".svg", "image/svg+xml");
		map.put(".tiff", "image/tiff");
		map.put(".tif", "image/tiff");
		map.put(".webp", "image/webp");
		map.put(".ico", "image/x-icon");
		map.put(".bmp", "image/x-ms-bmp");

		map.put(".woff", "application/font-woff");
		map.put(".js", "application/javascript");
		map.put(".json", "application/json");
		map.put(".doc", "application/msword");
		map.put(".pdf", "application/pdf");
		map.put(".rtf", "application/rtf");
		map.put(".m3u8", "application/vnd.apple.mpegurl");
		map.put(".xls", "application/vnd.ms-excel");
		map.put(".eot", "application/vnd.ms-fontobject");
		map.put(".ppt", "application/vnd.ms-powerpoint");
		map.put(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
		map.put(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		map.put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		map.put(".7z", "application/x-7z-compressed");
		map.put(".rar", "application/x-rar-compressed");
		map.put(".swf", "application/x-shockwave-flash");
		map.put(".zip", "application/zip");
		map.put(".epub", "application/epub+zip");

		map.put(".mp3", "audio/mpeg");
		map.put(".ogg", "audio/ogg");
		map.put(".wav", "audio/wav");
		map.put(".midi", "audio/midi");
		map.put(".mid", "audio/midi");

		map.put(".3gp", "video/3gpp");
		map.put(".3gpp", "video/3gpp");
		map.put(".ts", "video/mp2t");
		map.put(".mp4", "video/mp4");
		map.put(".mpeg", "video/mpeg");
		map.put(".mpg", "video/mpeg");
		map.put(".mov", "video/quicktime");
		map.put(".webm", "video/webm");
		map.put(".flv", "video/x-flv");
		map.put(".f4v", "video/x-f4v");
		map.put(".m4v", "video/x-m4v");
		map.put(".mkv", "video/x-matroska");
		map.put(".vob", "video/x-ms-vob");
		map.put(".mng", "video/x-mng");
		map.put(".asx", "video/x-ms-asf");
		map.put(".asf", "video/x-ms-asf");
		map.put(".wmv", "video/x-ms-wmv");
		map.put(".avi", "video/x-msvideo");
		contentTypeMap = map;
	}

	public static String guessContentType(String fileName) {
		String ext = FileUtil.getFileExt(fileName);
		return contentTypeMap.getOrDefault(ext, "application/octet-stream");
	}

	public static Map<String, String> urlDecodeAsMap(String qs) {
		String[] ss = qs.split("&");
		Map<String, String> params = new HashMap<String, String>();
		for (String s : ss) {
			int pos = s.indexOf('=');
			if (pos <= 0) {
				log.warn("Invalid parameter: " + s);
			} else {
				params.put(s.substring(0, pos), urlDecode(s.substring(pos + 1)));
			}
		}
		return params;
	}

	public static String urlDecode(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String urlEncode(String s) {
		try {
			return URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Encode url key-value pairs sorted by key.
	 * 
	 * @param params
	 * @return
	 */
	public static String urlEncode(Map<String, String> params) {
		List<String> list = new ArrayList<String>(params.size());
		String[] keys = params.keySet().toArray(EMPTY_STRING_ARRAY);
		Arrays.sort(keys);
		for (String key : keys) {
			list.add(key + "=" + urlEncode(params.get(key)));
		}
		return String.join("&", list);
	}

	public static String httpGet(String url, Map<String, String> data, Map<String, String> headers) throws IOException {
		if (data != null && !data.isEmpty()) {
			char ch = url.indexOf('?') == (-1) ? '?' : '&';
			url = url + ch + urlEncode(data);
		}
		return http("GET", url, null, headers);
	}

	public static String httpPost(String url, String data, Map<String, String> headers) throws IOException {
		InputStream input = null;
		if (data != null && !data.isEmpty()) {
			byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
			if (headers == null) {
				headers = new HashMap<String, String>();
			}
			headers.put("Content-Length", String.valueOf(bytes.length));
			input = new ByteArrayInputStream(bytes);
		}
		return http("POST", url, input, headers);
	}

	public static String httpPost(String url, InputStream input, Map<String, String> headers) throws IOException {
		return http("POST", url, input, headers);
	}

	public static String httpPut(String url, String data, Map<String, String> headers) throws IOException {
		InputStream input = null;
		if (data != null && !data.isEmpty()) {
			byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
			if (headers == null) {
				headers = new HashMap<String, String>();
			}
			headers.put("Content-Length", String.valueOf(bytes.length));
			input = new ByteArrayInputStream(bytes);
		}
		return httpPut(url, input, headers);
	}

	public static String httpPut(String url, InputStream input, Map<String, String> headers) throws IOException {
		return http("PUT", url, input, headers);
	}

	public static String httpDelete(String url, String data, Map<String, String> headers) throws IOException {
		InputStream input = null;
		if (data != null && !data.isEmpty()) {
			byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
			if (headers == null) {
				headers = new HashMap<String, String>();
			}
			headers.put("Content-Length", String.valueOf(bytes.length));
			input = new ByteArrayInputStream(bytes);
		}
		return httpDelete(url, input, headers);
	}

	public static String httpDelete(String url, InputStream input, Map<String, String> headers) throws IOException {
		return http("DELETE", url, input, headers);
	}

	static String http(String method, String url, InputStream dataInput, Map<String, String> headers)
			throws IOException {
		log.info(method + ": " + url);
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod(method);
		conn.setConnectTimeout(CONN_TIMEOUT);
		conn.setReadTimeout(READ_TIMEOUT);
		conn.setUseCaches(false);
		conn.setInstanceFollowRedirects(false);
		conn.setAllowUserInteraction(false);
		conn.setDoOutput(dataInput != null);
		if (headers != null) {
			for (String key : headers.keySet()) {
				conn.setRequestProperty(key, headers.get(key));
			}
		}
		OutputStream output = null;
		InputStream input = null;
		int code = -1;
		try {
			if (dataInput != null) {
				output = conn.getOutputStream();
				byte[] buffer = new byte[10240];
				int n = 0;
				while ((n = dataInput.read(buffer)) != (-1)) {
					output.write(buffer, 0, n);
				}
				output.flush();
			}
			code = conn.getResponseCode();
			if (code / 100 != 2) {
				log.error("Bad response code: " + code + ": " + conn.getResponseMessage());
				input = conn.getErrorStream();
				if (input != null) {
					ByteArrayOutputStream byteArray = new ByteArrayOutputStream(4096);
					byte[] buffer = new byte[10240];
					int n = 0;
					while ((n = input.read(buffer)) != (-1)) {
						byteArray.write(buffer, 0, n);
					}
					log.error("HttpError: " + byteArray.toString("UTF-8"));
				}
				throw new IOException("Bad response: " + code);
			}
			input = conn.getInputStream();
			byte[] buffer = new byte[4096];
			int n = 0;
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			while ((n = input.read(buffer)) != (-1)) {
				byteArray.write(buffer, 0, n);
			}
			return new String(byteArray.toByteArray(), StandardCharsets.UTF_8);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
			conn.disconnect();
		}
	}

	/**
	 * Return "http" or "https" depends on client's request. NOTE the
	 * request.getScheme() is not work if server is under a reversed proxy.
	 * 
	 * @param request
	 *            The HttpServletRequest object.
	 * @return "http" or "https".
	 */
	public static String getScheme(HttpServletRequest request) {
		String scheme = request.getHeader("X-Forwarded-Proto");
		if (scheme == null) {
			scheme = request.getScheme();
		}
		return scheme;
	}

	public static Map<String, String> getParameters(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Enumeration<String> en = request.getParameterNames();
		while (en.hasMoreElements()) {
			String name = en.nextElement();
			String value = request.getParameter(name);
			params.put(name, value);
		}
		return params;
	}

	public static String getRequestURL(HttpServletRequest request) {
		String uri = request.getContextPath() + request.getRequestURI();
		if (!"GET".equals(request.getMethod())) {
			return uri;
		}
		String query = request.getQueryString();
		return query == null ? uri : uri + "?" + query;
	}
}
