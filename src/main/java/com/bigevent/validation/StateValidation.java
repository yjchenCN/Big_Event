package com.bigevent.validation;

import com.bigevent.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    @Override
    public void initialize(State constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // 提供校验规则
        if (s == null)
            return false;
        if ("已发布".equals(s) || "草稿".equals(s))
            return true;
        return false;
    }
}
