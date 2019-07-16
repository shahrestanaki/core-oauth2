package com.config;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyDateValidator.class)
@Documented
public @interface ValidDate {

    String message() default "date not validate";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class MyDateValidator implements ConstraintValidator<ValidDate, String> {
    public void initialize(ValidDate constraint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        String str = "\\d{4}[-]\\d{2}[-]\\d{2}";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}