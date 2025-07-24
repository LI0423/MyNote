package Java.DataEncrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static String encrypt(String context){
        MessageDigest instance = null;
        try {
            // 获取MessageDigest实例，指定加密算法为MD5
            instance = MessageDigest.getInstance("MD5");
            // 重置MessageDigest实例的状态
            instance.reset();
             // 将输入内容转换为UTF-8编码字节数组，并更新到MessageDigest实例中
            instance.update(context.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = instance.digest();

        StringBuilder stringBuilder = new StringBuilder();
        for(byte b: digest){
            /***
             * % 是占位符，表示是一个格式化指令。
             * 0 是在输出结果前填充前导0，表示输出长度至少是两个字符
             * 2 表示每个字节被转换为2位的16进制字符
             * x 表示使用16进制来输出结果的字符
             */
            stringBuilder.append(String.format("%02x", b));
        }
        String result = stringBuilder.toString();
        return result;
    }

    public static void main(String[] args) {
        String context = "abcdefghijklmnopqrstuvwxyz";
        System.out.println(encrypt(context));
    }
}
