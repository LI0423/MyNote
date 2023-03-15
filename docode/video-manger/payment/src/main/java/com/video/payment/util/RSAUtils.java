package com.video.payment.util;

import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class RSAUtils {

    private static final String KEY_ALGORITHM = "PKCS12";

    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    private static final String CIPHER_TYPE = "X.509";

    private static final String ENCRYPT_TYPE = "RSA";

    public static String sign(String privateKeyStr, String text)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        PrivateKey privateKey = PemUtil
                .loadPrivateKey(new ByteArrayInputStream(privateKeyStr.getBytes(StandardCharsets.UTF_8)));

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);

            signature.update(text.getBytes(StandardCharsets.UTF_8));

            byte[] result = signature.sign();

            return Base64.encodeBase64String(result);
    }

}
