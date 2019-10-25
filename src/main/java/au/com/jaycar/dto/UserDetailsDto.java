package au.com.jaycar.dto;

import java.time.ZonedDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

import lombok.Data;

@Data
public class UserDetailsDto {
	
	private Long id;
	
	@NotBlank
	private String userName;

	@Email
	private String email;

	@NotBlank
	private String password;
	
	private ZonedDateTime timeCreated;
	
	@NotBlank
	private String confirmPassword;

	@Null
	private Boolean userEnabled;
}
