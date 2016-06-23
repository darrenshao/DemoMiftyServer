/*
 * Copyright 2016 The Twohalf Project
 * 
 * Shenzhen Twohalf Technology Co. Ltd.
 *
 * http://www.twohalf.com
 */
package club.jmint.demo.mifty.utils;

import club.jmint.crossing.utils.Security;

public class Tools {
	public final static int MOBILE_NUMBER_LEN = 11;
	
	public static String getMaskedMobile(String mobile){
		String masked=null;
		if (mobile==null) return mobile;
		
		if (mobile.length()>=MOBILE_NUMBER_LEN){
			masked = mobile.substring(0,3) + "****" + mobile.substring(7);
			return masked;
		} 
		return mobile;
	}
	
	public static String getEncryptPassword(String plain){
		return Security.crossingSign(plain, "", "MD5");
	}
	
}

