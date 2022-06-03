package com.ycourlee.tranquil.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.regex.Pattern;

/**
 * 验证目标元素是否是手机号，默认中国大陆手机号码段：[130, 199]
 *
 * @author yoooonn
 * @date 2021.09.10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = PhoneNumber.Validator.class)
public @interface PhoneNumber {

    String regex() default "";

    String message() default "there must be a phone number of China Mainland.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator extends AbstractSimplePatternValidator<PhoneNumber> {

        private static final Pattern REGEX = Pattern.compile("^[1]([3-9])[0-9]{9}$");

        private Pattern regex;

        @Override
        public void initialize(PhoneNumber constraintAnnotation) {
            if (constraintAnnotation.regex() != null && !constraintAnnotation.regex().isEmpty()) {
                regex = Pattern.compile(constraintAnnotation.regex());
            } else {
                regex = REGEX;
            }
        }

        @Override
        protected Pattern withPattern() {
            return regex;
        }
    }
}
