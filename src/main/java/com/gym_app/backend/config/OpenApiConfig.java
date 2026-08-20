package com.gym_app.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gymAppOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gym App API")
                        .description("API para gerenciamento de alunos, professores, exercícios e treinos")
                        .version("v1")
                        .contact(new Contact()
                                .name("Gym App")));
    }
}
