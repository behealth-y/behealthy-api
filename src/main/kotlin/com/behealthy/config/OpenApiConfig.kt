package com.behealthy.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(buildProperties: BuildProperties): OpenAPI? {
        return OpenAPI()
            .components(Components())
            .info(
                Info()
                    .title("behealthy")
                    .version(buildProperties.version)
            )
    }
}