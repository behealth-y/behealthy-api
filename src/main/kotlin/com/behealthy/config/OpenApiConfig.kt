package com.behealthy.config

import com.behealthy.domain.auth.AuthConstants.AUTHENTICATION_HEADER_NAME
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(buildProperties: BuildProperties): OpenAPI? {
        return OpenAPI()
            .components(
                Components().addSecuritySchemes(
                    AUTHENTICATION_HEADER_NAME,
                    SecurityScheme()
                        .name(AUTHENTICATION_HEADER_NAME)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("JWT")
                )
            )
            .info(
                Info()
                    .title("behealthy")
                    .version(buildProperties.version)
            )
            .addSecurityItem(SecurityRequirement().addList(AUTHENTICATION_HEADER_NAME))
    }
}