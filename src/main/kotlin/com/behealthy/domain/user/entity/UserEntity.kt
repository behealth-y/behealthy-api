package com.behealthy.domain.user.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*


@EntityListeners(AuditingEntityListener::class)
@Table(name = "user")
@Entity
class UserEntity(

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var status: Status = Status.ACTIVE
) {

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    lateinit var modifiedAt: LocalDateTime

    enum class Status(val dbColumn: String) {
        ACTIVE("active"),
        WITHDRAW("withdraw");

        @javax.persistence.Converter(autoApply = true)
        class Converter : AttributeConverter<Status, String> {
            override fun convertToDatabaseColumn(attribute: Status?): String? {
                return attribute?.dbColumn
            }

            override fun convertToEntityAttribute(dbData: String?): Status {
                return Status.values().first { it.dbColumn == dbData }
            }
        }

        companion object
    }
}