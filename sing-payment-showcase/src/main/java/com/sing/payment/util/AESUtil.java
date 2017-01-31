package com.sing.payment.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESUtil {

	private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

	public static boolean isAES =true;// Constant.AES.ISAES;
	
	public static String SALT = "0102030405060708";

	public static String encrypt(String sSrc,String sKey) throws Exception {

		if (!isAES) {
			return sSrc;
		}
		if (sKey == null) {
			return null;
		}
		// AES-128-CBC key length  should be 16
		if (sKey.length() != 16) {
			return null;
		}
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(SALT.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));

		return new BASE64Encoder().encode(encrypted);
	}

	public static String decrypt(String sSrc,String sKey) throws Exception {
		if (!isAES) {
			return sSrc;
		}
		if (sKey == null) {
			return null;
		}
		if (sKey.length() != 16) {
			return null;
		}
		byte[] raw = sKey.getBytes("ASCII");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(SALT.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		byte[] encrypted1 = new BASE64Decoder ().decodeBuffer(sSrc);
		
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);
		return originalString;
	}



	

}
