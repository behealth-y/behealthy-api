package com.behealthy.domain.auth.dto

import com.behealthy.domain.user.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class EmailPasswordAuthenticationUser(
    private val email: String,
    private val _password: String,
    val userEntity: UserEntity,
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> = emptyList()
    override fun getUsername(): String = email
    override fun getPassword(): String = _password

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true

}