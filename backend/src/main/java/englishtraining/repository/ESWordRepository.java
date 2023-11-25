package englishtraining.repository;

import englishtraining.model.es.ESWord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESWordRepository extends ElasticsearchRepository<ESWord, String> {
}
