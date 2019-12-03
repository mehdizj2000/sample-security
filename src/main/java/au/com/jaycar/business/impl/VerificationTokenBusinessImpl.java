package au.com.jaycar.business.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.jaycar.business.TokenBusiness;
import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.domain.VerificationToken;
import au.com.jaycar.dto.UserDetailsDto;
import au.com.jaycar.exception.TokenVerificationException;
import au.com.jaycar.mapper.UserInfoMapper;
import au.com.jaycar.repo.UserInfoRepo;
import au.com.jaycar.repo.VerificationTokenRepo;

@Component
public class VerificationTokenBusinessImpl implements TokenBusiness<VerificationToken> {

	private VerificationTokenRepo verificationTokenRepo;

	private UserInfoRepo userInfoRepo;

	private UserInfoMapper userInfoMapper;

	@Override
	public VerificationToken createToken(UserInfo userInfo) {
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(UUID.randomUUID().toString());
		verificationToken.setUserInfo(userInfo);
		return verificationTokenRepo.save(verificationToken);
	}

	public UserInfoMapper getUserInfoMapper() {
		return userInfoMapper;
	}

	public UserInfoRepo getUserInfoRepo() {
		return userInfoRepo;
	}

	public VerificationTokenRepo getVerificationTokenRepo() {
		return verificationTokenRepo;
	}

	@Autowired
	public void setUserInfoMapper(UserInfoMapper userInfoMapper) {
		this.userInfoMapper = userInfoMapper;
	}

	@Autowired
	public void setUserInfoRepo(UserInfoRepo userInfoRepo) {
		this.userInfoRepo = userInfoRepo;
	}

	@Autowired
	public void setVerificationTokenRepo(VerificationTokenRepo verificationTokenRepo) {
		this.verificationTokenRepo = verificationTokenRepo;
	}

	@Override
	public UserDetailsDto verifyToken(String token) {
		Optional<VerificationToken> verificationTokenOpt = verificationTokenRepo.findByToken(token);
		verificationTokenOpt.orElseThrow(TokenVerificationException::new);

		VerificationToken verificationToken = verificationTokenOpt.get();

		ZonedDateTime zonedDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
		boolean notExpired = verificationToken.getExpiryDate().withZoneSameInstant(ZoneId.of("UTC"))
				.isAfter(zonedDateTime);

		if (notExpired) {

			UserInfo userInfo = verificationToken.getUserInfo();

			userInfo.setUserEnabled(Boolean.TRUE);
			
			verificationTokenRepo.delete(verificationToken);

			return userInfoMapper.toUserDto(userInfoRepo.save(userInfo));

		} else
			throw new TokenVerificationException();

	}

}
