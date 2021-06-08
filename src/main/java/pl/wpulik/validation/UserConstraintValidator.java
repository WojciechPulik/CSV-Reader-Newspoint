package pl.wpulik.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

@Component
public class UserConstraintValidator implements ConstraintValidator<ValidPhoneNumber, Integer> {
	

	@Override
	public boolean isValid(Integer phoneNumber, ConstraintValidatorContext context) {	
		if(phoneNumber==null)
			return true;
		else
			return phoneNumber >= 100000000 && phoneNumber <= 999999999;
	}

}
