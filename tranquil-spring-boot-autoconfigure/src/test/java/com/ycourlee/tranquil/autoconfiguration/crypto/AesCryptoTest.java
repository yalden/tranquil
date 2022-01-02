package com.ycourlee.tranquil.autoconfiguration.crypto;

import com.ycourlee.tranquil.core.exception.AssertException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yongjiang
 * @date 2021.12.16
 */
public class AesCryptoTest extends ApplicationContextRunningConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AesCryptoTest.class);

    @Test
    @SuppressWarnings({"ConstantConditions"})
    void nullCiphertextTest() {
        assertThrows(AssertException.class, () -> aesCrypto.ciphertext(null));
    }

    @Test
    void emptyCiphertextTest() {
        String ciphertext = aesCrypto.ciphertext("");
        log.info("ciphertext: {}", ciphertext);
        assertNotNull(ciphertext);
        assertTrue(ciphertext.length() > 0);
    }

    @Test
    void ciphertextTest() {
        String ciphertext = aesCrypto.ciphertext("hello", "1");
        log.info("ciphertext: {}", ciphertext);
        String plaintext = aesCrypto.plaintext(ciphertext, "1");
        assertEquals(plaintext, "hello");
    }
}
