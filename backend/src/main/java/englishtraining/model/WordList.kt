package englishtraining.model

import jakarta.persistence.*
import org.springframework.lang.NonNull

@Entity
data class WordList @JvmOverloads constructor(
    @NonNull
    @Column(unique = true)
    var name: String,
    @ManyToMany
    var words: List<Word>? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var user: User
):BaseEntity()