package com.ycourlee.tranquil.jwt;

import java.io.Serializable;

public class Claim implements Serializable {

    private static final long serialVersionUID = -895875540581785581L;

    /**
     * 不同于jwt定义的payload，只是代表claims中的一个key,value
     * 但payload这个词太有代表性了不是吗
     */
    private String payLoad;

    /**
     * 签发者(JWT令牌此项有值)
     */
    private String issuer = "issuer";

    /**
     * 主题
     */
    private String subject = "subject";

    /**
     * 接收方(JWT令牌此项有值)
     */
    private String audience = "audience";

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }
}
