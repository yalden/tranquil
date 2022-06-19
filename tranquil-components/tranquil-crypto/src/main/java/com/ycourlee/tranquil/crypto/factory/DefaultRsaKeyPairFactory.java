package com.ycourlee.tranquil.crypto.factory;

import com.ycourlee.tranquil.crypto.Algorithms;
import com.ycourlee.tranquil.crypto.BCJcaJceHelperHolder;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * @author yooonn
 * @date 2021.11.15
 */
public class DefaultRsaKeyPairFactory extends BCJcaJceHelperHolder implements Factory<KeyPair, Integer> {

    public KeyPair keyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator rsa = helper.createKeyPairGenerator(Algorithms.RSA.name());
        rsa.initialize(keySize);
        return rsa.generateKeyPair();
    }

    @Override
    public KeyPair generate(Integer obj) throws Exception {
        return keyPair(((Number) obj).intValue());
    }
}
