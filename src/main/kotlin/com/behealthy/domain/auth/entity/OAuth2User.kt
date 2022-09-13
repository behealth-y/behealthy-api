package com.behealthy.domain.auth.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*


@EntityListeners(AuditingEntityListener::class)
@Table(
    name = "oauth2_user",
    uniqueConstraints = [UniqueConstraint(name = "uk__provider__oauth2_id", columnNames = ["provider", "oauth2_id"])]
)
@Entity
class OAuth2User(

    @Id
    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val provider: String,

    @Column(name = "oauth2_id", nullable = false)
    val oauth2Id: String
) {

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    lateinit var createdAt: LocalDateTime

    @CreatedDate
    @Column(name = "modified_at", nullable = false)
    lateinit var modifiedAt: LocalDateTime
}