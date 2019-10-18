package au.com.jaycar.business.impl;

import java.util.List;

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
		
		log.info("{}",listAllUsers);
		return listAllUsers;
		
	}


}
