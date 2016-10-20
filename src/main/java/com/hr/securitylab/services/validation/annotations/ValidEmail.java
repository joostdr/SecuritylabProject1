package com.hr.securitylab.services.validation.annotations;

import com.hr.securitylab.services.validation.classes.ValidEmailValidator;
import com.hr.securitylab.services.validation.classes.ValidPinValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Joost on 16-10-2016.
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidPinValidator.class)
@Documented
public @interface ValidEmail {

    String message() default "Pin is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}