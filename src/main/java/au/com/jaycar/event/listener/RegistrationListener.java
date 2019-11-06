package au.com.jaycar.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import au.com.jaycar.business.TokenBusiness;
import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.domain.VerificationToken;
import au.com.jaycar.event.RegistrationEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RegistrationListener implements ApplicationListener<RegistrationEvent> {

	private TokenBusiness<VerificationToken> verificationTokenBusiness;

	private JavaMailSender mailSender;

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public TokenBusiness<VerificationToken> getVerificationTokenBusiness() {
		return verificationTokenBusiness;
	}

	@Override
	public void onApplicationEvent(RegistrationEvent event) {

		log.info("Within Listener");
		UserInfo userInfo = event.getUserInfo();

		VerificationToken verificationToken = getVerificationTokenBusiness().createToken(userInfo);

		String url = event.getUrl();

		url += "/users/registration/confirm/" + verificationToken.getToken();

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(userInfo.getEmail());
		mailMessage.setSubject("registration confirmation");
		mailMessage.setText(url);
		mailMessage.setFrom("zareimeh@gmail.com");

		mailSender.send(mailMessage);
	}

	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Autowired
	public void setVerificationTokenBusiness(TokenBusiness<VerificationToken> verificationTokenBusiness) {
		this.verificationTokenBusiness = verificationTokenBusiness;
	}

}
