package com.behealthy.domain.auth.dto

import org.springframework.security.oauth2.core.user.OAuth2User
import javax.transaction.NotSupportedException

data class OAuth2Attribute(
    val attr: Map<String, Any>,
    val nameAttributeKey: String,
    val name: String,
    val provider: String,
    val oauth2Id: String
) {
    companion object {
        fun of(provider: String, oAuth2User: OAuth2User, nameAttributeKey: String) = when (provider) {
            "kakao" -> kakao(oAuth2User, nameAttributeKey)
            else -> throw NotSupportedException()
        }

        private fun kakao(oAuth2User: OAuth2User, nameAttributeKey: String): OAuth2Attribute {
            val properties = oAuth2User.attributes["properties"] as Map<String, Any>
            return OAuth2Attribute(
                attr = oAuth2User.attributes,
                nameAttributeKey = nameAttributeKey,
                name = properties["nickname"] as String,
                provider = "kakao",
                oauth2Id = (oAuth2User.attributes[nameAttributeKey] as Long).toString()
            )
        }
    }
}