package au.com.jaycar.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.jaycar.domain.UserInfo;

public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {

	Optional<UserInfo> findByEmail(String email);

//	Optional<UserInfo> findByUserName(String userName);

}
