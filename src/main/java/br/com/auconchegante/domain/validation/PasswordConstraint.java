package br.com.auconchegante.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Password must contain at least 1 uppercase letter and 1 special character.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
