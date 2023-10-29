package englishtraining.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.lang.NonNull
import java.util.*

@Entity
data class Word @JvmOverloads constructor(
    @Id
    val id : String = UUID.randomUUID().toString(),
    @NonNull
    var name: String,
    @NonNull
    var definition: String,
    var exampleSentences: List<String>?,
    @Enumerated(EnumType.ORDINAL)
    var level: Level ?,
    @Enumerated(EnumType.ORDINAL)
    var status: WordStatus = WordStatus.LEARNING,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wordList_id")
    var wordList: WordList?,
    @CreationTimestamp
    var createdAt: Date? = null,
    @UpdateTimestamp
    var updatedAt: Date? = null
)