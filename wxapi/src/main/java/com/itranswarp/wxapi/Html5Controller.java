package com.itranswarp.wxapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Html5Controller extends AbstractController {

	@RequestMapping(value = "/h5", method = RequestMethod.GET)
	public ModelAndView index() {

		return new ModelAndView("index.html");
	}

}
