package englishtraining.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.lang.NonNull
import java.util.*

@Entity
data class WordList @JvmOverloads constructor(
    @Id
    val id: String = UUID.randomUUID().toString(),
    @NonNull
    var name: String,
    @OneToMany(mappedBy = "wordList", targetEntity = Word::class, fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var words: List<Word>? = null,
    @CreationTimestamp
    var createdAt: Date? = null,
    @UpdateTimestamp
    var updatedAt: Date? = null
)