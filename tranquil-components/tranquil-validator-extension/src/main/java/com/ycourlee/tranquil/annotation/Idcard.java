package com.ycourlee.tranquil.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.regex.Pattern;

/**
 * 验证目标元素是否是手机号，默认中国大陆手机号码段：[130, 199]
 *
 * @author yooonn
 * @date 2021.09.10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = Idcard.Validator.class)
public @interface Idcard {

    String regex() default "";

    String message() default "there must be an idcard number of China Mainland.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator extends AbstractSimplePatternValidator<Idcard> {

        /**
         * Consider that idcard has 15 or 18 length and special digit.
         */
        private static final Pattern REGEX = Pattern.compile("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");

        private Pattern regex;

        @Override
        public void initialize(Idcard constraintAnnotation) {
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
