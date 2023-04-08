package com.g6.nfp121;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@RequiredArgsConstructor
@EnableSwagger2
// @ComponentScan(basePackages = "com.g6.nfp121")
public class NFP121Application {

	public static void main(String[] args) {
		SpringApplication.run(NFP121Application.class, args);
	}

	/**
	 * Mappeur de modèles. Utilisé pour mapper les DTOs en Entités et vice versa
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(thymeleafTemplateResolver());
		return templateEngine;
	}

	@Bean
	public SpringResourceTemplateResolver thymeleafTemplateResolver() {
		SpringResourceTemplateResolver templateResolver
				= new SpringResourceTemplateResolver();
		templateResolver.setPrefix("/WEB-INF/views/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
	}
}
