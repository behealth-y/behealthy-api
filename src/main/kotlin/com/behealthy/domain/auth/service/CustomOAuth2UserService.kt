package com.behealthy.domain.auth.service

import com.behealthy.domain.auth.dto.OAuth2Attribute
import com.behealthy.domain.auth.dto.OAuth2AuthenticationUser
import com.behealthy.domain.auth.repository.OAuth2UserRepository
import com.behealthy.domain.user.dto.UserCreationDto
import com.behealthy.domain.user.service.UserService
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val repository: OAuth2UserRepository,
    private val userService: UserService
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = DefaultOAuth2UserService().loadUser(userRequest)
        val registrationId = userRequest.clientRegistration.registrationId
        val userNameAttributeName =
            userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName // primary key

        val oAuthAttribute = OAuth2Attribute.of(registrationId, oAuth2User, userNameAttributeName)

        val user = findUserOrCreate(oAuthAttribute)
        return OAuth2AuthenticationUser(oAuthAttribute.attr, oAuthAttribute.nameAttributeKey, user)
    }

    fun findUserOrCreate(oAuth2Attribute: OAuth2Attribute): com.behealthy.domain.user.entity.User {
        val oAuth2User = repository.findFirstByProviderAndOauth2Id(oAuth2Attribute.provider, oAuth2Attribute.oauth2Id)
        return if (oAuth2User.isPresent) {
            userService.find(oAuth2User.get().userId).get()
        } else {
            create(oAuth2Attribute)
        }
    }

    private fun create(oAuth2Attribute: OAuth2Attribute): com.behealthy.domain.user.entity.User {
        val user = userService.create(UserCreationDto(oAuth2Attribute.name))
        repository.save(
            com.behealthy.domain.auth.entity.OAuth2User(
                user.id!!,
                oAuth2Attribute.provider,
                oAuth2Attribute.oauth2Id
            )
        )
        return user
    }
}