package englishtraining.model

import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.springframework.lang.NonNull

@Entity
data class WordList @JvmOverloads constructor(
    @NonNull
    @Column(unique = true)
    var name: String,
    @ManyToMany
    var words: List<Word>? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    var user: User
):BaseEntity()