package englishtraining.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.lang.NonNull
import java.util.*

@Entity
data class Word @JvmOverloads constructor(
    @Id
    @GeneratedValue
    val id : UUID? = null,
    @NonNull
    @Column(unique = true)
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
    var active: Boolean = true,
    @CreationTimestamp
    var createdAt: Date? = null,
    @UpdateTimestamp
    var updatedAt: Date? = null
)