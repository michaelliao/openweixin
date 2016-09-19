package com.itranswarp.wxapi.sample.robot;

import static org.junit.Assert.*;

import org.junit.Test;

import com.itranswarp.wxapi.util.HashUtil;

public class RobotTest {

	@Test
	public void test() throws Exception {
		Robot robot = new Robot();
		robot.apiKey = "key";
		robot.apiSecret = "123";
		String timestamp = "456789";
		String keyParam = HashUtil.md5(robot.apiSecret + timestamp + robot.apiKey);
		assertEquals("912194e51267870e9283e9a035360a78", keyParam);
		String param = "{\"info\":\"你好\"}";
		String data = robot.aesEncrypt(keyParam, param);
		assertEquals("TwPFGlIQk/yl2qDbNyuSQg9JMeV6aLdCS7yo6lT5Ia0=", data);
	}

}
