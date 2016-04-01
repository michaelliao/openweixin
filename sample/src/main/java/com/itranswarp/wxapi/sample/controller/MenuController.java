package com.itranswarp.wxapi.sample.controller;

import java.util.Map;

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
import com.itranswarp.wxapi.menu.Rule;
import com.itranswarp.wxapi.menu.ScanButton;
import com.itranswarp.wxapi.menu.ScanPushButton;
import com.itranswarp.wxapi.menu.SubMenu;
import com.itranswarp.wxapi.menu.ViewButton;
import com.itranswarp.wxapi.util.HttpUtil;
import com.itranswarp.wxapi.util.MapUtil;

@RestController
public class MenuController extends AbstractController {

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	Object getMenu() {
		return client.getJson(Map.class, "/menu/get?access_token=" + HttpUtil.urlEncode(client.getAccessToken()), null);
	}

	@RequestMapping(value = "/menu/create", method = RequestMethod.GET)
	public Object createMenu(@RequestParam(value = "sex", defaultValue = "") String sex) {
		Menu menu = new Menu();
		menu.addButton(new SubMenu("发图", new PhotoButton("拍照", "photo"), new AlbumButton("图库", "album"),
				new AlbumOrPhotoButton("拍照或选图", "photo-or-album")));
		menu.addButton(new SubMenu("扫码", new ScanButton("扫一扫", "scan"), new ScanPushButton("扫码关注", "scanpush-1"),
				new LocationButton("发位置", "send-location"),
				new MediaButton("素材", "ozKzHPBUWAi_wmtjJ0bvGADRw-J6K-o7GgJZfFw7P00"),
				new ClickButton("点一点", "click-it")));
		if (!sex.isEmpty()) {
			Rule rule = new Rule();
			rule.sex = sex;
			menu.matchrule = rule;
			menu.addButton(new ViewButton("个性化", "http://weixin.liaoxuefeng.com/"));
		} else {
			menu.addButton(new ViewButton("网站", "http://weixin.liaoxuefeng.com/"));
		}
		client.createMenu(menu);
		return MapUtil.createMap("menu", "OK");
	}
}
