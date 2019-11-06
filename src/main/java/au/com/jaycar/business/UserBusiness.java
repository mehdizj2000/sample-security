package au.com.jaycar.business;

import java.util.List;

import au.com.jaycar.dto.UserDetailsDto;

public interface UserBusiness {

	void deleteUser(final Long id);

	UserDetailsDto findUser(final Long id);

	List<UserDetailsDto> listAllUsers();

	UserDetailsDto saveUser(UserDetailsDto user);
	
	UserDetailsDto updateUserReq(long id, String email);

}
