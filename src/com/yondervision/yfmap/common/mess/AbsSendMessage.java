package com.yondervision.yfmap.common.mess;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;

public abstract class AbsSendMessage {
	/** 存放AbsMessageCtr实例的HashMap */
	private final static HashMap<String, AbsSendMessage> MAP_SENDTYPE_CTR = new HashMap<String, AbsSendMessage>();
	/** 实例累名称，模板编号：&ltmsgTemp&gt */
	private final static String CLASS_NAME_TEMP = "com.yondervision.yfmap.common.mess.<sendType>SendMessage";
	
	/**
	 * socket服务端ip
	 */
	String ip = null;
	/**
	 * socket服务端口
	 */
	int port = 0; 
	/**
	 * 发送报文头
	 */
	String head="";
	/**
	 * 加密标志
	 */
	String encryFlag = null;
	/**
	 * socket超时时间
	 */
	int TIME_OUT= 1200 * 1000;
	/**
	 * 编码字符集
	 */
	String encode = "GBK";
	Logger log = Logger.getLogger("YFMAP");
	/**
	 * 交易码
	 */
	String trancode=null;
	OutputStream out = null;;
	InputStream in = null;
	/**
	 * 发送信息
	 */
	String upItem = null;
	/**
	 * 接收信息
	 */
	String downItem = null;
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setUpItem(String upItem){
		this.upItem = upItem;
	}
	
	public void setTranCode(String trancode){
		this.trancode=trancode;
	}
	
	public String getRet(){	   
		 return downItem;
	}
	/**
	 * <pre>
	 *     创建/获取AbsMessageCtr的实例。
	 * </pre>
	 * 
	 * @param msgType
	 *            报文模板类型
	 * @return AbsMessageCtr的实例
	 * @throws CenterRuntimeException
	 */
	public static AbsSendMessage getInstance(String sendType) throws CenterRuntimeException {
		AbsSendMessage sendCla = MAP_SENDTYPE_CTR.get(sendType);
//		if (sendCla == null) {
			String className = CLASS_NAME_TEMP.replace("<sendType>", sendType);
			try {
				sendCla = (AbsSendMessage) Class.forName(className).newInstance();
				MAP_SENDTYPE_CTR.put(sendType, sendCla);
			} catch (InstantiationException e) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 创建/获取AbsSendMessage的实例异常。");
			} catch (IllegalAccessException e) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 创建/获取AbsSendMessage的实例异常。");
			} catch (ClassNotFoundException e) {
				throw new CenterRuntimeException(Constants.ERROR_999999, "前置机： 创建/获取AbsSendMessage的实例异常。");
			}
//		}
		return sendCla;
	}
	
	public abstract void send() throws CenterRuntimeException;
}
