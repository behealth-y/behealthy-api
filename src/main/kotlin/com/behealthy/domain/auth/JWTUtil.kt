package com.behealthy.domain.auth

import com.behealthy.domain.auth.repository.EmailPasswordUserRepository
import com.behealthy.domain.user.entity.UserEntity
import com.behealthy.domain.user.repository.UserRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JWTUtil(
    @Value("\${behealthy.auth.jwt.secret:secret")
    private val secret: String,
    private val emailPasswordUserRepository: EmailPasswordUserRepository,
    private val userRepository: UserRepository
) {
    fun extractExpiration(token: String): Date {
        return extractClaim(token) { it.expiration }
    }

    fun extractUserId(token: String): Long {
        return extractClaim(token) { it.get("userId", Integer::class.java) }.toLong()
    }

    fun extractUserName(token: String): String {
        return extractClaim(token) { it.get("name", String::class.java) }
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun validateToken(token: String): Boolean {
        return isTokenExpired(token)
    }

    fun generateAccessToken(userEntity: UserEntity): String {
        return createToken(
            mutableMapOf("userId" to userEntity.id!!, "name" to userEntity.name),
            userEntity.id,
            ACCESS_TOKEN_EXPIRATION_MILLISECOND
        )
    }

    fun generateRefreshToken(userEntity: UserEntity): String {
        return createToken(
            mutableMapOf("userId" to userEntity.id!!),
            userEntity.id,
            REFRESH_TOKEN_EXPIRATION_MILLISECOND
        )
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).after(Date())
    }

    private fun createToken(claims: MutableMap<String, Any>, userId: Long, expirationTimeMs: Long): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userId.toString())
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationTimeMs))
            .signWith(SignatureAlgorithm.HS256, secret).compact()

    }

    companion object {
        const val ACCESS_TOKEN_EXPIRATION_MILLISECOND: Long = 1000 * 60 * 60 * 1 // 1 hour
        const val REFRESH_TOKEN_EXPIRATION_MILLISECOND: Long = 1000L * 60L * 60L * 24L * 90L // 90 days
    }
}