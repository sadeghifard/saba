package com.saba.sci.utile;

import org.apache.tomcat.util.codec.binary.Base64;

public class Base64Coder {

	public static String encode(String raw) {
		String encodedString = Base64.encodeBase64String(raw.getBytes());
		return encodedString;
	}
	
	public static String decode(String encodedString) {
		byte[] decodedBytes = Base64.decodeBase64(encodedString);
		String decodedString = new String(decodedBytes);
		return decodedString;
	}
	
	public static void main(String[] args) {
		System.out.println(encode("reza-reza"));
	}
}
