package au.com.jaycar.business;

import java.util.List;

import au.com.jaycar.dto.UserDetailsDto;

public interface UserBusiness {
	
	List<UserDetailsDto> listAllUsers();
	
	UserDetailsDto findUser(final Long id);
	
	void deleteUser(final Long id);
	
	UserDetailsDto saveUser(UserDetailsDto user);

}
