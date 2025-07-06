package br.com.personreg.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
				.allowedOrigins("http://localhost:4200",
						"https://localhost:8081", "http://localhost:5173/",
						"http://localhost:3031",
						"http://web-person-registration-dev:5173",
						"http://web-person-registration:3031")
				.allowedMethods("POST", "PUT", "DELETE", "GET")
				.allowedHeaders("*");
	}
}
