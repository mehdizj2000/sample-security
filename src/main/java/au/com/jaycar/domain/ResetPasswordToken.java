package au.com.jaycar.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import lombok.Data;

@Data
@Entity
public class ResetPasswordToken {
	
	private static final int EXPIRATION = 15;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String token;

	@OneToOne
	private UserInfo userInfo;

	private ZonedDateTime expiryDate;
	
	@PrePersist
	void preCreate() {
		
		expiryDate = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).plusMinutes(EXPIRATION);
		
	}

}
