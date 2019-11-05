package au.com.jaycar.business.impl;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.jaycar.business.TokenBusiness;
import au.com.jaycar.domain.ResetPasswordToken;
import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.dto.UserDetailsDto;
import au.com.jaycar.exception.TokenVerificationException;
import au.com.jaycar.mapper.UserInfoMapper;
import au.com.jaycar.repo.ResetPasswordTokenRepo;
import au.com.jaycar.repo.UserInfoRepo;

@Component
public class ResetPasswordTokenBusinessImpl implements TokenBusiness<ResetPasswordToken> {

	private ResetPasswordTokenRepo resetPasswordTokenRepo;

	private UserInfoRepo userInfoRepo;

	private UserInfoMapper userInfoMapper;

	@Override
	public ResetPasswordToken createToken(UserInfo userInfo) {
		ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
		resetPasswordToken.setToken(UUID.randomUUID().toString());
		resetPasswordToken.setUserInfo(userInfo);
		return getResetPasswordTokenRepo().save(resetPasswordToken);
	}

	@Override
	public UserDetailsDto verifyToken(String token) {
		Optional<ResetPasswordToken> resetPasswordTokenOpt = getResetPasswordTokenRepo().findByToken(token);
		resetPasswordTokenOpt.orElseThrow(TokenVerificationException::new);

		ResetPasswordToken resetPasswordToken = resetPasswordTokenOpt.get();

		ZonedDateTime zonedDateTime = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
		boolean notExpired = resetPasswordToken.getExpiryDate().withZoneSameInstant(ZoneId.of("UTC"))
				.isAfter(zonedDateTime);

		if (notExpired) {

			UserInfo userInfo = resetPasswordToken.getUserInfo();

			userInfo.setUserEnabled(Boolean.TRUE);

			return userInfoMapper.toUserDto(userInfoRepo.save(userInfo));

		} else
			throw new TokenVerificationException();

	}

	public UserInfoRepo getUserInfoRepo() {
		return userInfoRepo;
	}

	@Autowired
	public void setUserInfoRepo(UserInfoRepo userInfoRepo) {
		this.userInfoRepo = userInfoRepo;
	}

	public UserInfoMapper getUserInfoMapper() {
		return userInfoMapper;
	}

	@Autowired
	public void setUserInfoMapper(UserInfoMapper userInfoMapper) {
		this.userInfoMapper = userInfoMapper;
	}

	public ResetPasswordTokenRepo getResetPasswordTokenRepo() {
		return resetPasswordTokenRepo;
	}

	public void setResetPasswordTokenRepo(ResetPasswordTokenRepo resetPasswordTokenRepo) {
		this.resetPasswordTokenRepo = resetPasswordTokenRepo;
	}

}
