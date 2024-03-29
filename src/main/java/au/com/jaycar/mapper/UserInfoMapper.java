package au.com.jaycar.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.dto.UserDetailsDto;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

	UserDetailsDto toUserDto(UserInfo userInfo);

	List<UserDetailsDto> toUserDtoList(List<UserInfo> userInfos);

	UserInfo toUserEntity(UserDetailsDto detailsDto);

}
