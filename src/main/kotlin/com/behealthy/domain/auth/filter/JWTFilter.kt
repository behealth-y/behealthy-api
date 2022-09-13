package com.behealthy.domain.auth.filter

import com.behealthy.domain.auth.JWTUtil
import com.behealthy.domain.auth.dto.AuthenticatedUser
import com.behealthy.domain.auth.service.EmailPasswordUserService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class JWTFilter(
    private val jwtUtil: JWTUtil,
    private val emailPasswordUserService: EmailPasswordUserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")
        val token = authorizationHeader
            ?.takeIf { authorizationHeader.startsWith("Bearer ") }
            ?.let { authorizationHeader.substring(7) }

        if (token != null && SecurityContextHolder.getContext().authentication == null) {
            jwtUtil.validateToken(token)
            val authentication = UsernamePasswordAuthenticationToken(
                /* principal = */ AuthenticatedUser(
                    jwtUtil.extractUserId(token),
                    jwtUtil.extractUserName(token)
                ),
                /* credentials = */ null,
                /* authorities = */ emptyList()
            ).apply { details = WebAuthenticationDetailsSource().buildDetails(request) }
            SecurityContextHolder.getContext().authentication = authentication

        }

        filterChain.doFilter(request, response)
    }
}