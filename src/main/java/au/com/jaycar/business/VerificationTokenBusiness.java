package au.com.jaycar.business;

import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.domain.VerificationToken;
import au.com.jaycar.dto.UserDetailsDto;

public interface VerificationTokenBusiness {
	
	VerificationToken createToken(UserInfo userInfo);
	
	UserDetailsDto verifyToken(String token);

}
