package com.tools;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD, ANNOTATION_TYPE ,CONSTRUCTOR,PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy={})
@Documented
@Pattern(regexp=OnlyLetter.PATTERN,message="{validate.input.badCharacters}")

public @interface OnlyLetter   {
	String message() default "{org.my.constraints.OnlyLetter}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

	String PATTERN = "^[a-zA-Z\\ \\'\\u0600-\\u06FF,\\u0590-\\u05FF,.(),،_:/\"0-9\\-\n]+$";
}
