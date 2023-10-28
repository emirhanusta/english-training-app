package englishtraining.model

import jakarta.persistence.*
import org.springframework.lang.NonNull
import java.util.UUID

@Entity
data class Word @JvmOverloads constructor(
    @Id
    val id : String = UUID.randomUUID().toString(),
    @NonNull
    var name: String,
    @NonNull
    var definition: String,
    var exampleSentences: List<String>? ,
    @Enumerated(EnumType.ORDINAL)
    var level: Level?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wordList_id")
    var wordList: WordList? ,
)