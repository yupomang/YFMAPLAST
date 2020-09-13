package com.yondervision.yfmap.common.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import  javax.crypto.spec.DESedeKeySpec ;
import javax.crypto.spec.IvParameterSpec;
import org.apache.tomcat.util.codec.binary.Base64;

public class DesUtil {
	//向量
	private final static String IV="01234567";
	//加密编码
	private final static String ENCODING  ="UTF-8";
	//加密
	public static byte[] encrypt(String plainText,String deckey) throws Exception {
	//切始化问量
	IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
	//创建一个DESKeySpec对象
	DESedeKeySpec deskey =new DESedeKeySpec(deckey . getBytes());
	//创建一个密匙工厂
	SecretKeyFactory keyFactory=SecretKeyFactory.getInstance( "DESede");
	//将DESKeyspec对象转换成secretkey对豫
	SecretKey secureKey =keyFactory.generateSecret(deskey);
	//Cipher对象实际完成解密操作
	Cipher cipher=Cipher.getInstance( "DESede/CBC/PKCS5Padding");
	//用密匙初始化CIpher对象
	cipher. init(cipher. ENCRYPT_MODE, secureKey, ips);
	//真正开始加密操作
	return cipher.doFinal(plainText. getBytes(ENCODING));
	//return Base64.encodeBytesCencryptData);
	}
	
	public static String encryptCodecEncode(String plainText, String decKey) throws Exception {

	//先按decKey加密再使用org.apache.tomcat.util.codecbinary.Base64;.
	String encode = Base64.encodeBase64String(encrypt(plainText, decKey));
	return encode;
	}
}
