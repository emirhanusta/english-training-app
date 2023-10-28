package englishtraining.repository;

import englishtraining.model.WordList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordListRepository extends JpaRepository<WordList,String> {
}
