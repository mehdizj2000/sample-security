package au.com.jaycar.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import au.com.jaycar.validation.EmailMatcherValidator;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailMatcherValidator.class)
@Documented
public @interface EmailMatcher {

	String message() default "Emails do not match";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
