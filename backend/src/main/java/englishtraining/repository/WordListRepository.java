package englishtraining.repository;

import englishtraining.model.WordList;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WordListRepository extends JpaRepository<WordList, UUID> {
    Optional<WordList> findByIdAndActiveTrue(UUID id);

    List<WordList> findAllByActiveTrue(PageRequest pageRequest);
}
