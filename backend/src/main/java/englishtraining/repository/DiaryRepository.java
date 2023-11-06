package englishtraining.repository;

import englishtraining.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary,String> {
}
