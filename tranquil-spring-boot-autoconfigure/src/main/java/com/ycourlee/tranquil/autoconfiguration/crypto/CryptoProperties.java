package com.ycourlee.tranquil.autoconfiguration.crypto;

import com.ycourlee.tranquil.crypto.CipherAlgMode;
import com.ycourlee.tranquil.crypto.CipherAlgPadding;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author yoooonn
 * @date 2021.11.12
 */
@Setter
@Getter
@ConfigurationProperties(prefix = CryptoProperties.PREFIX)
public class CryptoProperties {

    public static final String PREFIX = "tranquil.crypto";

    private Boolean enable;

    /**
     * Cipher Algorithm.
     */
    private CipherAlg cipher = new CipherAlg();

    /**
     * Signature Algorithm.
     */
    private SignatureAlg signature = new SignatureAlg();

    @Setter
    @Getter
    public static class AesProp {

        /**
         * Default prop.
         */
        private AesPropInternal defaultProp = new AesPropInternal();

        /**
         * Prop groups.
         */
        private Map<String, AesPropInternal> group;
    }

    @Setter
    @Getter
    public static class AesPropInternal {

        /**
         * Default value is for quickstart, replace it when used in product
         */
        private String           rawKey     = "iamfineiamfineiamfineiamfineiamf";
        /**
         * Cipher Algorithm Mode. Default ECB
         */
        private CipherAlgMode    algMode    = CipherAlgMode.ECB;
        /**
         * Cipher Algorithm Padding. Default PKCS5Padding
         */
        private CipherAlgPadding algPadding = CipherAlgPadding.PKCS5Padding;
        /**
         * Initial Vector When algMode is CBC.
         */
        private String           cbcModeIv;
    }

    @Setter
    @Getter
    public static class RsaProp {

        private RsaPropInternal defaultProp = new RsaPropInternal();

        private Map<String, RsaPropInternal> group;
    }

    @Setter
    @Getter
    public static class RsaPropInternal {

        private String publicKey;
        private String privateKey;
    }

    @Setter
    @Getter
    public static class CipherAlg {

        private AesProp aes = new AesProp();
    }

    @Setter
    @Getter
    public static class SignatureAlg {

        private RsaProp rsa = new RsaProp();
    }
}
