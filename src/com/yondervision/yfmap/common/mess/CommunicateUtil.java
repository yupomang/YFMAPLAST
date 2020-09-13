package com.yondervision.yfmap.common.mess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;


public class CommunicateUtil {
	/**
	 * 
	 * @param sendType 通信方式 1为与CSP，2为BSP
	 * @param ip 通信地址
	 * @param port 通信端口
	 * @param message 发送信息
	 * @param trancode 交易码
	 * @return 返回报文
	 * @throws CenterRuntimeException
	 */
	public static String sendMessage(String sendType,String ip,int port,String message,String trancode) throws CenterRuntimeException {
		if(Constants.method_XML.equals(sendType)){
			sendType = "CSP";
		}else if(Constants.method_FSP.equals(sendType)){
			sendType = "FSP";
		}else if(Constants.method_BSP.equals(sendType)){
			sendType = "BSP";
		}
		AbsSendMessage sendCla = AbsSendMessage.getInstance(sendType);
		sendCla.setIp(ip);
		sendCla.setPort(port);
		Pattern p = Pattern.compile("\\*|\t|\r|\n");
		Matcher m = p.matcher(message);
		message=message.replace("\n", "");
		message=message.replace("\t", "");
		//message=message.replace(" ", "");
		sendCla.setUpItem(message);
		sendCla.setTranCode(trancode);
		sendCla.send();
		return sendCla.getRet();
	}
}
