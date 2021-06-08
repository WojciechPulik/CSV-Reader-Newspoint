package pl.wpulik.validation;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;



@Constraint(validatedBy =  UserConstraintValidator.class)
@Target({FIELD, METHOD, CONSTRUCTOR, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface ValidPhoneNumber {
	String message() default "Number should have 9 digits or be null. User not persisted in database.";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
}
