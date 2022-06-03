package com.ycourlee.tranquil.crypto.annotation;

import java.lang.annotation.*;

/**
 * 声明一个元素为密文，用于类的属性上。被声明的属性将在加解密执行时，被处理为密文
 *
 * @author yoooonn
 * @date 2021.11.12
 * @see Plaintext
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ciphertext {

    Category category() default Category.TEXT;

    String algorithm() default "AES";

    String keyGroup() default "";

    String group() default "";

    boolean urlSafely() default false;
}
