package com.behealthy.domain.auth.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*


@EntityListeners(AuditingEntityListener::class)
@Table(name = "user_refresh_token")
@Entity
class UserRefreshToken(

    @Id
    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "refresh_token", nullable = false)
    var refreshToken: String
) {

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    lateinit var modifiedAt: LocalDateTime
}