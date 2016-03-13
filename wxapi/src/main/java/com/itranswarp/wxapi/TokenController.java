package com.itranswarp.wxapi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TokenController extends AbstractController {

	@RequestMapping(value = "/message", method = RequestMethod.GET)
	@ResponseBody
	String token(@RequestParam(value = "echostr", defaultValue = "") String echoStr, HttpServletRequest request) {
		validateSignature(request);
		return echoStr;
	}

}
