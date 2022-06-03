package com.ycourlee.tranquil.autoconfiguration.crypto;

import com.ycourlee.tranquil.crypto.aes.AesCryptoExecutor;
import com.ycourlee.tranquil.crypto.exception.CryptoException;
import org.springframework.lang.Nullable;

import java.util.Map;

/**
 * @author yoooonn
 * @date 2021.12.15
 */
public class AesCrypto {

    @Nullable
    private Map<String, CryptoProperties.AesPropInternal> propGroup;

    private AesCryptoExecutor aesCryptoExecutor;

    public AesCrypto(CryptoProperties cryptoProperties, AesCryptoExecutor aesCryptoExecutor) {
        this.propGroup = cryptoProperties.getCipher().getAes().getGroup();
        this.aesCryptoExecutor = aesCryptoExecutor;
    }

    public String ciphertext(String plaintext) {
        return aesCryptoExecutor.ciphertext(plaintext);
    }

    public String ciphertext(String plaintext, boolean urlSafely) {
        return aesCryptoExecutor.ciphertext(plaintext, urlSafely);
    }

    public String ciphertext(String plaintext, String group) {
        return ciphertext(plaintext, group, false);
    }

    public String ciphertext(String plaintext, String group, boolean urlSafely) {
        CryptoProperties.AesPropInternal prop = retrieveGroupProp(group);
        return aesCryptoExecutor.ciphertext(plaintext, prop.getRawKey(),
                AesCryptoExecutor.transformOf(prop.getAlgMode(), prop.getAlgPadding()), urlSafely);
    }

    public String plaintext(String ciphertext) {
        return aesCryptoExecutor.plaintext(ciphertext);
    }

    public String plaintext(String ciphertext, boolean urlSafely) {
        return aesCryptoExecutor.plaintext(ciphertext, urlSafely);
    }

    public String plaintext(String ciphertext, String group) {
        return plaintext(ciphertext, group, false);
    }

    public String plaintext(String ciphertext, String group, boolean urlSafely) {
        CryptoProperties.AesPropInternal prop = retrieveGroupProp(group);
        return aesCryptoExecutor.plaintext(ciphertext, prop.getRawKey(),
                AesCryptoExecutor.transformOf(prop.getAlgMode(), prop.getAlgPadding()), urlSafely);
    }

    private CryptoProperties.AesPropInternal retrieveGroupProp(String group) {
        CryptoProperties.AesPropInternal prop;
        if (propGroup == null || (prop = propGroup.get(group)) == null) {
            throw new CryptoException("Not found aes key group: " + group);
        }
        return prop;
    }
}
