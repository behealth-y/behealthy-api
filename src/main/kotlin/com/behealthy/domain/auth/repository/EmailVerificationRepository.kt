package com.behealthy.domain.auth.repository

import com.behealthy.domain.auth.entity.EmailVerification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EmailVerificationRepository : JpaRepository<EmailVerification, Int> {

    fun findFirstByEmailAndPurpose(email: String, purpose: EmailVerification.Purpose): EmailVerification?
}