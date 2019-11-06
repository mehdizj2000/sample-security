package au.com.jaycar.event;

import org.springframework.context.ApplicationEvent;

import au.com.jaycar.domain.UserInfo;

public class RegistrationEvent extends ApplicationEvent {

	private static final long serialVersionUID = -6364809282303490598L;

	private String url;

	private UserInfo userInfo;

	public RegistrationEvent(UserInfo userInfo, String url) {
		super(userInfo);
		this.setUrl(url);
		this.setUserInfo(userInfo);
	}

	public String getUrl() {
		return url;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
