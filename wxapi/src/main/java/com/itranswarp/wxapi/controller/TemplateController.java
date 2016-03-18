package com.itranswarp.wxapi.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itranswarp.wxapi.template.Industry;
import com.itranswarp.wxapi.template.TemplateList;
import com.itranswarp.wxapi.util.HttpUtil;
import com.itranswarp.wxapi.util.MapUtil;

@RestController
public class TemplateController extends AbstractController {

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public Object sendMessageByTemplate(@RequestParam("templateId") String templateId, @RequestParam("to") String to) {
		String accessToken = client.getAccessToken();
		Map<String, Object> data = MapUtil.createMap("touser", to, "template_id", templateId, "url",
				"http://weixin.qq.com/download/", "topcolor", "#FF0000", "data",
				MapUtil.createMap("user", MapUtil.createMap("value", "Mr兔子", "color", "#FF0000")));
		return client.postJson(Map.class, "/message/template/send?access_token=" + HttpUtil.urlEncode(accessToken),
				data);
	}

	@RequestMapping(value = "/templates", method = RequestMethod.GET)
	public TemplateList getTemplates() {
		return client.getTemplateList();
	}

	@RequestMapping(value = "/template/setIndustry", method = RequestMethod.GET)
	public Object setIndustry() {
		Industry industry = new Industry();
		industry.industry_id1 = Industry.EDUCATION_TRAINING;
		industry.industry_id2 = Industry.BUSINESS_AGENT;
		return client.setIndustry(industry);
	}

	@RequestMapping(value = "/template/getIndustry", method = RequestMethod.GET)
	public Object getIndustry() {
		return client.getIndustry();
	}
}
