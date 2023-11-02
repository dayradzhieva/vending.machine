package com.example.vending.machine.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI myOpenAPI() {
		Info info = new Info()
		.title("Vending Machine API")
		.version("1.0")
		.description("Vending Machine.");
		
		return new OpenAPI().info(info);
	}
}
