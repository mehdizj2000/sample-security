package au.com.jaycar.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import au.com.jaycar.dto.UserDetailsDto;
import au.com.jaycar.validation.annotation.PasswordMatcher;

public class PasswordMatcherValidator implements ConstraintValidator<PasswordMatcher, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		UserDetailsDto detailsDto = (UserDetailsDto) value;
		return StringUtils.compare(detailsDto.getPassword(), detailsDto.getConfirmPassword()) == 0;
	}

}
