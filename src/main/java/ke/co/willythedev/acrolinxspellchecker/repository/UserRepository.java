package ke.co.willythedev.acrolinxspellchecker.repository;

import ke.co.willythedev.acrolinxspellchecker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Extends JPaRepository
 * Provides utility methods from JPA as well as custom ones.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

}
