package englishtraining.repository;

import englishtraining.model.Word;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WordRepository extends JpaRepository<Word, UUID> {
    Optional<Word> findByIdAndActiveTrue(UUID id);

    List<Word> findAllByActiveTrue(PageRequest pageRequest);

    Boolean existsByNameAndActiveTrue(String name);
}
