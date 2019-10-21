package au.com.jaycar.business.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import au.com.jaycar.business.UserBusiness;
import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.dto.UserDetailsDto;
import au.com.jaycar.mapper.UserInfoMapper;
import au.com.jaycar.repo.UserInfoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserBusinessImpl implements UserBusiness {

	private final UserInfoRepo userInfoRepo;

	private final UserInfoMapper userInfoMapper;

	@Override
	public List<UserDetailsDto> listAllUsers() {
		List<UserInfo> userInfos = userInfoRepo.findAll();
		List<UserDetailsDto> listAllUsers = userInfoMapper.toUserDtoList(userInfos);
		log.info("{}", listAllUsers);
		return listAllUsers;
	}

	@Override
	public UserDetailsDto findUser(final String email) {
		Optional<UserInfo> optionalUserInfo = userInfoRepo.findByEmail(email);
		UserInfo userInfo = optionalUserInfo.orElseThrow(RuntimeException::new);
		return userInfoMapper.toUserDto(userInfo);
	}

	@Override
	public void deleteUser(final String email) {
		Optional<UserInfo> optionalUserInfo = userInfoRepo.findByEmail(email);
		UserInfo userInfo = optionalUserInfo.orElseThrow(RuntimeException::new);
		userInfoRepo.delete(userInfo);
	}

	@Override
	public void updateUser(final String email, UserDetailsDto user) {
		Optional<UserInfo> optionalUserInfo = userInfoRepo.findByEmail(email);
		UserInfo userInfo = optionalUserInfo.orElseThrow(RuntimeException::new);

		UserInfo userInfo2 = userInfoMapper.toUserEntity(user);

		if (userInfo2.getUserEnabled() != null)
			userInfo.setUserEnabled(userInfo2.getUserEnabled());
		if (userInfo2.getPassword() != null)
			userInfo.setPassword(userInfo2.getPassword());
		if (userInfo2.getUserName() != null)
			userInfo.setUserName(userInfo2.getUserName());

		userInfoRepo.save(userInfo);
	}

}
