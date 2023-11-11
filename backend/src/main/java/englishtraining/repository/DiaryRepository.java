package englishtraining.repository;

import englishtraining.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DiaryRepository extends JpaRepository<Diary, UUID> {
}
