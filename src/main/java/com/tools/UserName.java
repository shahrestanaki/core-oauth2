package com.tools;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@Pattern(regexp = UserName.PATTERN, message = "نام کاربری باید شامل حروف انگليسي/ اعداد/ کاراکترهاي _. باشد")
public @interface UserName {
    String message() default "{org.my.constraints.persianOnly}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String PATTERN = "^([a-zA-Z0-9-_.]*)$";
}

