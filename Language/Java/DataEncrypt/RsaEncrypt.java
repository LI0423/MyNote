package Java.DataEncrypt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 *  非对称加密算法
 */

public class RsaEncrypt {
    public static String encrypt(String plaintext, PublicKey publicKey){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            return new String(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String decrypt(String ciphertext, PrivateKey privateKey){
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(ciphertext.getBytes());
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static byte[] sign(String data, PrivateKey privateKey){
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            return signature.sign();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verify(String data, byte[] signature, PublicKey publicKey){
        try {
            Signature signature2 = Signature.getInstance("SHA256withRSA");
            signature2.initVerify(publicKey);
            signature2.update(data.getBytes());
            return signature2.verify(signature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String plaintext = "Hello RSA!";
        KeyPair rsaKeyPair = generateRSAKeyPair();
        PublicKey publicKey = rsaKeyPair.getPublic();
        PrivateKey privateKey = rsaKeyPair.getPrivate();

        String encryptedText = encrypt(plaintext, publicKey);
        System.out.println("Encrypted: " + encryptedText);
        String decryptedText = decrypt(encryptedText, privateKey);
        System.out.println("Decrypted: " + decryptedText);

        byte[] sign = sign(plaintext, privateKey);
        System.out.println("Signature: " + Base64.getEncoder().encodeToString(sign));

        boolean verify = verify(plaintext, sign, publicKey);
        System.out.println("Signature Verifed: " + verify);
    }
}
