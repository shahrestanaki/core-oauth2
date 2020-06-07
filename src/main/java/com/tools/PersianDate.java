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
@Pattern(regexp = PersianDate.PATTERN, message = "فرمت تاریخ فارسی معتبر نمی باشد")
public @interface PersianDate {
    String message() default "{ui.general.error.PersianDateFormat}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String PATTERN = "^((1[3-4])(\\d{2})\\/(0[1-9]|1[012])\\/(0[1-9]|[12][0-9]|3[01]))*$";
}