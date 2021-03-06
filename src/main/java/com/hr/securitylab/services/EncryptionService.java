package com.hr.securitylab.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hr.securitylab.database.DatabaseFactory;
import com.hr.securitylab.database.entities.hibernate.Polling;
import com.hr.securitylab.database.entities.hibernate.Product;
import com.hr.securitylab.database.entities.hibernate.User;
import com.hr.securitylab.database.entities.rest.PollingRest;
import com.hr.securitylab.database.entities.rest.Response;
import com.hr.securitylab.exceptions.KeyNotSetException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.util.encoders.Base64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Class which handles AES 128 ECB encryption
 */
public class EncryptionService {

    private static SecretKey key;
    private static byte[] encodedKey;

    /**
     * Retrieves encryptionkey from the hibernate for the corresponding device
     * Saves the encryptionkey in the variable key
     *
     * @param request
     * @return
     */
    public void getKey(HttpServletRequest request) {
        String productId = request.getHeader("productid");
        String keyString = DatabaseFactory.getProductService().findById(productId).getEncryption_key();
        encodedKey = keyString.getBytes(Charset.forName("UTF-8"));
        key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public static String decrypt(String cipherText) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, KeyNotSetException, InvalidKeyException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance("AES");
        if (key == null) throw new KeyNotSetException("Key is not set");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)), "UTF-8");
    }

    public static String encrypt(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("AES");///CBC/PKCS5Padding
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(Charset.forName("UTF-8")));
        return Hex.encodeHexString(encrypted);
    }

    public static String encryptPollingModel(PollingRest pollingRest) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        return EncryptionService.encrypt(mapper.writeValueAsString(pollingRest));
    }

    public static String computeHash(String content) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        SecretKeySpec keySpec = new SecretKeySpec(encodedKey, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(keySpec);
        return new String (Base64.getEncoder().encode(mac.doFinal(content.getBytes())), "UTF-8");
    }


}
