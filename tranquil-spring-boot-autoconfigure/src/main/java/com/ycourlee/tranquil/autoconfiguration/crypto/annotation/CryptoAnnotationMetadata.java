package com.ycourlee.tranquil.autoconfiguration.crypto.annotation;

import com.ycourlee.tranquil.crypto.Algorithms;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yongjiang
 * @date 2021.12.17
 */
@Setter
@Getter
public class CryptoAnnotationMetadata {

    private Category category;

    private Algorithms algorithm;

    private String keyGroup;

    private String group;

    private Boolean urlSafely;

    private Boolean wasCipher;

    public static CryptoAnnotationMetadata from(Ciphertext ciphertext) {
        return getCryptoAnnotationMetadata(ciphertext.category(), ciphertext.algorithm(), ciphertext.keyGroup(),
                ciphertext.group(), ciphertext.urlSafely(), true);
    }

    public static CryptoAnnotationMetadata from(Plaintext plaintext) {
        return getCryptoAnnotationMetadata(plaintext.category(), plaintext.algorithm(), plaintext.keyGroup(),
                plaintext.group(), plaintext.urlSafely(), false);
    }

    private static CryptoAnnotationMetadata getCryptoAnnotationMetadata(Category category, Algorithms algorithm, String keyGroup,
                                                                        String group, boolean urlSafely, boolean wasCipher) {
        CryptoAnnotationMetadata metadata = new CryptoAnnotationMetadata();
        metadata.setCategory(category);
        metadata.setAlgorithm(algorithm);
        metadata.setKeyGroup(keyGroup);
        metadata.setGroup(group);
        metadata.setUrlSafely(urlSafely);
        metadata.setWasCipher(wasCipher);
        return metadata;
    }
}
