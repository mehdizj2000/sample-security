package au.com.jaycar.business;

import java.util.List;

import au.com.jaycar.dto.UserDetailsDto;

public interface UserBusiness {
	
	List<UserDetailsDto> listAllUsers();
	
	UserDetailsDto findUser(final String email);
	
	void deleteUser(final String email);
	
	void updateUser(final String email, UserDetailsDto user);

}
