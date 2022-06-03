package com.ycourlee.tranquil.autoconfiguration.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

/**
 * @author yoooonn
 */
@Setter
@Getter
@ConfigurationProperties(prefix = JwtProperties.PREFIX)
public class JwtProperties {

    public static final String PREFIX = "tranquil.jwt";

    private static final Logger log = LoggerFactory.getLogger(JwtProperties.class);

    /**
     * jwt issuer.
     */
    private String issuer = "admin";
    /**
     * subject of issuing jwt.
     */
    private String subject = "subject";
    /**
     * audience of jwt.
     */
    private String audience = "audience";
    /**
     * Claim key
     */
    private String claimName = "payload";
    /**
     * sign secret key.
     */
    @Getter(AccessLevel.NONE)
    private String secretKey = "#iam fine!?#iam fine!?#iam fine!?#iam fine!?#iam fine!?#iam fine!?#iam fine!?";

    @Setter(AccessLevel.NONE)
    private byte[] base64EncodedSecretKey = Base64.getEncoder().encode(secretKey.getBytes(StandardCharsets.UTF_8));

    /**
     * Signature algorithm used in jwt.
     */
    private SignatureAlgorithm alg = SignatureAlgorithm.HS256;

    /**
     * prefix symbol.
     */
    private String   leadingSymbol = "";
    /**
     * default expiration time in seconds.
     */
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration aliveTime     = Duration.ofSeconds(60L * 60 * 24 * 30);

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        this.base64EncodedSecretKey = Base64.getEncoder().encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
