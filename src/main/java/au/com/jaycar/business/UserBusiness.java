package au.com.jaycar.business;

import java.util.List;

import au.com.jaycar.dto.UserDetailsDto;

public interface UserBusiness {
	
	List<UserDetailsDto> listAllUsers();

}
