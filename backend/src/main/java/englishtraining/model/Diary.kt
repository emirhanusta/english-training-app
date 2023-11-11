package englishtraining.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Lob
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.lang.NonNull
import java.util.*

@Entity
data class Diary @JvmOverloads constructor(
    @Id
    @GeneratedValue
    val id : UUID? = null,
    @NonNull
    var title: String,
    @Lob
    var content: String,
    @CreationTimestamp
    var createdAt: Date? = null,
    @UpdateTimestamp
    var updatedAt: Date? = null
)
