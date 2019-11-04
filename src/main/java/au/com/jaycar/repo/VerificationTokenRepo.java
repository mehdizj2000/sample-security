package au.com.jaycar.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import au.com.jaycar.domain.VerificationToken;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {

	Optional<VerificationToken> findByToken(String token);

}
