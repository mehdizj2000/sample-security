package au.com.jaycar.business.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.jaycar.business.VerificationTokenBusiness;
import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.domain.VerificationToken;
import au.com.jaycar.repo.VerificationTokenRepo;

@Component
public class VerificationTokenBusinessImpl implements VerificationTokenBusiness {

	private VerificationTokenRepo verificationTokenRepo;

	@Override
	public VerificationToken createToken(UserInfo userInfo) {
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(UUID.randomUUID().toString());
		verificationToken.setUserInfo(userInfo);
		return verificationTokenRepo.save(verificationToken);
	}

	public VerificationTokenRepo getVerificationTokenRepo() {
		return verificationTokenRepo;
	}

	@Autowired
	public void setVerificationTokenRepo(VerificationTokenRepo verificationTokenRepo) {
		this.verificationTokenRepo = verificationTokenRepo;
	}

}
