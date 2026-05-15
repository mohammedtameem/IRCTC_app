package com.airobosoft.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CoachValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCoach {

    String message() default "Invalid coach count";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
