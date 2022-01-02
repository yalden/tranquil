package com.ycourlee.tranquil.annotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 验证目标元素的空格特征，当Level为
 * <ul>
 *     <li>NORMAL：只检查前导空格和后导空格，如果存在空格，不通过</li>
 *     <li>ALL：检查目标元素的每一个字符，如果存在空格，不通过</li>
 * </ul>
 *
 * @author yongjiang
 * @date 2021.09.10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = NoSpace.NoSpaceValidator.class)
public @interface NoSpace {

    Level level() default Level.NORMAL;

    String message() default "there must be no space";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    enum Level {
        /**
         * Check leading and trailing char ' '
         */
        NORMAL,
        /**
         * Check all char ' ' in string
         */
        ALL
    }

    class NoSpaceValidator implements ConstraintValidator<NoSpace, CharSequence> {

        private Level level;

        @Override
        public void initialize(NoSpace constraintAnnotation) {
            level = constraintAnnotation.level();
        }

        @Override
        public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
            if (value == null || value.length() == 0) {
                return true;
            }
            final char space = ' ';
            if (value.charAt(0) == space || value.charAt(value.length() - 1) == space) {
                return false;
            }
            if (level.equals(Level.ALL)) {
                for (int i = 0; i < value.length(); i++) {
                    if (value.charAt(i) == space) {
                        return false;
                    }
                }
            }
            return true;
        }
    }
}
