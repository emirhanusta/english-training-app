package englishtraining.repository;

import englishtraining.model.WordList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WordListRepository extends JpaRepository<WordList, UUID> {
    Optional<WordList> findByName(String name);

    Boolean existsByName(String name);
}
