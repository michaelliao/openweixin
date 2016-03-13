package com.itranswarp.wxapi.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itranswarp.wxapi.util.HttpUtil;
import com.itranswarp.wxapi.util.JsonUtil;

@Controller
public class MenuController extends AbstractController {

	@RequestMapping(value = "/createMenu1", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	String menu1(@RequestParam(value = "accessToken") String accessToken, HttpServletRequest request) {
		Map r = client.postJson(Map.class, "/menu/create?access_token=" + HttpUtil.urlEncode(accessToken),
				JsonUtil.parseAsMap(MENU1));
		return JsonUtil.toJson(r);
	}

	@RequestMapping(value = "/createMenu2", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	String menu2(@RequestParam(value = "accessToken") String accessToken, HttpServletRequest request) {
		Map r = client.postJson(Map.class, "/menu/create?access_token=" + HttpUtil.urlEncode(accessToken),
				JsonUtil.parseAsMap(MENU2));
		return JsonUtil.toJson(r);
	}

	static final String MENU1 = "{\"button\": [{\"type\": \"click\", \"name\": \"今日歌曲\", \"key\": \"V1001_TODAY_MUSIC\"}, {\"name\": \"菜单\", \"sub_button\": [{\"url\": \"http://weixin.liaoxuefeng.com/h5/\", \"type\": \"view\", \"name\": \"搜索\"}, {\"url\": \"http://weixin.liaoxuefeng.com/h5/\", \"type\": \"view\", \"name\": \"视频\"}, {\"type\": \"click\", \"name\": \"赞一下我们\", \"key\": \"V1001_GOOD\"}]}]}";
	static final String MENU2 = "{\"button\": [{\"name\": \"扫码\", \"sub_button\": [{\"type\": \"scancode_waitmsg\", \"name\": \"扫码带提示\", \"key\": \"rselfmenu_0_0\", \"sub_button\": []}, {\"type\": \"scancode_push\", \"name\": \"扫码推\", \"key\": \"rselfmenu_0_1\", \"sub_button\": []}]}, {\"name\": \"发图\", \"sub_button\": [{\"type\": \"pic_sysphoto\", \"name\": \"拍照发图\", \"key\": \"rselfmenu_1_0\", \"sub_button\": []}, {\"type\": \"pic_photo_or_album\", \"name\": \"拍照或相册\", \"key\": \"rselfmenu_1_1\", \"sub_button\": []}, {\"type\": \"pic_weixin\", \"name\": \"微信相册发图\", \"key\": \"rselfmenu_1_2\", \"sub_button\": []}]}, {\"type\": \"location_select\", \"name\": \"发送位置\", \"key\": \"rselfmenu_2_0\"}]}";
}
