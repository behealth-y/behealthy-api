package com.behealthy.domain.auth.repository

import com.behealthy.domain.auth.entity.UserRefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRefreshTokenRepository : JpaRepository<UserRefreshToken, Long>