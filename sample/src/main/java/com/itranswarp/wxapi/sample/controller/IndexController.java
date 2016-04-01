package com.itranswarp.wxapi.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController extends AbstractController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() {
		return new ModelAndView("index.html");
	}

	@RequestMapping(value = "/h5", method = RequestMethod.GET)
	public ModelAndView h5() {
		return new ModelAndView("index.html");
	}

}
