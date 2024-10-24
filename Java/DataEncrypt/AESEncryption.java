package Java.DataEncrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *  对称加密算法
 */

public class AESEncryption {
    public static String encrypt(String plaintext, SecretKey secretKey){
        try {
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = aesCipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "NoSuchAlgorithmException";
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return "NoSuchPaddingException";
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return "InvalidKeyException";
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return "IllegalBlockSizeException";
        } catch (BadPaddingException e) {
            e.printStackTrace();
            return "BadPaddingException";
        }
    }

    public static String decrypt(String ciphertext, SecretKey secretKey){
        try {
            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = aesCipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(decryptedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "NoSuchAlgorithmException";
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return "NoSuchPaddingException";
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return "InvalidKeyException";
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return "IllegalBlockSizeException";
        } catch (BadPaddingException e) {
            e.printStackTrace();
            return "BadPaddingException";
        }
    }

    public static SecretKey generateAESKey() throws NoSuchAlgorithmException{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String plaintext = "Hello, AES! ";
        SecretKey aesKey = generateAESKey();
        String encrypt = encrypt(plaintext, aesKey);
        System.out.println("Encrypted: " + encrypt);
        String decrypt = decrypt(encrypt, aesKey);
        System.out.println("Decrypted: " + decrypt);
    }
}
