package com.yondervision.yfmap.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;


import com.mysql.jdbc.log.LogUtils;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.messagectrl.MessageDataConversion;

/**
 * <pre>回推业务抽象类</pre>
 *
 * @author Z.Y.Cao
 * @since  Jan 13, 2013
 */
public abstract class AbsBusinessServer {
	
	
	
	public static final String jym_3001 = "3001";	
	public static final String jym_3002 = "3002";	
	private static final String SERVICE_PACKAGE = "com.yondervision.yfmap.handle.handleImpl.handle";

	public abstract String acction(String xml,OutputStream out,String centerid, String seq)throws CenterRuntimeException ;
	
	/**
	* <pre> 获得SOCKET客户端处理类 </pre>
	* @param @param serviceName
	* @param @return    
	*/
	public static AbsBusinessServer getService(String serviceName, String centerid) throws CenterRuntimeException{
		String serviceClassName = new StringBuffer().append(SERVICE_PACKAGE)
				.append(centerid).append(".Handle"+serviceName+"_"+centerid).toString().trim();
		AbsBusinessServer business = null;
		try {
			business = (AbsBusinessServer) Class.forName(serviceClassName).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			CenterRuntimeException cre = new CenterRuntimeException("999999","前置实例化短信息处理类异常:"+serviceClassName);
			throw cre;
		} 
		return business;
	}	
	
	/**
	 * 发送报文
	 * 
	 * @param out
	 *            输出流
	 * @param msg
	 *            发送的报文
	 */
	public void sendXML(OutputStream out, byte[] msg) {
		int byteSize = 4096;
		int msgLength = msg.length;
		byte[] temp = new byte[byteSize];
		int wLine = 0;
		try {
			/*
			 * 发送长度和加密标志
			 */
			String strByteSize = String.valueOf(msgLength + 1);
			/*String head = new StringBuffer().append(
					"00000000".substring(strByteSize.length())).append(
					strByteSize).append(
					this.encryptionFlag ? KMUtil.ENCRYPTION_FLAG
							: KMUtil.UN_ENCRYPTION_FLAG).toString();*/
			String head = new StringBuffer(strByteSize).append("        ".substring(strByteSize.length())).append("0").toString();
			out.write(head.getBytes());
			/*
			 * 发送报文体
			 */
			while (wLine < msgLength) {
				if (wLine + byteSize > msgLength) {
					temp = new byte[msgLength - wLine];
				}
				System.arraycopy(msg, wLine, temp, 0, temp.length);
				out.write(temp);
				wLine += temp.length;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	/**
	* <pre> CSP通信报文封装 </pre>
	* @param @param xml 原报文
	* @param @param jym 交易码
	* @param @return CSP通信报文体
	* @param @throws YQZLException    
	*/
//	public String getHFIsendMessage(String xml,String jym) throws YQZLException {
//		SimpleDateFormat formatter1 = new SimpleDateFormat(MessageDataConversion.dateFormat);
//		SimpleDateFormat formatter2 = new SimpleDateFormat(MessageDataConversion.dateTimeFormatM);		
//		StringBuffer msg = new StringBuffer();
//		msg.append("<AuthFlag>0</><TellCode>9999</><ChkCode>0000</><BrcCode>");
//		msg.append("00045301</><TranIP>"+MessageTool.getLocalIP()+"</><TranCode>"+jym+"</><ChannelSeq>000</><TranChannel>0</>");
//		msg.append("<TranDate>"+formatter1.format(System.currentTimeMillis())+"</>");
//		msg.append("<STimeStamp>"+formatter2.format(System.currentTimeMillis())+"</>");
//		msg.append("<MTimeStamp>"+formatter2.format(System.currentTimeMillis())+"</><AuthCode2>1112</><AuthCode3>1112</>");
//		msg.append("<AuthCode1>1112</><OppOperList></><HostBank>null</><SubBank>null</>");
//		msg.append("<FinancialDate>"+formatter1.format(System.currentTimeMillis())+"</>");
//		msg.append("<sjzmessage>"+MessageTool.getTranslationToHFI(xml)+"</sjzmessage><OppOperList></>");
//		LogUtils.info(ContextUtils.COM_PAYMENT,ErrorCodeDefinition.MESSAGE_INFO," ******** To csp xml = "+msg.toString().trim());
//		return msg.toString().trim();
//	}
}
