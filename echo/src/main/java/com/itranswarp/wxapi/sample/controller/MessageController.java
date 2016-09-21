package com.itranswarp.wxapi.sample.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.itranswarp.wxapi.message.RepliedTextMessage;
import com.itranswarp.wxapi.util.XmlUtil;

@Controller
public class MessageController extends AbstractController {

	/**
	 * Response echo message.
	 * 
	 * @param echoStr
	 *            Random string sent from weixin.
	 * @param request
	 *            The HttpServletRequest object.
	 * @return The echo string.
	 */
	@RequestMapping(value = "/wxapi/message", method = RequestMethod.GET)
	@ResponseBody
	String token(@RequestParam(value = "echostr") String echoStr, HttpServletRequest request) {
		client.validateSignature(request);
		return echoStr;
	}

	/**
	 * Receive XML messages sent from weixin.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/wxapi/message", method = RequestMethod.POST, consumes = "text/xml", produces = "text/xml;charset=utf-8")
	@ResponseBody
	String onMessageReceived(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String xml = client.readXml(request);
		log.info("weixin >>> " + xml);
		Message msg = XmlUtil.fromXml(Message.class, xml);
		MessageBuilder builder = Message.buildMessage(msg.ToUserName, msg.FromUserName);
		if (msg.isTextMessage()) {
			ReceivedTextMessage received = msg.asTextMessage();
			RepliedTextMessage reply = builder.toTextMessage("Echo: " + received.Content);
			log.info("<<< " + XmlUtil.toXml(reply, "xml"));
			return XmlUtil.toXml(reply, "xml");
		}
		if (msg.isImageMessage()) {
			ReceivedImageMessage received = msg.asImageMessage();
			log.debug(received.getClass().getSimpleName());
			RepliedTextMessage reply = builder.toTextMessage("Don't show me the picture!");
			log.info("<<< " + XmlUtil.toXml(reply, "xml"));
			return XmlUtil.toXml(reply, "xml");
		}
		if (msg.isVoiceMessage()) {
			ReceivedVoiceMessage received = msg.asVoiceMessage();
			log.debug(received.getClass().getSimpleName());
			if (received.Recognition.isEmpty()) {
				RepliedTextMessage reply = builder.toTextMessage("Don't talk to me!");
				log.info("<<< " + XmlUtil.toXml(reply, "xml"));
				return XmlUtil.toXml(reply, "xml");
			} else {
				RepliedTextMessage reply = builder.toTextMessage("Sorry, I don't understand what you said!");
				log.info("<<< " + XmlUtil.toXml(reply, "xml"));
				return XmlUtil.toXml(reply, "xml");
			}
		}
		if (msg.isVideoMessage()) {
			ReceivedVideoMessage received = msg.asVideoMessage();
			log.debug(received.getClass().getSimpleName());
			RepliedTextMessage reply = builder.toTextMessage("Don't show me the video!");
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
		default:
			return builder.toTextMessage("Cannot handle this event!");
		}
	}

	String getWelcome() {
		return "你好，欢迎关注我！";
	}
}
