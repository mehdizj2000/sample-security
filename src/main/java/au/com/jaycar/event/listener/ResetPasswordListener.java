package au.com.jaycar.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import au.com.jaycar.business.TokenBusiness;
import au.com.jaycar.domain.ResetPasswordToken;
import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.event.ResetPasswordEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ResetPasswordListener implements ApplicationListener<ResetPasswordEvent> {

	private TokenBusiness<ResetPasswordToken> resetPasswordTokenBusiness;

	private JavaMailSender mailSender;

	@Override
	public void onApplicationEvent(ResetPasswordEvent event) {

		log.info("Within Listener");
		UserInfo userInfo = event.getUserInfo();

		ResetPasswordToken resetPasswordToken = resetPasswordTokenBusiness.createToken(userInfo);

		String url = event.getUrl();

		url += "/users/password/reset/confirm/" + resetPasswordToken.getToken();

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(userInfo.getEmail());
		mailMessage.setSubject("reset password confirmation");
		mailMessage.setText(url);
		mailMessage.setFrom("zareimeh@gmail.com");

		mailSender.send(mailMessage);
	}


	public JavaMailSender getMailSender() {
		return mailSender;
	}

	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}


	public TokenBusiness<ResetPasswordToken> getResetPasswordTokenBusiness() {
		return resetPasswordTokenBusiness;
	}

	@Autowired
	public void setResetPasswordTokenBusiness(TokenBusiness<ResetPasswordToken> resetPasswordTokenBusiness) {
		this.resetPasswordTokenBusiness = resetPasswordTokenBusiness;
	}

}
