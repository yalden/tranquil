package com.ycourlee.tranquil.crypto.annotation;

import java.lang.annotation.*;

/**
 * 声明一个元素为明文。用于类的属性上，被声明的属性将在加解密执行时，被处理为明文
 *
 * @author yooonn
 * @date 2021.11.12
 * @see Ciphertext
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Plaintext {

    /**
     * 被标注属性的分类。Beta func
     *
     * @return 默认TEXT
     */
    Category category() default Category.TEXT;

    /**
     * 被标注属性明文化时，指定所用的算法
     *
     * @return 默认AES
     */
    String algorithm() default "AES";

    /**
     * 被标注属性使用的密钥组
     *
     * @return 默认空字符串
     */
    String keyGroup() default "";

    /**
     * 被标注属性参与哪组明文化处理。{@link Crypto#enableGroups()}
     *
     * @return 默认空字符串
     */
    String group() default "";

    /**
     * 需要将被标注属性做url安全型明文化
     *
     * @return 默认非url安全型
     */
    boolean urlSafely() default false;
}
