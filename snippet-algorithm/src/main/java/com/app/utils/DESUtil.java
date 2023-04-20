package com.app.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES对称加密
 * @Author zhangchaohui
 * @Date 2022/2/23 11:08
 */
public class DESUtil {

    private DESUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void main(String[] args) throws Exception {
        String key = initKey();
        System.out.println(String.format("生成秘钥：%s", key));
        String data = "哈哈哈";
        String encrypt = encrypt(data, key);
        System.out.println(String.format("加密后结果：%s", encrypt));
        String decrypt = decrypt(encrypt, key);
        System.out.println(String.format("解密后结果：%s", decrypt));
    }

    /**
     * 生成密钥
     */
    public static String initKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);
        SecretKey secretKey = keyGen.generateKey();
        byte[] encoded = secretKey.getEncoded();
        return byteArr2HexStr(encoded);
    }

    /**
     * DES 加密
     */
    public static String encrypt(String data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(hexStr2ByteArr(key), "DES");

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherBytes = cipher.doFinal(data.getBytes());
        return byteArr2HexStr(cipherBytes).toLowerCase();
    }

    /**
     * DES 解密
     */
    public static String decrypt(String data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(hexStr2ByteArr(key), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] plainBytes = cipher.doFinal(hexStr2ByteArr(data));
        return new String(plainBytes);
    }

    private static String byteArr2HexStr(byte[] byteArr) {
        if (byteArr == null) {
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArr.length * 2];
        for (int j = 0; j < byteArr.length; j++) {
            int v = byteArr[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static byte[] hexStr2ByteArr(String hexStr) {
        if (hexStr == null) {
            return null;
        }
        if (hexStr.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[hexStr.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            String subStr = hexStr.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }
}
