package com.ycourlee.tranquil.jwt;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Date;

/**
 * only one claim in String style with name
 * <p>
 * Jwt通常作为工具类出现，这里整合Spring，可以动态配置签名密钥、token存活时间等等，在Apollo等配置中心的加入下，也能
 * 满足运行时改变签名密钥，满足安全性需求。现在是一个半插件的状态，后面会补全做成插件
 * <p>
 * 其实claims本质是一个map，只不过map中有自己的保留字（iat，exp，nbf等），通过保留字来约定签名方式，签名key的存放问题等等。
 * 可以向其中add很多key、value对。但只增加一个key，把value存成一个结构体，更高效，无论是解码时装箱，
 * 还是编码时拆箱，直接操作json。
 * <p>
 * 这就是只采用一个自定义claim的原因。
 * @author yooonn
 */
public class JwtHelper {

    private static final Logger log = LoggerFactory.getLogger(JwtHelper.class);

    private SignatureAlgorithm algorithm = SignatureAlgorithm.ES256;

    private String base64EncodedSecretKey = "how are you";

    private String claimName  = "payload";

    private String leadingSymbol;

    public JwtHelper() {
    }

    public JwtHelper(SignatureAlgorithm algorithm, String base64EncodedSecretKey, String claimName, String leadingSymbol) {
        this.algorithm = algorithm;
        this.base64EncodedSecretKey = base64EncodedSecretKey;
        this.claimName = claimName;
        this.leadingSymbol = leadingSymbol;
    }

    public String issue(Claim claim) {
        return issue(claim, Duration.ofDays(7));
    }

    public String issue(Claim claim, Duration aliveTime) {
        Object payload = claim.getPayLoad();
        String issuer = claim.getIssuer();
        String subject = claim.getSubject();
        String audience = claim.getAudience();
        Date current = new Date();
        return issue(JSON.toJSONString(payload), audience, subject, issuer, current,
                new Date(current.getTime() + aliveTime.toMillis()));
    }

    public String issue(String dynamicClaim, String audience, String subject, String issuer, Date issueAt, Date expiration) {
        return wrapJwt(baseJwt(audience, subject, issuer)
                .claim(claimName, dynamicClaim)
                .setIssuedAt(issueAt)
                .setExpiration(expiration)
                .compact());
    }

    /**
     * @param token json web token
     * @return payload parsed from Jwt.
     */
    public String parse(String token) {
        if (token == null || "".equals(token)) {
            throw new IllegalArgumentException("token must have length: " + token);
        }
        Claims claims = claimsOf(token);
        return ((String) claims.get(claimName));
    }

    /**
     * Claims of given Json web token
     * @param token json web token
     * @return claims
     */
    public Claims claimsOf(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(base64EncodedSecretKey)
                .build()
                .parseClaimsJws(unwrapJwt(token))
                .getBody();
    }

    private String wrapJwt(String jwt) {
        log.debug("[ jwt ] issued an jwt: {}", jwt);
        return leadingSymbol == null ? jwt : leadingSymbol + jwt;
    }

    private String unwrapJwt(String jwt) {
        if (leadingSymbol == null || leadingSymbol.isEmpty()) {
            return jwt;
        }
        if (!jwt.startsWith(leadingSymbol)) {
            throw new IllegalStateException("jwt style does not meet expectations. [ " + jwt + " ]");
        }
        return jwt.substring(leadingSymbol.length());
    }

    private JwtBuilder baseJwt(String audience, String subject, String issuer) {
        return Jwts.builder()
                .setAudience(audience)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(algorithm, base64EncodedSecretKey);
    }
}
