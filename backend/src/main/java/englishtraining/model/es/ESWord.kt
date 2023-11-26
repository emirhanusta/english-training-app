package englishtraining.model.es

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.annotations.Setting

@Setting(settingPath = "static/es-settings.json")
@Document(indexName = "words_index")
@JsonIgnoreProperties(ignoreUnknown = true)
data class ESWord @JvmOverloads constructor(
    @Id
    val id: String? = null,
    @Field(name = "name", type = FieldType.Text, analyzer = "custom_index", searchAnalyzer = "custom_search")
    var name: String,
    var definition: String,
    var exampleSentences: List<String>?,
    var level: String?,
    var status: String
)

