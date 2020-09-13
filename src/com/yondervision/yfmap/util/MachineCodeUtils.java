package com.yondervision.yfmap.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MachineCodeUtils{
public static String toMd5String(String pwd) {
		String key = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pwd.getBytes());
			key = bytesToHexString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return key;
	
	}
	private static String bytesToHexString(byte [] bt) {
		 StringBuffer sb = new StringBuffer(bt.length);  
	     String sTemp;  
	        for (int i = 0; i < bt.length; i++) {  
	            sTemp = Integer.toHexString(0xFF & bt[i]);  
	            if (sTemp.length() < 2)  
	                sb.append(0);  
	            sb.append(sTemp.toUpperCase());  
	        }  
	        return sb.toString();  
	}
}