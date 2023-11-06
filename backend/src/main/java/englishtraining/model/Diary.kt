package englishtraining.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.lang.NonNull
import java.util.*

@Entity
data class Diary @JvmOverloads constructor(
    @Id
    val id : String = UUID.randomUUID().toString(),
    @NonNull
    var title: String,
    var content: String,
    @CreationTimestamp
    var createdAt: Date? = null,
    @UpdateTimestamp
    var updatedAt: Date? = null
)
