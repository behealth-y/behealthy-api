package com.behealthy.domain.auth.dto

import com.behealthy.domain.user.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class OAuth2AuthenticationUser(
    private val _attributes: Map<String, Any>,
    private val nameAttributeKey: String,
    val user: User
) : OAuth2User {
    override fun getName(): String = nameAttributeKey
    override fun getAttributes(): Map<String, Any> = _attributes
    override fun getAuthorities(): Collection<GrantedAuthority> = emptyList()
}