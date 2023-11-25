package englishtraining.model.es

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "words_index")
data class ESWord @JvmOverloads constructor(
    @Id
    val id: String? = null,
    var name: String,
    var definition: String,
    var exampleSentences: List<String>?,
    var level: String?,
    var status: String
)

