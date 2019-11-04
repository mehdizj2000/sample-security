package au.com.jaycar.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import au.com.jaycar.business.VerificationTokenBusiness;
import au.com.jaycar.domain.UserInfo;
import au.com.jaycar.domain.VerificationToken;
import au.com.jaycar.event.RegistrationEvent;

@Component
public class RegistrationListener implements ApplicationListener<RegistrationEvent> {

	private VerificationTokenBusiness verificationTokenBusiness;

	private JavaMailSender mailSender;

	@Override
	public void onApplicationEvent(RegistrationEvent event) {

		UserInfo userInfo = event.getUserInfo();

		VerificationToken verificationToken = verificationTokenBusiness.createToken(userInfo);

		String url = event.getUrl();
		
		url += "/users/registration/confrirm?token="+verificationToken.getToken();

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(userInfo.getEmail());
		mailMessage.setSubject("registration confirmation");
		mailMessage.setText(url);
		mailMessage.setFrom("zareimeh@gmail.com");

		mailSender.send(mailMessage);
	}

	public VerificationTokenBusiness getVerificationTokenBusiness() {
		return verificationTokenBusiness;
	}

	@Autowired
	public void setVerificationTokenBusiness(VerificationTokenBusiness verificationTokenBusiness) {
		this.verificationTokenBusiness = verificationTokenBusiness;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	@Autowired
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

}
