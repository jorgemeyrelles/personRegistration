package br.com.personreg.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI customOpenApi() {

		final String securitySchemeName = "bearerAuth";

		return new OpenAPI()
				.components(new Components().addSecuritySchemes(
						securitySchemeName,
						new SecurityScheme().type(SecurityScheme.Type.HTTP)
								.scheme("bearer").bearerFormat("JWT")))
				.addSecurityItem(
						new SecurityRequirement().addList(securitySchemeName))
				.info(new Info()
						.title("API Registro - Sistema de registro de pessoas.")
						.description(
								"API Java - Jakarta EE - Spring Boot - Angular - "
										+ "com integração ao RabbitMQ e autenticação JWT")
						.version("v1"));
	}
}