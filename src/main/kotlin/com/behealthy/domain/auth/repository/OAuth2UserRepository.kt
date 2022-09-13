package com.behealthy.domain.auth.repository

import com.behealthy.domain.auth.entity.OAuth2User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface OAuth2UserRepository : JpaRepository<OAuth2User, Long> {

    @Transactional(readOnly = true)
    fun findFirstByProviderAndOauth2Id(provider: String, oauth2Id: String): Optional<OAuth2User>
}