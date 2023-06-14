package com.api.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages = {"com.api.rest.model"})
@ComponentScan(basePackages = {"com.*"})
@EnableJpaRepositories(basePackages = {"com.api.rest.repository"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
@EnableCaching
public class Application implements WebMvcConfigurer {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("1234"));
	}
	
	/**
	 * Método para realizar uma configuração centralizada do CORS.
	 * Define um grupo de endpoints para liberar o acesso a determinadas origens.
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// Libera quais métodos podem ser chamados via AJAX
		registry.addMapping("/usuario/**")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowedOrigins("*"); // Libera acesso de todas as rotas a partir de /usuario
		
		WebMvcConfigurer.super.addCorsMappings(registry);
	}
	
}
