package au.com.jaycar.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import lombok.Data;

@Data
@Entity
public class VerificationToken {

	private static final int EXPIRATION = 24;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String token;

	@OneToOne
	@JoinColumn(name = "fk_user_id")
	private UserInfo userInfo;

	private ZonedDateTime expiryDate;

	@PrePersist
	void preCreate() {

		expiryDate = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).plusMinutes(EXPIRATION);

	}

}
