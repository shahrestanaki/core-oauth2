package com.tools;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER  })
@Retention(RUNTIME)
@Constraint(validatedBy={})
@Documented
@Pattern(regexp=PersianOnly.PATTERN,message="{validate.input.NotFarsiCharacters}")
public @interface PersianOnly   {
	String message() default "{org.my.constraints.persianOnly}";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
	String PATTERN = "^([\\u0600-\\u06FF,\\u0590-\\u05FF, ]*)$";
	//String PATTERN = "^[\\x{0621}-\\x{0628}\\x{062A}-\\x{063A}\\x{0641}-\\x{0642}\\x{0644}-\\x{0648}\\x{064E}-\\x{0651}\\x{0655}\\x{067E}\\x{0686}\\x{0698}\\x{06A9}\\x{06AF}\\x{06BE}\\x{06CC}\\x{0020}]+$";
//String PATTERN = "/^[\\u0600-\\u06FF\\|(|\\)|\\.|\\-|\\,\\s]+$/";

}

