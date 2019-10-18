package au.com.jaycar.dto;

import lombok.Data;

@Data
public class UserDetailsDto {
	
	private String userName;

	private String email;

	private String password;
	
	private String confirmPassword;

	private Boolean userEnabled;
}
