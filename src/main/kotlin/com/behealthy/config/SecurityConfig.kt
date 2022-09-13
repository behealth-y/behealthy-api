package com.behealthy.config

import com.behealthy.domain.auth.JWTUtil
import com.behealthy.domain.auth.dto.OAuth2AuthenticationUser
import com.behealthy.domain.auth.filter.JWTFilter
import com.behealthy.domain.auth.service.CustomOAuth2UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.HandlerExceptionResolver


@EnableWebSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtFilter: JWTFilter,
        authenticationEntryPoint: AuthenticationEntryPoint,
        oAuth2UserService: CustomOAuth2UserService,
        oAuth2AuthenticationSuccessHandler: AuthenticationSuccessHandler
    ): SecurityFilterChain {
        return http.csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/api/auth/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // disable session
            .and()
            .formLogin() // disable form login
            .disable()
            .exceptionHandling { it.authenticationEntryPoint(authenticationEntryPoint) }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .also {
                it.oauth2Login()
                    .userInfoEndpoint() // 로그인 성공 후 사용자 정보를 가져올 때의 설정들을 담당
                    .userService(oAuth2UserService)
                    .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
            }
            .build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { webSecurity ->
            webSecurity.ignoring().antMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**")
        }
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun authenticationEntryPoint(handlerExceptionResolver: HandlerExceptionResolver): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { request, response, authException ->
            handlerExceptionResolver.resolveException(request, response, null, authException);
        }
    }

    @Bean
    fun oAuth2AuthenticationSuccessHandler(objectMapper: ObjectMapper, jwtUtil: JWTUtil): AuthenticationSuccessHandler {
        return AuthenticationSuccessHandler { request, response, authentication ->
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.writer.write(
                objectMapper.writeValueAsString(
                    mapOf("token" to jwtUtil.generateToken((authentication.principal as OAuth2AuthenticationUser).user))
                )
            );
        }
    }
}