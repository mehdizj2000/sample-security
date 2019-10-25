package au.com.jaycar.mapper;

import static org.junit.Assert.assertNotNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;
import org.mapstruct.factory.Mappers;

import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.dto.UserDetailsDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserInfoMapperTest {

	@Test
	public void test() {
		
		UserInfoMapper infoMapper = Mappers.getMapper(UserInfoMapper.class);
		
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail("sjfgjsdfgjhfg@jhcjh.com");
		userInfo.setId(2l);
		userInfo.setPassword("sfgjsghfjksfjkhfg");
		userInfo.setUserEnabled(false);
		userInfo.setUserName("sdrugfjsghfjksgh");
		userInfo.setTimeCreated(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")));
		userInfo.setTimeUpdated(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")));
		
		
		UserDetailsDto detailsDto = infoMapper.toUserDto(userInfo);
		
		assertNotNull(detailsDto);
		
		log.info(detailsDto.toString());
//		fail("Not yet implemented");
	}

}
