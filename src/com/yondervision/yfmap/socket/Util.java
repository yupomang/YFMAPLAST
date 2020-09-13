/**
 * 
 */
package com.yondervision.yfmap.socket;

import java.io.UnsupportedEncodingException;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.security.DESForJava;
import com.yondervision.yfmap.util.PropertiesReader;

public class Util {

//	private static ReadProperties rp = ReadProperties.getInstence(ContextUtils.selfDefineLogMessage_PR);
	
	public static final String TRANSCODE_PATH = "/root/head/transCode";
	
	public static final String SUCCESS_FLAG_PATH = "/root/head/ans_code";
	
	/**
	 * 成功标志-0
	 */
	public static final String SUCCESS_FLAG = "000000";
	
	/**
	 * 加密
	 */
	public static final String ENCRYPTION_FLAG = "1";
	
	/**
	 * 不加密
	 */
	public static final String UN_ENCRYPTION_FLAG = "0";
	
	public static final String CONFIG_FILE_NAME = "config.properties";

	/**
	* <pre> 密钥 </pre>
	* @param @return    
	*/
	public static String getKey() {
		return PropertiesReader.getProperty("properties.properties", "YFMAPServerSendMessageKey").trim();
	}
	
	public static String charset = PropertiesReader.getProperty("properties.properties", "YFMAPServerSendMessageCharset").trim();
	/**
	* <pre> 取得发送报文 </pre>
	* @param @param message
	* @param @param encryptionFlag
	* @param @return byte[]
	* @param @throws Exception
	* @param @throws UnsupportedEncodingException    
	*/
	public static byte[] getSendMessage(String message, boolean encryptionFlag) throws CenterRuntimeException{
		byte[] bMessage = null;
		if (encryptionFlag) {
			try {
				bMessage = DESForJava.encryption(message, Util.getKey().getBytes());
			} catch (UnsupportedEncodingException e) {				
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("100001","加密处理异常");
	            throw cre;
			}
		} else {
			try {
				bMessage = message.getBytes(charset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				CenterRuntimeException cre = new CenterRuntimeException("100001","加密处理异常");
	            throw cre;
			}
		}
		return bMessage;
	}

	
	/**
	 * @param bMessage
	 * @param encryptionFlag
	 * @return
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	public static String getReceiveMessage(byte[] bMessage,
			boolean encryptionFlag) throws CenterRuntimeException {
		String message = null;
		try{
			if (encryptionFlag) {
				message = DESForJava.decryption(bMessage, Util.getKey().getBytes());
			} else {
				message = new String(bMessage, charset);
			}
		}catch(Exception e){
			e.printStackTrace();
			CenterRuntimeException cre = new CenterRuntimeException("100002","解密处理异常");
            throw cre;
		}
		return message;
	}
}
