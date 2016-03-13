package com.itranswarp.wxapi.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class FileUtil {

	static final Pattern EXT_PATTERN = Pattern.compile("^\\.[a-z0-9]{1,10}$");

	/**
	 * Get file extension as ".txt", or "" if no extension.
	 * 
	 * @param fileName
	 *            The file name as String.
	 * @return File extension.
	 */
	public static String getFileExt(String fileName) {
		if (fileName == null) {
			return "";
		}
		int pos = fileName.lastIndexOf('.');
		if (pos == (-1)) {
			return "";
		}
		String ext = fileName.substring(pos).trim().toLowerCase();
		if (EXT_PATTERN.matcher(ext).matches()) {
			return ext;
		}
		return "";
	}

	static final String UNNAMED = "(unnamed)";

	public static String getMainFileName(String originalFileName) {
		if (originalFileName == null) {
			return UNNAMED;
		}
		originalFileName = originalFileName.trim();
		String name = originalFileName;
		int pos = originalFileName.lastIndexOf('.');
		if (pos != (-1)) {
			name = originalFileName.substring(0, pos).trim();
		}
		return name.isEmpty() ? UNNAMED : (name.length() <= 100 ? name : name.substring(0, 97) + "...");
	}

	public static byte[] getResource(String resource) throws IOException {
		InputStream input = FileUtil.class.getResourceAsStream(resource);
		if (input == null) {
			throw new IOException("Resource not found: " + resource);
		}
		try {
			try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
				byte[] buffer = new byte[10240];
				int n;
				while ((n = input.read(buffer)) != -1) {
					output.write(buffer, 0, n);
				}
				return output.toByteArray();
			}
		} finally {
			input.close();
		}
	}

	public static void writeString(String path, String data) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8))) {
			writer.write(data);
		}
	}

	public static String readAsString(String path) throws IOException {
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
			String line = null;
			StringBuilder sb = new StringBuilder(4096);
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
			return sb.toString();
		}
	}
}
