package englishtraining.model

import jakarta.persistence.Entity
import jakarta.persistence.Lob
import org.springframework.lang.NonNull

@Entity
class Diary(
    @NonNull
    var title: String,
    @NonNull
    @Lob
    var content: String
) : BaseEntity()