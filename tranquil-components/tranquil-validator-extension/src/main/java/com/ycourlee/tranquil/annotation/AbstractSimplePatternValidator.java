package com.ycourlee.tranquil.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yongjiang
 * @date 2021.11.15
 */
public abstract class AbstractSimplePatternValidator<T extends Annotation> implements ConstraintValidator<T, CharSequence> {

    protected abstract Pattern withPattern();

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            return true;
        }
        Matcher matcher = withPattern().matcher(value);
        return matcher.matches();
    }
}