package com.tools;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*An annotation type is defined using the @interface keyword. All attributes of an annotation type
are declared in a method-like manner. The specification of the Bean Validation API demands, that
any constraint annotation defines*/
@Target( { METHOD, FIELD, ANNOTATION_TYPE ,CONSTRUCTOR,PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy={})
@Documented
@Pattern(regexp=INumber.PATTERN,message="{فرمت عدد وارد شده قابل پذیرش نمی باشد.}")
public @interface INumber   {
	/*an attribute message that returns the default key for creating error messages in case the
	constraint is violated*/
	String message() default "{com.iansar.clubapp.validation.constraints.Number}";
	/*an attribute groups that allows the specification of validation groups, to which this constraint
	belongs (see Section 2.3, �Validating groups�). This must default to an empty array of type
	Class<?>.*/
	Class<?>[] groups() default {};
	/* an attribute payload that can be used by clients of the Bean Validation API to asign custom
	payload objects to a constraint. This attribute is not used by the API itself.*/
	Class<? extends Payload>[] payload() default {};
	
	String PATTERN = "^[0-9\\,]*$";
}
