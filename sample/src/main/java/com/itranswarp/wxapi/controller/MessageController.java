package com.itranswarp.wxapi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itranswarp.robot.Robot;
import com.itranswarp.robot.Robot.RobotResponse;
import com.itranswarp.wxapi.event.AbstractEvent;
import com.itranswarp.wxapi.message.Message;
import com.itranswarp.wxapi.message.Message.MessageBuilder;
import com.itranswarp.wxapi.message.ReceivedImageMessage;
import com.itranswarp.wxapi.message.ReceivedLinkMessage;
import com.itranswarp.wxapi.message.ReceivedLocationMessage;
import com.itranswarp.wxapi.message.ReceivedShortVideoMessage;
import com.itranswarp.wxapi.message.ReceivedTextMessage;
import com.itranswarp.wxapi.message.ReceivedVideoMessage;
import com.itranswarp.wxapi.message.ReceivedVoiceMessage;
import com.itranswarp.wxapi.message.RepliedImageMessage;
import com.itranswarp.wxapi.message.RepliedTextMessage;
import com.itranswarp.wxapi.message.RepliedVideoMessage;
import com.itranswarp.wxapi.message.RepliedVoiceMessage;
import com.itranswarp.wxapi.util.XmlUtil;

@Controller
public class MessageController extends AbstractController {

	@Autowired
	Robot robot;

	/**
	 * Response echo message.
	 * 
	 * @param echoStr
	 *            Random string sent from weixin.
	 * @param request
	 *            The HttpServletRequest object.
	 * @return The echo string.
	 */
	@RequestMapping(value = "/message", method = RequestMethod.GET)
	@ResponseBody
	String token(@RequestParam(value = "echostr") String echoStr, HttpServletRequest request) {
		client.validateSignature(request);
		return echoStr;
	}

	String talk(String userId, String text) throws Exception {
		RobotResponse resp = robot.talk(userId, text);
		if (resp.code == RobotResponse.CODE_TEXT) {
			return resp.text;
		}
		return "听不懂你在说什么 :(";
	}

	/**
	 * Receive XML messages sent from weixin.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/message", method = RequestMethod.POST, consumes = "text/xml", produces = "text/xml;charset=utf-8")
	@ResponseBody
	String onMessageReceived(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String xml = client.readXml(request);
		log.info("weixin >>> " + xml);
		Message msg = XmlUtil.fromXml(Message.class, xml);
		MessageBuilder builder = Message.buildMessage(msg.ToUserName, msg.FromUserName);
		if (msg.isTextMessage()) {
			ReceivedTextMessage received = msg.asTextMessage();
			RepliedTextMessage reply = builder.toTextMessage(talk(msg.FromUserName, received.Content));
			log.info("<<< " + XmlUtil.toXml(reply, "xml"));
			return XmlUtil.toXml(reply, "xml");
		}
		if (msg.isImageMessage()) {
			ReceivedImageMessage received = msg.asImageMessage();
			log.debug(received.getClass().getSimpleName());
			RepliedImageMessage reply = builder.toImageMessage("ozKzHPBUWAi_wmtjJ0bvGMgY324WxWuat5QxIK9jzAs");
			log.info("<<< " + XmlUtil.toXml(reply, "xml"));
			return XmlUtil.toXml(reply, "xml");
		}
		if (msg.isVoiceMessage()) {
			ReceivedVoiceMessage received = msg.asVoiceMessage();
			log.debug(received.getClass().getSimpleName());
			if (received.Recognition.isEmpty()) {
				RepliedVoiceMessage reply = builder.toVoiceMessage("ozKzHPBUWAi_wmtjJ0bvGADRw-J6K-o7GgJZfFw7P00");
				log.info("<<< " + XmlUtil.toXml(reply, "xml"));
				return XmlUtil.toXml(reply, "xml");
			} else {
				RepliedTextMessage reply = builder.toTextMessage(talk(msg.FromUserName, received.Recognition));
				log.info("<<< " + XmlUtil.toXml(reply, "xml"));
				return XmlUtil.toXml(reply, "xml");
			}
		}
		if (msg.isVideoMessage()) {
			ReceivedVideoMessage received = msg.asVideoMessage();
			log.debug(received.getClass().getSimpleName());
			RepliedVideoMessage reply = builder.toVideoMessage("ozKzHPBUWAi_wmtjJ0bvGPJW6xqWlOR6JDngd4W8Twc", "Hi",
					"Bla bla bla...");
			log.info("<<< " + XmlUtil.toXml(reply, "xml"));
			return XmlUtil.toXml(reply, "xml");
		}
		if (msg.isShortVideoMessage()) {
			ReceivedShortVideoMessage received = msg.asShortVideoMessage();
			log.info(received);
			RepliedTextMessage reply = builder.toTextMessage("小电影我不看！");
			log.info("<<< " + XmlUtil.toXml(reply, "xml"));
			return XmlUtil.toXml(reply, "xml");
		}
		if (msg.isLocationMessage()) {
			ReceivedLocationMessage received = msg.asLocationMessage();
			log.info(received);
			RepliedTextMessage reply = builder.toTextMessage("你在" + received.Label + "？真不巧，我不住那。");
			log.info("<<< " + XmlUtil.toXml(reply, "xml"));
			return XmlUtil.toXml(reply, "xml");
		}
		if (msg.isLinkMessage()) {
			ReceivedLinkMessage received = msg.asLinkMessage();
			log.info(received);
			RepliedTextMessage reply = builder.toTextMessage("发链接我不看！");
			log.info("<<< " + XmlUtil.toXml(reply, "xml"));
			return XmlUtil.toXml(reply, "xml");
		}
		if (msg.isEvent()) {
			Object reply = processEvent(msg.asEvent(), builder);
			if (reply != null) {
				log.info("<<< " + XmlUtil.toXml(reply, "xml"));
				return XmlUtil.toXml(reply, "xml");
			}
		}

		RepliedTextMessage reply = builder.toTextMessage("I don't understand what you said :(");
		log.info("<<< " + XmlUtil.toXml(reply, "xml"));
		return XmlUtil.toXml(reply, "xml");
	}

	Object processEvent(AbstractEvent event, MessageBuilder builder) {
		switch (event.Event) {
		case AbstractEvent.EVENT_SUBSCRIBE:
			return builder.toTextMessage(getWelcome());
		case AbstractEvent.EVENT_UNSUBSCRIBE:
			// cannot send message to unsubscribe user:
			log.warn("User lost: " + event.FromUserName);
			return null;
		}
		return builder.toTextMessage("Cannot handle this event!");
	}

	String getWelcome() {
		int n = (int) (Math.random() * 100000) % WELCOMES.length;
		String s = WELCOMES[n];
		return s + "我是诗诗姑娘，欢迎关注我！";
	}

	static String[] WELCOMES = { "夜发清溪向三峡，思君不见下渝州。", "日日思君不见君，共饮长江水。", "花径不曾缘客扫，蓬门今始为君开。" };
}
