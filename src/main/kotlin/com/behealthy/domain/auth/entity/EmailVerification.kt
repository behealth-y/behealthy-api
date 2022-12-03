package com.behealthy.domain.auth.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@EntityListeners(AuditingEntityListener::class)
@Table(
    name = "email_verification",
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk__email__purpose",
            columnNames = ["email", "purpose"]
        )
    ]
)
@Entity
class EmailVerification(

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(nullable = false)
    val id: Int? = null,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val purpose: Purpose,

    @Column(name = "verification_code", nullable = false)
    var verificationCode: String,

    @Column(nullable = false)
    var expiredAt: LocalDateTime
) {

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    lateinit var modifiedAt: LocalDateTime

    enum class Purpose(val dbColumn: String) {
        SIGN_UP("sign_up"),
        CHANGE_PASSWORD("change_password"),
        UNKNOWN("unknown");

        @javax.persistence.Converter(autoApply = true)
        class Converter : AttributeConverter<Purpose, String> {
            override fun convertToDatabaseColumn(attribute: Purpose?): String? {
                return attribute?.dbColumn
            }

            override fun convertToEntityAttribute(dbData: String?): Purpose {
                return Purpose.values().find { it.dbColumn == dbData } ?: UNKNOWN
            }
        }

        companion object
    }
}