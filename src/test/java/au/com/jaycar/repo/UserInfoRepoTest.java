package au.com.jaycar.repo;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.dto.UserDetailsDto;
import au.com.jaycar.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoRepoTest {

	@Autowired
	private UserInfoRepo userInfoRepo;

	@Autowired
	private UserInfoMapper userInfoMapper;

	@Test
	public void findAllUsersTest() {
		List<UserInfo> infos = userInfoRepo.findAll();
		assertNotNull(infos);
	}

	@Test
	public void findAllUsersAndSaveTest() {

		List<UserInfo> infos = new ArrayList<UserInfo>();
		for (int i = 0; i < 20; i++) {
			String someString = RandomStringUtils.randomAlphabetic(8);
			UserInfo userInfo = new UserInfo();
			userInfo.setEmail(someString + "@shaga.com");
			userInfo.setPassword("password");
			userInfo.setUserEnabled(false);
			userInfo.setUserName(someString);
			infos.add(userInfo);
		}
		List<UserInfo> savedUI = userInfoRepo.saveAll(infos);
		assertThat(savedUI, Matchers.notNullValue());
		log.info("savedUI.toString(): {}", savedUI);

	}

	@Test
	public void savePartialUserTest() {
		Optional<UserInfo> userInfo = userInfoRepo.findByEmail("IoOFrdyv@shaga.com");
		if (userInfo.isPresent()) {
			UserInfo info = userInfo.get();
			assertNotNull(info);
			log.info("wkerufksdfjgksdjfhgksfjg: {}", info);

			info.setPassword("sjfbhjshfjshjsdfhjhf");
			info.setUserEnabled(true);
			UserInfo savedInfo = userInfoRepo.save(info);
			log.info("UserInfo savedInfo: {}", savedInfo);
		}

	}

	@Test
	public void testUpdateUser() {

		String email = "aFRrtUWY@shaga.com";

//		'5', 'aFRrtUWY@shaga.com', 'password', '2019-10-21 00:03:24', NULL, '0', 'aFRrtUWY'

		UserDetailsDto detailsDto = new UserDetailsDto();
		detailsDto.setId(5l);
		detailsDto.setEmail("aFRrtUWY@shaga.com");
		detailsDto.setUserName("Mehdi");
		detailsDto.setPassword("dfgdfgdfgdfg");
		detailsDto.setConfirmPassword("garage");
//		detailsDto.setUserEnabled(false);

		UserInfo info = userInfoMapper.toUserEntity(detailsDto);

		Optional<UserInfo> userOpt = userInfoRepo.findByEmail(email);

		UserInfo info2 = userOpt.get();

		if (info.getUserEnabled() != null)
			info2.setUserEnabled(info.getUserEnabled());
		if (info.getPassword() != null)
			info2.setPassword(info.getPassword());
		if(info.getUserName() != null)
			info2.setUserName(info.getUserName());

		UserInfo piyaz = userInfoRepo.save(info2);

		System.out.println(piyaz);

	}

	@Test
	@Ignore
	public void testFindByUserName() {
		fail("Not yet implemented");
	}

}
