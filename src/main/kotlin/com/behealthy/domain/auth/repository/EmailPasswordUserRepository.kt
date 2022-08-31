package com.behealthy.domain.auth.repository

import com.behealthy.domain.auth.entity.EmailPasswordUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface EmailPasswordUserRepository : JpaRepository<EmailPasswordUser, Long>