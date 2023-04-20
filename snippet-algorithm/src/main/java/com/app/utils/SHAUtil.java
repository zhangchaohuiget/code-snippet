package com.app.utils;

import java.security.MessageDigest;

/**
 * SHA-1 加密 不可逆
 */
public class SHAUtil {

    private SHAUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * SHA-1 加密 生成40位SHA码
     *
     * @param inStr 待加密字符串
     * @return 返回40位SHA码
     * @throws Exception
     */
    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
