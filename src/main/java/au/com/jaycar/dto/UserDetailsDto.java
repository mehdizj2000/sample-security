package au.com.jaycar.dto;

import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class UserDetailsDto {
	
	private Long id;
	
	private String userName;

	private String email;

	private String password;
	
	private ZonedDateTime timeCreated;
	
	private String confirmPassword;

	private Boolean userEnabled;
}
