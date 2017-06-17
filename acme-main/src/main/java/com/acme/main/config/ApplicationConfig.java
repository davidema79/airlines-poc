package com.acme.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.acme.main.config.security.AuthorizationConfig;
import com.acme.main.config.security.SecurityConfig;
import com.acme.main.services.PersistenceService;
import com.acme.persistence.PersistenceConfig;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Davide Martorana
 *
 */	
@EnableSwagger2
@Import({SecurityConfig.class, AuthorizationConfig.class, PersistenceConfig.class})
public class ApplicationConfig {

	@Bean
	public PersistenceService flightsService(){
		return new PersistenceService();
	}
	
	@Bean(name = "OBJECT_MAPPER_BEAN")
	public ObjectMapper jsonObjectMapper() {
	    return Jackson2ObjectMapperBuilder.json()
	            .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
	            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
	            .modules(new JavaTimeModule())
	            .build();
	}
	
	 @Bean
	    public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select()                                  
	          .apis(RequestHandlerSelectors.any())              
	          .paths(PathSelectors.any())                          
	          .build();                                           
	    }
	
}
