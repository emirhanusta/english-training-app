package englishtraining.repository;

import englishtraining.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String name);

    Optional<User> findByUsername(String username);
}
