package br.com.auconchegante.auth.domain.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile(".*[A-Z].*");
    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return false;

        boolean hasUppercase = UPPERCASE_PATTERN.matcher(value).matches();
        boolean hasSpecialChar = SPECIAL_CHAR_PATTERN.matcher(value).matches();

        return hasUppercase && hasSpecialChar;
    }
}
