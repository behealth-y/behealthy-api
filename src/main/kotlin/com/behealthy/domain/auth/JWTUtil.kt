package com.behealthy.domain.auth

import com.behealthy.domain.auth.repository.EmailPasswordUserRepository
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

    fun extractEmail(token: String): String {
        return extractClaim(token) { it.subject }
    }

    fun extractExpiration(token: String): Date {
        return extractClaim(token) { it.expiration }
    }

    fun extractUserId(token: String): Int {
        return extractClaim(token) { it.get("userId", Integer::class.java) }.toInt()
    }

    fun extractUserName(token: String): String {
        return extractClaim(token) { it.get("name", String::class.java) }
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun validateToken(token: String, email: String): Boolean {
        return extractEmail(token) == email && isTokenExpired(token)
    }

    fun generateToken(email: String): String {
        val emailPasswordUser = emailPasswordUserRepository.findFirstByEmail(email).get()
        val user = userRepository.findById(emailPasswordUser.userId).get()
        return createToken(mutableMapOf("userId" to emailPasswordUser.id!!, "name" to user.name), email)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).after(Date())
    }


    private fun createToken(claims: MutableMap<String, Any>, email: String): String {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(email)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + EXPIRATION_MILLISECOND))
            .signWith(SignatureAlgorithm.HS256, secret).compact()

    }

    companion object {
        const val EXPIRATION_MILLISECOND = 1000 * 60 * 60 * 1 // 1 hour
    }
}