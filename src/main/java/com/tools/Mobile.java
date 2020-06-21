package com.tools;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@Pattern(regexp = Mobile.PATTERN, message = "فرمت شماره موبایل قابل پذیرش نمی باشد")
public @interface Mobile {
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String PATTERN = "^[0][9][0-9\\-\\(\\)\\ ]\\d{8}+$";
}
