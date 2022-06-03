package com.ycourlee.tranquil.autoconfiguration.jwt;

import com.ycourlee.tranquil.jwt.Claim;
import com.ycourlee.tranquil.jwt.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Date;

/**
 * @author yoooonn
 */
public class JwtEnhancer extends JwtHelper {

    private static final Logger log = LoggerFactory.getLogger(JwtEnhancer.class);

    private JwtProperties jwtProperties;

    public JwtEnhancer(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override public String issue(Claim claim) {
        Date current = new Date();
        return super.issue(claim.getPayLoad(), jwtProperties.getAudience(), jwtProperties.getSubject(), jwtProperties.getIssuer(), current,
                new Date(current.getTime() + jwtProperties.getAliveTime().toMillis()));
    }

    @Override public String issue(Claim claim, Duration aliveTime) {
        Date current = new Date();
        return super.issue(claim.getPayLoad(), jwtProperties.getAudience(), jwtProperties.getSubject(), jwtProperties.getIssuer(), current,
                new Date(current.getTime() + aliveTime.toMillis()));
    }

    @Override
    public String issue(String dynamicClaim, String audience, String subject, String issuer, Date issueAt, Date expiration) {
        return super.issue(dynamicClaim, audience, subject, issuer, issueAt, expiration);
    }
}
