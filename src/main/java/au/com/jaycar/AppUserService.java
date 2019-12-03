package au.com.jaycar;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.repo.UserInfoRepo;

@Service
public class AppUserService implements UserDetailsService {
	
	private static String ROLE_USER = "ROLE_USER";

	private UserInfoRepo userInfoRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<UserInfo> userOpt = userInfoRepo.findByEmail(email);
		UserInfo userInfo = userOpt.orElseThrow(() -> new UsernameNotFoundException("no user found with username: " + email));
	
		return new User(userInfo.getEmail(), userInfo.getPassword(), userInfo.getUserEnabled(), true, true, true, getAuthorities(ROLE_USER));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Arrays.asList(new SimpleGrantedAuthority(role));
	}

	public UserInfoRepo getUserInfoRepo() {
		return userInfoRepo;
	}

	@Autowired
	public void setUserInfoRepo(UserInfoRepo userInfoRepo) {
		this.userInfoRepo = userInfoRepo;
	}

}
