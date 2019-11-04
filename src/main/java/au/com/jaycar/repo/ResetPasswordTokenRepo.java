package au.com.jaycar.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.jaycar.domain.ResetPasswordToken;

public interface ResetPasswordTokenRepo extends JpaRepository<ResetPasswordToken, Long> {

	Optional<ResetPasswordToken> findByToken(String token);

}
