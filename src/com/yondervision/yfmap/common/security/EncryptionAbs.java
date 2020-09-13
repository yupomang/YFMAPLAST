package com.yondervision.yfmap.common.security;

import java.io.UnsupportedEncodingException;

public abstract class EncryptionAbs {
	public abstract byte[] encryption(String msg,byte[] key)throws Exception;
	public abstract String dencryption(byte[] msg,byte[] key)throws Exception;
}
