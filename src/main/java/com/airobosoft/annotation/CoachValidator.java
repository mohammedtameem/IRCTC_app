package com.airobosoft.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CoachValidator implements ConstraintValidator<ValidCoach,Integer> {

    @Override
    public boolean isValid(Integer value,
                           ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        return value >= 5 && value <=100;
    }
}
