package englishtraining.model

import englishtraining.model.enums.Role
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "users")
data class User @JvmOverloads constructor(
    @Id
    @GeneratedValue
    val id : UUID? = null,
    @Column(unique = true)
    var username: String,
    var password: String,
    var email: String,
    @Enumerated(EnumType.ORDINAL)
    val role: Role? = null,
)
