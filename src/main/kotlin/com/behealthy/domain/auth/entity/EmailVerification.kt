package com.behealthy.domain.auth.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@EntityListeners(AuditingEntityListener::class)
@Table(name = "email_verification")
@Entity
class EmailVerification(

    @Id
    @Column(nullable = false)
    val email: String,

    @Column(name = "verification_code", nullable = false)
    var verificationCode: String,

    @Column(nullable = false)
    var expiredAt: LocalDateTime
) {

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    lateinit var createdAt: LocalDateTime // 왜 안먹지..

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    lateinit var modifiedAt: LocalDateTime
}