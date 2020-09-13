package com.yondervision.yfmap.common.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;



import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.util.PropertiesReader;

public class AES {
	Logger log = Logger.getLogger("YFMAP");
	
	private final String KEY_GENERATION_ALG = "PBKDF2WithHmacSHA1";

	private final int HASH_ITERATIONS = 10000;
	private final int KEY_LENGTH = 128;

	// 定义密钥
	private char[] KEY = { '1', 'a', '2', 'b', '3', 'c', '4', 'd', '5', 'e', '6', 'f', '7', 'g', '8', 'h' };
//	private char[] KEY_00087100_10 = { '0', '1', 'p', 'c', 'a', '3', '5', 'D', 'p', 'W', '8', 'f', 'g', 'G', '3', 'd' };
//	private char[] KEY_00087100_20 = { '0', '2', '2', 'm', 'e', '6', '2', 'J', 'j', 'T', '4', 'h', 'w', 'Y', '6', 'h' };
//	private char[] KEY_00087100_30 = { '0', '3', 'E', 'k', 'E', '8', '7', 'W', 'k', 'X', '2', 'f', 'u', 'T', '2', 's' };
//	private char[] KEY_00087100_40 = { '0', '4', '2', 'b', 'H', '2', '1', 'k', 'e', 'N', '9', 'g', 'i', 'G', '8', 'x' };
//	private char[] KEY_00087100_50 = { '0', '5', 'X', 'd', '3', '1', '8', 'I', 's', 'U', '1', 'x', 'o', 'I', '1', 'j' };
//	private char[] KEY_00087100_60 = { '0', '6', 'Q', 's', 'U', '9', '5', 'O', 'e', 'K', '0', 'c', 'w', 'D', '8', 'e' };
//	private char[] KEY_00087100_70 = { '0', '7', 'J', 'b', 'E', '0', '1', 'C', 'q', 'X', '6', 'r', 'x', 'B', '0', 'u' };
//	private char[] KEY_00087100_80 = { '0', '8', 'Y', 'c', 'P', '1', '3', 'B', 'a', 'M', '3', 'u', 'p', 'I', '9', 'i' };
//	
//	private char[] KEY_00073300_10 = { '0', '1', 'p', 'c', 'a', 'i', 'j', 's', 'm', 'k', 'e', 'f', 'g', 'G', '3', 'd' };
//	private char[] KEY_00073300_20 = { 'p', 'p', 'o', '1', '9', '2', '2', 'J', 'j', 'T', '4', 'h', 'w', 'Y', '6', 'h' };
//	private char[] KEY_00073300_30 = { '0', '3', 'E', 'k', 'E', '8', '7', 'i', 'j', 'f', 'e', '3', '6', 'T', '2', 's' };
//	private char[] KEY_00073300_40 = { '0', '4', '2', 'b', 'H', '2', '1', 'p', 'q', 'a', 'd', '2', 'k', 'G', '8', 'x' };
//	private char[] KEY_00073300_50 = { '0', 'J', 'k', 'I', '7', '2', 'j', 'I', 's', 'U', '1', 'x', 'o', 'I', '1', 'j' };
//	private char[] KEY_00073300_60 = { '0', '6', 'Q', 's', 'K', 'W', 'L', 'M', '2', '1', '0', 'c', 'w', 'D', '8', 'e' };
//	private char[] KEY_00073300_70 = { '0', '7', 'J', 'b', 'E', '0', '1', 'C', 'q', 'X', 'i', 'q', 'W', '1', 'j', 'c' };
//	private char[] KEY_00073300_80 = { '0', '8', 'Y', 'c', 'P', '1', '3', 'B', 'a', 'M', '1', '9', 'I', 'j', 'w', 'l' };
//	
//	private char[] KEY_00066800_10 = { '0', '1', 'p', 'c', 'a', 'i', 'j', 's', 'm', 'k', 'e', 'f', 'g', 'G', '3', 'd' };
//	private char[] KEY_00066800_20 = { 'p', 'p', 'o', '1', '9', '2', '2', 'J', 'j', 'T', '4', 'h', 'w', 'Y', '6', 'h' };
//	private char[] KEY_00066800_30 = { '0', '3', 'E', 'k', 'E', '8', '7', 'i', 'j', 'f', 'e', '3', '6', 'T', '2', 's' };
//	private char[] KEY_00066800_40 = { '0', '4', '2', 'b', 'H', '2', '1', 'p', 'q', 'a', 'd', '2', 'k', 'G', '8', 'x' };
//	private char[] KEY_00066800_50 = { '0', 'J', 'k', 'I', '7', '2', 'j', 'I', 's', 'U', '1', 'x', 'o', 'I', '1', 'j' };
//	private char[] KEY_00066800_60 = { '0', '6', 'Q', 's', 'K', 'W', 'L', 'M', '2', '1', '0', 'c', 'w', 'D', '8', 'e' };
//	private char[] KEY_00066800_70 = { '0', '7', 'J', 'b', 'E', '0', '1', 'C', 'q', 'X', 'i', 'q', 'W', '1', 'j', 'c' };
//	private char[] KEY_00066800_80 = { '0', '8', 'Y', 'c', 'P', '1', '3', 'B', 'a', 'M', '1', '9', 'I', 'j', 'w', 'l' };
	
	private byte[] salt = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF }; // must save this for next
																							// time we want the key

	private PBEKeySpec myKeyspec = new PBEKeySpec(KEY, salt, HASH_ITERATIONS, KEY_LENGTH);
//	private final String CIPHERMODEPADDING = "AES/CBC/PKCS7Padding";
	private static String CIPHERMODEPADDING = "AES/CBC/PKCS5Padding";

	private SecretKeyFactory keyfactory = null;
	private SecretKey sk = null;
	private SecretKeySpec skforAES = null;
	private byte[] iv = { 0xA, 1, 0xB, 5, 4, 0xF, 7, 9, 0x17, 3, 1, 6, 8, 0xC, 0xD, 91 };

	private IvParameterSpec IV;

	public AES() {

		try {
			keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
			sk = keyfactory.generateSecret(myKeyspec);

		} catch (NoSuchAlgorithmException nsae) {
			log.error("no key factory support for PBEWITHSHAANDTWOFISH-CBC");
			nsae.printStackTrace();
		} catch (InvalidKeySpecException ikse) {
			log.error("invalid key spec for PBEWITHSHAANDTWOFISH-CBC");
			ikse.printStackTrace();
		}

		// This is our secret key. We could just save this to a file instead of
		// regenerating it
		// each time it is needed. But that file cannot be on the device (too
		// insecure). It could
		// be secure if we kept it on a server accessible through https.
		byte[] skAsByteArray = sk.getEncoded();
		// Log.d("",
		// "skAsByteArray=" + skAsByteArray.length + ","
		// + Base64Encoder.encode(skAsByteArray));
		skforAES = new SecretKeySpec(skAsByteArray, "AES");

		IV = new IvParameterSpec(iv);

	}

	public AES(String centerId, String channel, String appid, String appkey) throws Exception {		
		try {
			char[] key = null; 	
			
			String keys = PropertiesReader.getProperty("properties.properties", "KEY_"+centerId+"_"+channel);
			
			key = keys.toCharArray();
			
//			if(centerId.equals("00087100")){
//				if(centerId.equals("00087100")&&channel.equals("10")){//&&appid.equals("yondervisionapp10")
//					key = KEY_00087100_10;
//				}else if(centerId.equals("00087100")&&channel.equals("20")){//&&appid.equals("yondervisionweixin20")
//					key = KEY_00087100_20;
//				}else if(centerId.equals("00087100")&&channel.equals("30")){//&&appid.equals("yondervisionwebsite30")
//					key = KEY_00087100_30;
//				}else if(centerId.equals("00087100")&&channel.equals("40")){//&&appid.equals("yondervisionwebservice40")
//					key = KEY_00087100_40;
//				}else if(centerId.equals("00087100")&&channel.equals("50")){//&&appid.equals("yondervisionselfservice50")
//					key = KEY_00087100_50;
//				}else if(centerId.equals("00087100")&&channel.equals("60")){//
//					key = KEY_00087100_60;
//				}else if(centerId.equals("00087100")&&channel.equals("70")){//&&appid.equals("yondervisionsms70")
//					key = KEY_00087100_70;
//				}else if(centerId.equals("00087100")&&channel.equals("80")){//&&appid.equals("yondervisionmicroblog80")
//					key = KEY_00087100_80;
//				}
//			}else if(centerId.equals("00073300")){
//				if(centerId.equals("00073300")&&channel.equals("10")){//&&appid.equals("yondervisionapp10")
//					key = KEY_00073300_10;
//				}else if(centerId.equals("00073300")&&channel.equals("20")){//&&appid.equals("yondervisionweixin20")
//					key = KEY_00073300_20;
//				}else if(centerId.equals("00073300")&&channel.equals("30")){//&&appid.equals("yondervisionwebsite30")
//					key = KEY_00073300_30;
//				}else if(centerId.equals("00073300")&&channel.equals("40")){//&&appid.equals("yondervisionwebservice40")
//					key = KEY_00073300_40;
//				}else if(centerId.equals("00073300")&&channel.equals("50")){//&&appid.equals("yondervisionselfservice50")
//					key = KEY_00073300_50;
//				}else if(centerId.equals("00073300")&&channel.equals("60")){//
//					key = KEY_00073300_60;
//				}else if(centerId.equals("00073300")&&channel.equals("70")){//&&appid.equals("yondervisionsms70")
//					key = KEY_00073300_70;
//				}else if(centerId.equals("00073300")&&channel.equals("80")){//&&appid.equals("yondervisionmicroblog80")
//					key = KEY_00073300_80;
//				}
//			}else if(centerId.equals("00066800")){
//				if(centerId.equals("00066800")&&channel.equals("10")){//&&appid.equals("yondervisionapp10")
//					key = KEY_00066800_10;
//				}else if(centerId.equals("00066800")&&channel.equals("20")){//&&appid.equals("yondervisionweixin20")
//					key = KEY_00066800_20;
//				}else if(centerId.equals("00066800")&&channel.equals("30")){//&&appid.equals("yondervisionwebsite30")
//					key = KEY_00066800_30;
//				}else if(centerId.equals("00066800")&&channel.equals("40")){//&&appid.equals("yondervisionwebservice40")
//					key = KEY_00066800_40;
//				}else if(centerId.equals("00066800")&&channel.equals("50")){//&&appid.equals("yondervisionselfservice50")
//					key = KEY_00066800_50;
//				}else if(centerId.equals("00066800")&&channel.equals("60")){//
//					key = KEY_00066800_60;
//				}else if(centerId.equals("00066800")&&channel.equals("70")){//&&appid.equals("yondervisionsms70")
//					key = KEY_00066800_70;
//				}else if(centerId.equals("00066800")&&channel.equals("80")){//&&appid.equals("yondervisionmicroblog80")
//					key = KEY_00066800_80;
//				}
//			}
			
			PBEKeySpec myKeyspec = new PBEKeySpec(key, salt, HASH_ITERATIONS, KEY_LENGTH);
			keyfactory = SecretKeyFactory.getInstance(KEY_GENERATION_ALG);
			sk = keyfactory.generateSecret(myKeyspec);

		} catch (NoSuchAlgorithmException nsae) {
			log.error("no key factory support for PBEWITHSHAANDTWOFISH-CBC");
			nsae.printStackTrace();
		} catch (InvalidKeySpecException ikse) {
			log.error("invalid key spec for PBEWITHSHAANDTWOFISH-CBC");
			ikse.printStackTrace();
		}
		byte[] skAsByteArray = sk.getEncoded();
		skforAES = new SecretKeySpec(skAsByteArray, "AES");
		IV = new IvParameterSpec(iv);
	}
	
	
	public String encrypt(byte[] plaintext) throws CenterRuntimeException{
		try{
			byte[] ciphertext = encrypt(CIPHERMODEPADDING, skforAES, IV, plaintext);
			String base64_ciphertext = Base64Encoder.encode(ciphertext);
			return base64_ciphertext;
		}catch (Exception e){
			throw new CenterRuntimeException(Constants.ERROR_999999,"中心前置服务加密处理异常");
		}		
	}

	public String decrypt(String ciphertext_base64) throws CenterRuntimeException{
		try{
			byte[] s = Base64Decoder.decodeToBytes(ciphertext_base64);
			String decrypted = new String(decrypt(CIPHERMODEPADDING, skforAES, IV, s),"UTF-8");
			return decrypted;
		}catch (Exception e){
			throw new CenterRuntimeException(Constants.ERROR_999999,"中心前置服务解密处理异常");
		}
	}

	// Use this method if you want to add the padding manually
	// AES deals with messages in blocks of 16 bytes.
	// This method looks at the length of the message, and adds bytes at the end
	// so that the entire message is a multiple of 16 bytes.
	// the padding is a series of bytes, each set to the total bytes added (a
	// number in range 1..16).
	private byte[] addPadding(byte[] plain) {
		byte plainpad[] = null;
		int shortage = 16 - (plain.length % 16);
		// if already an exact multiple of 16, need to add another block of 16
		// bytes
		if (shortage == 0)
			shortage = 16;

		// reallocate array bigger to be exact multiple, adding shortage bits.
		plainpad = new byte[plain.length + shortage];
		for (int i = 0; i < plain.length; i++) {
			plainpad[i] = plain[i];
		}
		for (int i = plain.length; i < plain.length + shortage; i++) {
			plainpad[i] = (byte) shortage;
		}
		return plainpad;
	}

	// Use this method if you want to remove the padding manually
	// This method removes the padding bytes
	private byte[] dropPadding(byte[] plainpad) {
		byte plain[] = null;
		int drop = plainpad[plainpad.length - 1]; // last byte gives number of
													// bytes to drop

		// reallocate array smaller, dropping the pad bytes.
		plain = new byte[plainpad.length - drop];
		for (int i = 0; i < plain.length; i++) {
			plain[i] = plainpad[i];
			plainpad[i] = 0; // don't keep a copy of the decrypt
		}
		return plain;
	}

	private byte[] encrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] msg) throws Exception{
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.ENCRYPT_MODE, sk, IV);
			return c.doFinal(msg);
		} catch (NoSuchAlgorithmException nsae) {
			log.error("no cipher getinstance support for " + cmp);
			nsae.printStackTrace();
			throw nsae;
		} catch (NoSuchPaddingException nspe) {
			log.error("no cipher getinstance support for padding " + cmp);
			nspe.printStackTrace();
			throw nspe;
		} catch (InvalidKeyException e) {
			log.error("invalid key exception");
			e.printStackTrace();
			throw e;
		} catch (InvalidAlgorithmParameterException e) {
			log.error("invalid algorithm parameter exception");
			e.printStackTrace();
			throw e;
		} catch (IllegalBlockSizeException e) {
			log.error("illegal block size exception");
			e.printStackTrace();
			throw e;
		} catch (BadPaddingException e) {
			log.error("bad padding exception");
			e.printStackTrace();
			throw e;
		}
	}

	private byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] ciphertext) throws Exception{
		try {
			Cipher c = Cipher.getInstance(cmp);
			c.init(Cipher.DECRYPT_MODE, sk, IV);
			return c.doFinal(ciphertext);
		} catch (NoSuchAlgorithmException nsae) {
			log.error("no cipher getinstance support for " + cmp);
			nsae.printStackTrace();
			throw nsae;
		} catch (NoSuchPaddingException nspe) {
			log.error("no cipher getinstance support for padding " + cmp);
			nspe.printStackTrace();
			throw nspe;
		} catch (InvalidKeyException e) {
			log.error("invalid key exception");
			e.printStackTrace();
			throw e;
		} catch (InvalidAlgorithmParameterException e) {
			log.error("invalid algorithm parameter exception");
			e.printStackTrace();
			throw e;
		} catch (IllegalBlockSizeException e) {
			log.error("illegal block size exception");
			e.printStackTrace();
			throw e;
		} catch (BadPaddingException e) {
			log.error("bad padding exception");
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		AES aes = new AES("00066800","40",null,null);

		//System.out.println("str === " + aes.encrypt("768564".getBytes("UTF-8")));
		System.out.println("d str === " + aes.decrypt("WfIOJkaAYE9SGLKx9vGivA=="));
		System.out.println("d str === " + aes.decrypt("0efQE/C8pp4BLwmuOj8MsCYUpfJSTMgTMOgTwz3ORFg="));
		System.out.println("d str === " + aes.decrypt("f3Klnwb+a15NWwocAP17BLeOAGS6ugenZCUGkefkhmBQUmj/srhJBF0iEjoRNqt0"));
		//System.out.println("str === " + aes.encrypt("130731198302065415".getBytes()));
		//System.out.println("d str === " + aes.decrypt("0TaqNh0kgz2KlNxsPeZ4shaOHowNfJ6RR2IET50MTqk="));
	}
}
