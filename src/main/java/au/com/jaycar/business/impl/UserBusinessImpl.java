package au.com.jaycar.business.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import au.com.jaycar.business.UserBusiness;
import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.dto.UserDetailsDto;
import au.com.jaycar.event.RegistrationEvent;
import au.com.jaycar.event.ResetPasswordEvent;
import au.com.jaycar.exception.GeneralBusinessException;
import au.com.jaycar.mapper.UserInfoMapper;
import au.com.jaycar.repo.UserInfoRepo;
import au.com.jaycar.repo.VerificationTokenRepo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserBusinessImpl implements UserBusiness {

	private UserInfoRepo userInfoRepo;

	private UserInfoMapper userInfoMapper;

	private ApplicationEventPublisher eventPublisher;

	private VerificationTokenRepo verificationTokenRepo;

	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void deleteUser(final Long id) {
		userInfoRepo.deleteById(id);
	}

	@Override
	public UserDetailsDto findUser(final Long id) {
		Optional<UserInfo> optionalUserInfo = getUserInfoRepo().findById(id);
		UserInfo userInfo = optionalUserInfo.orElseThrow(RuntimeException::new);
		return getUserInfoMapper().toUserDto(userInfo);
	}

	public ApplicationEventPublisher getEventPublisher() {
		return eventPublisher;
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

	@Override
	public List<UserDetailsDto> listAllUsers() {
		List<UserInfo> userInfos = getUserInfoRepo().findAll();
		List<UserDetailsDto> listAllUsers = getUserInfoMapper().toUserDtoList(userInfos);
		log.info("{}", listAllUsers);
		return listAllUsers;
	}

	@Override
	public UserDetailsDto updateUserReq(long id, String email) {
		Optional<UserInfo> userInfoOpt = userInfoRepo.findById(id);
		UserInfo userInfo = userInfoOpt.orElseThrow(GeneralBusinessException::new);
		if (!userInfo.getEmail().contentEquals(email))
			throw new GeneralBusinessException();

		String url = "http://127.0.0.1:8992";
		eventPublisher.publishEvent(new ResetPasswordEvent(userInfo, url));

		return userInfoMapper.toUserDto(userInfo);
	}

	@Override
	public UserDetailsDto saveUser(UserDetailsDto user) {
		if (user.getId() != null && user.getId().intValue() != 0) {
			Optional<UserInfo> optionalUserInfo = getUserInfoRepo().findById(user.getId());
			UserInfo userInfo = optionalUserInfo.orElseThrow(RuntimeException::new);

			UserInfo userInfo2 = getUserInfoMapper().toUserEntity(user);

			if (userInfo2.getUserEnabled() != null)
				userInfo.setUserEnabled(userInfo2.getUserEnabled());
			if (userInfo2.getPassword() != null)
				userInfo.setPassword(passwordEncoder.encode(userInfo2.getPassword()));
			if (userInfo2.getUserName() != null)
				userInfo.setUserName(userInfo2.getUserName());
			UserInfo info = getUserInfoRepo().save(userInfo);
			return getUserInfoMapper().toUserDto(info);
		} else {
			UserInfo userInfo2 = getUserInfoMapper().toUserEntity(user);

			userInfo2.setPassword(passwordEncoder.encode(userInfo2.getPassword()));
			UserInfo info = getUserInfoRepo().save(userInfo2);
			String url = "http://127.0.0.1:8992";
			eventPublisher.publishEvent(new RegistrationEvent(info, url));
			return getUserInfoMapper().toUserDto(info);
		}
	}

	@Autowired
	public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
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

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

}
