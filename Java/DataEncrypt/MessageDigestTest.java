package Java.DataEncrypt;

import java.security.MessageDigest;

/**
 *  消息摘要算法（Message Digest）
    MD5：产生128位的散列值，已被认为不再安全
    SHA-1: 产生160位的散列值，已被认为不再安全
    SHA-256、SHA-384、SHA-512: 分别产生256、384、512位的散列值
 */

public class MessageDigestTest {
    public static String hashString(String input, String algorithm){
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = digest.digest(input.getBytes());
            return bytesToHex(hashedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String bytesToHex(byte[] bytes){
        StringBuilder result = new StringBuilder();
        for(byte b : bytes){
            result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String input = "Hello World!";
        System.out.println("MD5: " + hashString(input, "MD5"));
        System.out.println("SHA-256: " + hashString(input, "SHA-256"));
    }
}