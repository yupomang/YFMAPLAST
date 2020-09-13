package com.yondervision.yfmap.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yondervision.yfmap.common.Constants;

@Controller
public class WebHeartBeatContorller {
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 短信心跳接口
	 */
	@RequestMapping(value = "/heartbeat.{ext}")
	public void heartbeat(HttpServletResponse response) throws Exception {
		log.info(Constants.LOG_HEAD + "heartbeat begin.");
		String result = " {\"remsg\":\"成功\",\"recode\":\"000000\"}";
		response.getOutputStream().write(result.getBytes("utf-8"));
		log.info(Constants.LOG_HEAD + "heartbeat end.");
		return;
	}
}
