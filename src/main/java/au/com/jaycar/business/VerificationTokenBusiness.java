package au.com.jaycar.business;

import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.domain.VerificationToken;

public interface VerificationTokenBusiness {
	
	VerificationToken createToken(UserInfo userInfo);

}
