package au.com.jaycar.business;

import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.dto.UserDetailsDto;

public interface TokenBusiness<T> {

	T createToken(UserInfo userInfo);

	UserDetailsDto verifyToken(String token);

}
