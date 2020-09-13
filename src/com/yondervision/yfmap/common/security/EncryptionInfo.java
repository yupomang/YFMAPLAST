package com.yondervision.yfmap.common.security;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.util.PropertiesReader;

public class EncryptionInfo {
	public static String PROPERTIES_FILE_NAME = "properties.properties";
	public static String encryptionClassPath = "com.yondervision.yfmap.common.security";
	public static String getEncryptionFlag(){
		String encryptionFlag = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "encryptionFlag").trim();
		return encryptionFlag;
	}
	public static String getEncryptionClass(){
		String encryptionType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "encryptionType").trim();
		String encryptionClass = encryptionClassPath+".EncryptionBy"+encryptionType.toUpperCase();
		return encryptionClass;
	}
	public static String getEncryptionKey(){
		String encryptionKey = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "encryptionKey").trim();
		return encryptionKey;
	}
	public static byte[] encryptionMessage(String message){
		byte[] msg = null;
		try {
			EncryptionAbs encryptionClass =  (EncryptionAbs) Class.forName(getEncryptionClass()).newInstance();
			msg = encryptionClass.encryption(message, getEncryptionKey().getBytes());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999,"加密失败");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999,"加密失败");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999,"加密失败");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999,"加密失败");
		}
		return msg;
	}
	public static String dencryptionMessage(byte[] message){
		String msg = null;
		try {
			EncryptionAbs encryptionClass =  (EncryptionAbs) Class.forName(getEncryptionClass()).newInstance();
			msg = encryptionClass.dencryption(message, getEncryptionKey().getBytes());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999,"解密失败");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999,"解密失败");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999,"解密失败");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999,"解密失败");
		}
		return msg;
	}
}
