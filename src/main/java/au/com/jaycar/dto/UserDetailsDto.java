package au.com.jaycar.dto;

import java.time.ZonedDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import au.com.jaycar.validation.annotation.EmailMatcher;
import au.com.jaycar.validation.annotation.PasswordMatcher;
import lombok.Data;

@Data
@PasswordMatcher
@EmailMatcher
public class UserDetailsDto {

	private Long id;

	@NotBlank
	private String userName;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@Email
	@NotBlank
	private String email;

	@Email
	@NotBlank
	private String emailConfirmation;

	@NotBlank
	private String password;

	private ZonedDateTime timeCreated;

	@NotBlank
	private String confirmPassword;

	@Null
	private Boolean userEnabled;
}
