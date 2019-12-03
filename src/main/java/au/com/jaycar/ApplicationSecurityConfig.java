package au.com.jaycar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private AppUserService appUserService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		// @formatter:off
		builder
			.userDetailsService(getAppUserService())
			.passwordEncoder(passwordEncoder());
// 		builder
// 			.inMemoryAuthentication()
// 			.withUser("mehdi")
// 			.password(passwordEncoder().encode("mehdi"))
// 			.authorities("USER")
 			/*.and()
 			.withUser("admin")
 			.password(passwordEncoder().encode("admin"))
 			.authorities("ADMIN")*/;
		// @formatter:on
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/webjars/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
 		http
		.authorizeRequests()
//			.antMatchers("/users/list").hasAuthority("ADMIN")
			.antMatchers("/users/save","/users/new", "/users/registration/confirm/**").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin().loginPage("/login").permitAll().loginProcessingUrl("/logmein")
		.and().logout().permitAll().logoutUrl("/logmeout")
		.and().rememberMe()
		.and().csrf().disable()
		;
		
		// @formatter:on
	}

	public AppUserService getAppUserService() {
		return appUserService;
	}

	@Autowired
	public void setAppUserService(AppUserService appUserService) {
		this.appUserService = appUserService;
	}

}
