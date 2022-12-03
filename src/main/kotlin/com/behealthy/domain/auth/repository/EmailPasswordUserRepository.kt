package com.behealthy.domain.auth.repository

import com.behealthy.domain.auth.entity.EmailPasswordUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
interface EmailPasswordUserRepository : JpaRepository<EmailPasswordUser, Long> {

    @Transactional(readOnly = true)
    fun findFirstByEmail(email: String): EmailPasswordUser?
}