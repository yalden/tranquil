package com.ycourlee.tranquil.crypto.aes;

import com.ycourlee.tranquil.core.util.Assert;
import com.ycourlee.tranquil.core.util.StringUtil;
import com.ycourlee.tranquil.crypto.Algorithms;
import com.ycourlee.tranquil.crypto.CipherAlgMode;
import com.ycourlee.tranquil.crypto.CipherAlgPadding;
import com.ycourlee.tranquil.crypto.CipherParam;
import com.ycourlee.tranquil.crypto.exception.CryptoException;
import com.ycourlee.tranquil.crypto.factory.DefaultAesSecretKeyFactory;
import com.ycourlee.tranquil.crypto.factory.DefaultCipherFactory;
import com.ycourlee.tranquil.crypto.factory.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author yongjiang
 * @date 2021.02.03
 */
public class AesCryptoExecutor {

    /**
     * 默认密钥
     */
    public final String           rawKey;
    /**
     * 算法/加密模式/补码填充方式
     */
    private      String           defaultTransform;
    private      CipherAlgMode    defaultCipherAlgMode;
    private      CipherAlgPadding defaultCipherAlgPadding;
    private      String           defaultCipherAlgCbcModeIv;

    private static final Logger log = LoggerFactory.getLogger(AesCryptoExecutor.class);

    private Factory<Cipher, CipherParam> cipherFactory;
    private Factory<SecretKey, String>   aesSecretKeyFactory;

    public AesCryptoExecutor() {
        this(new DefaultCipherFactory(), new DefaultAesSecretKeyFactory(),
                "iamfineiamfineiamfineiamfineiamf",
                CipherAlgMode.ECB, CipherAlgPadding.PKCS5Padding, null);
    }

    public AesCryptoExecutor(String defaultRawKey, CipherAlgMode defaultCipherAlgMode,
                             CipherAlgPadding defaultCipherAlgPadding,
                             String defaultCipherAlgCbcModeIv) {
        this(new DefaultCipherFactory(), new DefaultAesSecretKeyFactory(),
                defaultRawKey, defaultCipherAlgMode, defaultCipherAlgPadding, defaultCipherAlgCbcModeIv);
    }

    public AesCryptoExecutor(Factory<Cipher, CipherParam> cipherFactory,
                             Factory<SecretKey, String> aesSecretKeyFactory,
                             String defaultRawKey, CipherAlgMode defaultCipherAlgMode,
                             CipherAlgPadding defaultCipherAlgPadding,
                             String defaultCipherAlgCbcModeIv) {
        this.cipherFactory = cipherFactory;
        this.aesSecretKeyFactory = aesSecretKeyFactory;
        this.rawKey = defaultRawKey;
        this.defaultCipherAlgMode = defaultCipherAlgMode;
        this.defaultCipherAlgPadding = defaultCipherAlgPadding;
        this.defaultCipherAlgCbcModeIv = defaultCipherAlgCbcModeIv;
        this.defaultTransform = transformOf(defaultCipherAlgMode, defaultCipherAlgPadding);
    }

    public String ciphertext(String plaintext) {
        return ciphertext(plaintext, rawKey, defaultTransform, false);
    }

    public String ciphertext(String plaintext, String rawKey) {
        return ciphertext(plaintext, rawKey, defaultTransform, false);
    }

    public String ciphertext(String plaintext, AesCryptoParam param) {
        return ciphertext(plaintext, param.getRawKey(), param.getTransform(), false);
    }

    public String ciphertext(String plaintext, boolean urlSafely) {
        return ciphertext(plaintext, rawKey, defaultTransform, urlSafely);
    }

    public String ciphertext(String plaintext, String rawKey, boolean urlSafely) {
        return ciphertext(plaintext, rawKey, defaultTransform, urlSafely);
    }

    public String ciphertext(String plaintext, AesCryptoParam param, boolean urlSafely) {
        return ciphertext(plaintext, param.getRawKey(), param.getTransform(), urlSafely);
    }

    /**
     * 等效 select to_base64(AES_ENCRYPT(plaintext, rawKey));
     *
     * @param plaintext 明文
     * @param rawKey    密钥
     * @param transform transform
     * @param urlSafely urlSafely
     * @return 密文
     */
    public String ciphertext(String plaintext, String rawKey, String transform, boolean urlSafely) {
        Assert.impossible(plaintext == null);
        byte[] encrypts = encrypt(plaintext.getBytes(StandardCharsets.UTF_8), rawKey, transform);
        return new String(encoder(urlSafely).encode(encrypts), StandardCharsets.UTF_8);
    }

    public String plaintext(String ciphertext) {
        return plaintext(ciphertext, rawKey, defaultTransform, false);
    }

    public String plaintext(String ciphertext, String rawKey) {
        return plaintext(ciphertext, rawKey, defaultTransform, false);
    }

    public String plaintext(String ciphertext, AesCryptoParam param) {
        return plaintext(ciphertext, param.getRawKey(), param.getTransform(), false);
    }

    public String plaintext(String ciphertext, boolean urlSafely) {
        return plaintext(ciphertext, rawKey, defaultTransform, urlSafely);
    }

    public String plaintext(String ciphertext, String rawKey, boolean urlSafely) {
        return plaintext(ciphertext, rawKey, defaultTransform, urlSafely);
    }

    public String plaintext(String ciphertext, AesCryptoParam param, boolean urlSafely) {
        return plaintext(ciphertext, param.getRawKey(), param.getTransform(), urlSafely);
    }

    /**
     * 等效 select AES_DECRYPT(from_base64(ciphertext), rawKey);
     *
     * @param ciphertext 密文
     * @param rawKey     密钥
     * @param transform  transform
     * @param urlSafely  urlSafely
     * @return 明文
     */
    public String plaintext(String ciphertext, String rawKey, String transform, boolean urlSafely) {
        Assert.impossible(ciphertext == null);
        byte[] decrypts = decrypt(decoder(urlSafely).decode(ciphertext.getBytes(StandardCharsets.UTF_8)), rawKey, transform);
        return new String(decrypts);
    }

    /**
     * update()？？
     *
     * @param data 数据
     * @param key  密钥
     * @return 加密后的数据
     */
    public byte[] encrypt(byte[] data, String key) {
        return encrypt(data, key, defaultTransform);
    }

    /**
     * @param data 数据
     * @param key  密钥
     * @return 解密后的数据
     */
    public byte[] decrypt(byte[] data, String key) {
        return decrypt(data, key, defaultTransform);
    }

    /**
     * update()？？
     *
     * @param data      数据
     * @param key       密钥
     * @param transform 算法/加密模式/补码填充方式
     * @return 加密后的数据
     */
    public byte[] encrypt(byte[] data, String key, String transform) {
        return doFinal(data, Cipher.ENCRYPT_MODE, key, transform);
    }

    /**
     * @param data      数据
     * @param key       密钥
     * @param transform 算法/加密模式/补码填充方式
     * @return 解密后的数据
     */
    public byte[] decrypt(byte[] data, String key, String transform) {
        return doFinal(data, Cipher.DECRYPT_MODE, key, transform);
    }

    private byte[] doFinal(byte[] data, Integer mode, String rawKey, String transform) {
        Assert.impossible(data == null);
        try {
            CipherParam cipherParam = new CipherParam();
            cipherParam.setMode(mode);
            cipherParam.setSecretKey(aesSecretKeyFactory.generate(rawKey));
            cipherParam.setTransform(transform);
            cipherParam.setCbcModeIv(defaultCipherAlgCbcModeIv);
            Cipher cipher = cipherFactory.generate(cipherParam);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    private byte[] update(byte[] data, Integer mode, String rawKey, String transform) {
        Assert.impossible(data == null);
        try {
            CipherParam cipherParam = new CipherParam();
            cipherParam.setMode(mode);
            cipherParam.setSecretKey(aesSecretKeyFactory.generate(rawKey));
            cipherParam.setTransform(transform);
            Cipher cipher = cipherFactory.generate(cipherParam);
            return cipher.update(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    private Base64.Decoder decoder(boolean urlSafely) {
        return urlSafely ? Base64.getUrlDecoder() : Base64.getDecoder();
    }

    private Base64.Encoder encoder(boolean urlSafely) {
        return urlSafely ? Base64.getUrlEncoder() : Base64.getEncoder();
    }

    public void setCipherAlgModePadding(CipherAlgMode cipherAlgMode, CipherAlgPadding cipherAlgPadding) {
        this.defaultCipherAlgMode = cipherAlgMode;
        this.defaultCipherAlgPadding = cipherAlgPadding;
        this.defaultTransform = transformOf(cipherAlgMode, cipherAlgPadding);
    }

    public void setCipherAlgCbcModeIv(String cipherAlgCbcModeIv) {
        if (!CipherAlgMode.CBC.equals(defaultCipherAlgMode)) {
            log.warn("Current cipher algorithm mode is not CBC, ignore CBC Mode Iv param.");
            return;
        }
        this.defaultCipherAlgCbcModeIv = cipherAlgCbcModeIv;
    }

    public CipherAlgMode getCipherAlgMode() {
        return defaultCipherAlgMode;
    }

    public CipherAlgPadding getCipherAlgPadding() {
        return defaultCipherAlgPadding;
    }

    public static String transformOf(CipherAlgMode mode, CipherAlgPadding padding) {
        return Algorithms.AES.name() + StringUtil.SLASH + mode.name() + StringUtil.SLASH + padding.name();
    }
}
