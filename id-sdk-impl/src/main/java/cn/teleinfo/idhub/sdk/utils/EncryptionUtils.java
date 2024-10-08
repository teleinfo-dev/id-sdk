package cn.teleinfo.idhub.sdk.utils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * @author bluepoint
 * @title EncryptionUtils
 * Rsa 加密 签名
 * @since 2018-09-17
 */
public abstract class EncryptionUtils {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(2048);
        return keyPairGen.generateKeyPair();
    }

    /**
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, PrivateKey privateKey) throws Exception {
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);

        return Base64.getEncoder().encodeToString(signature.sign());
    }

    /**
     * @param data
     * @param pubKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, PublicKey pubKey, String sign) throws Exception {
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(decodeBASE64(sign));
    }

    public static byte[] decodeBASE64(String source) {
        return Base64.getMimeDecoder().decode(source);
    }

    public static String encodeBASE64(byte[] data) {
        return Base64.getMimeEncoder().encodeToString(data);
    }

    /**
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByKey(byte[] data, Key key) throws Exception {

        // 对数据解密
        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    /**
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByKey(byte[] data, Key key) throws Exception {

        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    /**
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByKey(byte[] data, Key key, String algorithm) throws Exception {

        // 对数据解密
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    /**
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByKey(byte[] data, Key key, String algorithm) throws Exception {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    /**
     * 获取加密后的密码
     * @param oriPasswordStr
     * @param publicKeyPem
     * @return
     * @throws Exception
     */
    public static String getEncryptionPassword(String oriPasswordStr, String publicKeyPem) throws Exception {
        byte[] oriPasswordBytes = oriPasswordStr.getBytes(StandardCharsets.UTF_8);
        PublicKey publicKey = KeyConverter.fromX509Pem(publicKeyPem);
        byte[] bytes = EncryptionUtils.encryptByKey(oriPasswordBytes, publicKey, "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING");
        return Base64.getEncoder().encodeToString(bytes);
    }

}
