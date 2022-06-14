package com.saba.sci.utile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SabaPasswordEncoder implements PasswordEncoder {
	
	private static final char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String byteArray2Hex(byte[] bytes) {
	    StringBuffer sb = new StringBuffer(bytes.length * 2);
	    for(final byte b : bytes) {
	        sb.append(hex[(b & 0xF0) >> 4]);
	        sb.append(hex[b & 0x0F]);
	    }
	    return sb.toString();
	}
	
	@Override
	public  String encode(CharSequence rawPassword) {
		 MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		    messageDigest.update(((String) rawPassword).getBytes());
		    return byteArray2Hex(messageDigest.digest());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String encodedRawPassword = encode(rawPassword);
		return (encodedRawPassword.strip()).equals(encodedPassword.strip()) ? true : false;
	}
	
}
