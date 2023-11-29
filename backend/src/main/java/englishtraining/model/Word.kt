package englishtraining.model

import englishtraining.model.enums.Level
import englishtraining.model.enums.WordStatus
import jakarta.persistence.*
import org.springframework.lang.NonNull
import java.util.*

@Entity
data class Word @JvmOverloads constructor(
    @NonNull
    var name: String,
    @NonNull
    var definition: String,
    var exampleSentences: List<String>?,
    @Enumerated(EnumType.ORDINAL)
    var level: Level?,
    @Enumerated(EnumType.ORDINAL)
    var status: WordStatus = WordStatus.LEARNING,
    var active: Boolean = true,
):BaseEntity()