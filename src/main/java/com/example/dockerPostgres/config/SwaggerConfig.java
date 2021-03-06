package com.example.dockerPostgres.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	 @Bean
	    public Docket api(){
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.example.dockerPostgres.controller"))
	                .paths(PathSelectors.regex("/api.*"))
	                .build()
	                .useDefaultResponseMessages(false)
	                .apiInfo(apiInfo());
	    }

	 private ApiInfo apiInfo(){
	        return new ApiInfoBuilder()
	                .title("CRUD Cliente")
	                .description("Treinando CRUD numa API com Spring Boot")
	                .version("1.0")
	                .license("Apache License version 2.0")
	                .contact(new Contact("Gabriel Potje", "https://www.linkedin.com/in/gabriel-potje-de-souza-b55973190/",
	                        "gpotje.souza@gmail.com"))
	                .build();
	    }
}
