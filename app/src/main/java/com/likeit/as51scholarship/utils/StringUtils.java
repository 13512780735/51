package com.likeit.as51scholarship.utils;


import java.security.MessageDigest;

/**
 * TODO 字符串处理 
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-5-23  下午5:56:13
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class StringUtils {
	
	/**
     * 字符串不为 null 而且不为 "" 时返回 true
     */
    public static boolean notBlank(String str) {
        return str == null || "".equals(str.trim()) || str.equals("null") ? false : true;
    }
    
    
    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     * 
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }
    
    /***
     * 截取字符串
     * 
     * @param start
     *            从那里开始，0算起
     * @param num
     *            截取多少个
     * @param str
     *            截取的字符串
     * @return
     */
    public static String getSubString(int start, int num, String str) {
        if (str == null) {
            return "";
        }
        int leng = str.length();
        if (start < 0) {
            start = 0;
        }
        if (start > leng) {
            start = leng;
        }
        if (num < 0) {
            num = 1;
        }
        int end = start + num;
        if (end > leng) {
            end = leng;
        }
        return str.substring(start, end);
    }
    
    public static String getMD5(byte[] bytes) {
		String strTemp = "";
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(bytes);
			return toHexString(algorithm.digest(), "");
		} catch (Exception e) {
			strTemp = "";
		}

		return strTemp;
	}
    
    public static String toHexString(byte[] bytes, String separator) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			if (Integer.toHexString(0xFF & b).length() == 1)
				hexString.append("0").append(Integer.toHexString(0xFF & b));
			else
				hexString.append(Integer.toHexString(0xFF & b));
		}
		return hexString.toString();
	}
    
}
