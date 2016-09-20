package com.itranswarp.wxapi.sample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itranswarp.wxapi.user.UserInfo;
import com.itranswarp.wxapi.user.UserList;

@RestController
public class UserController extends AbstractController {

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public UserList getUsers(@RequestParam(value = "nextOpenId", defaultValue = "") String nextOpenId) {
		return client.getUsers(nextOpenId);
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public UserInfo getUserInfo(@RequestParam("openId") String openId) {
		return client.getUserInfo(openId);
	}
}
