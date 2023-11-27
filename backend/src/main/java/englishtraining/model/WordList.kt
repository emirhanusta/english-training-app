package englishtraining.model

import jakarta.persistence.*
import org.springframework.lang.NonNull

@Entity
data class WordList @JvmOverloads constructor(
    @NonNull
    var name: String,
    @ManyToMany
    var words: List<Word>? = null
):BaseEntity()