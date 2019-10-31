package au.com.jaycar.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

@Entity
@Data
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String userName;

	private String firstName;

	private String lastName;

	@Column(unique = true, updatable = false, nullable = false)
	private String email;

	private String password;

	private Boolean userEnabled;

	@Column(updatable = false, nullable = false)
	private ZonedDateTime timeCreated;

	private ZonedDateTime timeUpdated;

	@PrePersist
	void prePersist() {
		userEnabled = false;
		timeCreated = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
	}

	@PreUpdate
	void preUpdate() {
		timeUpdated = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
	}

}
