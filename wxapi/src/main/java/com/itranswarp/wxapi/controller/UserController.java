package com.itranswarp.wxapi.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itranswarp.wxapi.menu.AlbumButton;
import com.itranswarp.wxapi.menu.AlbumOrPhotoButton;
import com.itranswarp.wxapi.menu.ClickButton;
import com.itranswarp.wxapi.menu.LocationButton;
import com.itranswarp.wxapi.menu.MediaButton;
import com.itranswarp.wxapi.menu.Menu;
import com.itranswarp.wxapi.menu.PhotoButton;
import com.itranswarp.wxapi.menu.ScanButton;
import com.itranswarp.wxapi.menu.ScanPushButton;
import com.itranswarp.wxapi.menu.SubMenu;
import com.itranswarp.wxapi.menu.ViewButton;
import com.itranswarp.wxapi.user.UserInfo;
import com.itranswarp.wxapi.user.UserList;
import com.itranswarp.wxapi.util.HttpUtil;
import com.itranswarp.wxapi.util.JsonUtil;

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
