package englishtraining.model

import jakarta.persistence.Entity
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import org.springframework.lang.NonNull

@Entity
class Diary(
    @NonNull
    var title: String,
    @NonNull
    @Lob
    var content: String,
    @ManyToOne
    var user: User
) : BaseEntity()