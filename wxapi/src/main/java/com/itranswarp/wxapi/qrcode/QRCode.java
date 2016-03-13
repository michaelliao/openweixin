package com.itranswarp.wxapi.qrcode;

import javax.xml.bind.annotation.XmlRootElement;

import com.itranswarp.wxapi.util.HttpUtil;

@XmlRootElement
public class QRCode {

	public String ticket;
	public long expire_seconds;
	public String url;

	public String getQrCodeUrl() {
		return "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + HttpUtil.urlEncode(ticket);
	}

}
