package com.ycourlee.tranquil.crypto;

import com.ycourlee.tranquil.core.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;
import javax.crypto.SecretKey;

/**
 * @author yooonn
 * @date 2021.12.10
 */
@Setter
@Getter
@ToString
public class CipherParam {

    private Integer mode;

    private SecretKey secretKey;

    private String transform;

    private String cbcModeIv;

    @Nullable
    public CipherAlgMode extractMode() {
        if (StringUtil.isEmpty(transform)) {
            return null;
        }
        String[] split = transform.split(StringUtil.SLASH, 3);
        if (split.length == 3) {
            return CipherAlgMode.valueOf(split[1].toUpperCase());
        }
        return null;
    }
}
