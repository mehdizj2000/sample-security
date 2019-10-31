package au.com.jaycar.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import au.com.jaycar.dto.UserDetailsDto;
import au.com.jaycar.validation.annotation.EmailMatcher;

public class EmailMatcherValidator implements ConstraintValidator<EmailMatcher, Object> {

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		UserDetailsDto detailsDto = (UserDetailsDto) value;
		return StringUtils.compare(detailsDto.getEmail(), detailsDto.getEmailConfirmation()) == 0;
	}

}
