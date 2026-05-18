package com.airobosoft.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("IRCTC Train API")

                        .version("1.0")

                        .description(
                                "Train Management System APIs"
                        )

                        .contact(new Contact()
                                .name("Tameem")
                                .email("tameem@gmail.com")
                        ));
    }
}