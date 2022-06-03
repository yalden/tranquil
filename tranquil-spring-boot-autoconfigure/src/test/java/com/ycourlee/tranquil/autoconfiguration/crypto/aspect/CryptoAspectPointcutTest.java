package com.ycourlee.tranquil.autoconfiguration.crypto.aspect;

import com.ycourlee.tranquil.autoconfiguration.crypto.annotation.*;
import com.ycourlee.tranquil.crypto.annotation.Crypto;
import org.springframework.stereotype.Component;

/**
 * @author yoooonn
 * @date 2021.12.16
 */
@Component
public class CryptoAspectPointcutTest {

    public CiphertextTest willNotBeWeaved() {
        CiphertextTest ciphertextTest = new CiphertextTest();
        ciphertextTest.setHello("hello");
        return ciphertextTest;
    }

    @Crypto
    public CiphertextTest simple(String plaintext) {
        CiphertextTest ciphertextTest = new CiphertextTest();
        ciphertextTest.setHello(plaintext);
        return ciphertextTest;
    }

    @Crypto(enableGroups = {"1"})
    public CiphertextCryptoEnableGroupTest group(String hello, String world) {
        CiphertextCryptoEnableGroupTest test = new CiphertextCryptoEnableGroupTest();
        test.setHello(hello);
        test.setWorld(world);
        return test;
    }

    @Crypto
    public CiphertextKeyGroupTest keyGroup(String hello) {
        CiphertextKeyGroupTest test = new CiphertextKeyGroupTest();
        test.setHello(hello);
        return test;
    }

    @Crypto
    public CiphertextNotAesAlgTest notAesAlg(String hello) {
        CiphertextNotAesAlgTest test = new CiphertextNotAesAlgTest();
        test.setHello(hello);
        return test;
    }

    @Crypto
    public CiphertextNotExistKeyGroupTest notExistKeyGroup(String hello) {
        CiphertextNotExistKeyGroupTest test = new CiphertextNotExistKeyGroupTest();
        test.setHello(hello);
        return test;
    }

    @Crypto
    public CiphertextUrlSafelyTest urlSafely(String hello) {
        CiphertextUrlSafelyTest test = new CiphertextUrlSafelyTest();
        test.setHello(hello);
        return test;
    }

    @Crypto
    public PlaintextTest simplePlaintext(String ciphertext) {
        PlaintextTest plaintextTest = new PlaintextTest();
        plaintextTest.setHello(ciphertext);
        return plaintextTest;
    }

    @Crypto(enableGroups = {"1"})
    public PlaintextCryptoEnableGroupTest groupPlaintext(String hello, String world) {
        PlaintextCryptoEnableGroupTest test = new PlaintextCryptoEnableGroupTest();
        test.setHello(hello);
        test.setWorld(world);
        return test;
    }

    @Crypto
    public PlaintextKeyGroupTest keyGroupPlaintext(String hello) {
        PlaintextKeyGroupTest test = new PlaintextKeyGroupTest();
        test.setHello(hello);
        return test;
    }

    @Crypto
    public PlaintextNotAesAlgTest notAesAlgPlaintext(String hello) {
        PlaintextNotAesAlgTest test = new PlaintextNotAesAlgTest();
        test.setHello(hello);
        return test;
    }

    @Crypto
    public PlaintextNotExistKeyGroupTest notExistKeyGroupPlaintext(String hello) {
        PlaintextNotExistKeyGroupTest test = new PlaintextNotExistKeyGroupTest();
        test.setHello(hello);
        return test;
    }

    @Crypto
    public PlaintextUrlSafelyTest urlSafelyPlaintext(String hello) {
        PlaintextUrlSafelyTest test = new PlaintextUrlSafelyTest();
        test.setHello(hello);
        return test;
    }
}
