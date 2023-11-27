package englishtraining.model

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue
    var id: UUID? = null
    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    var createdDate: Date? = null
    @UpdateTimestamp
    @Column(name = "updated_date")
    var updatedDate: Date? = null
}