package com.acme.main.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.acme.main.config.security.AuthorizationConfig;
import com.acme.main.config.security.SecurityConfig;
import com.acme.main.services.PersistenceService;
import com.acme.persistence.entity.Airline;
import com.acme.persistence.repositories.AircraftRepository;
import com.acme.persistence.repositories.AirlineRepository;
import com.acme.persistence.repositories.AirportsRepository;
import com.acme.persistence.repositories.FlightsRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * 
 * @author Davide Martorana
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(AirlinesRestController.class)
@ContextConfiguration(classes={AirlinesRestControllerTest.Config.class})
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class AirlinesRestControllerTest {

	@EnableWebMvc
	@ComponentScan(basePackageClasses={AirlinesRestController.class})
	@Import({SecurityConfig.class, AuthorizationConfig.class})
	public static class Config {
		@Bean
		public PersistenceService flightsService(){
			return Mockito.mock(PersistenceService.class);
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
		private FlightsRepository flightsRepository() {
			return Mockito.mock(FlightsRepository.class);
		}

		@Bean
		public AirportsRepository airportsRepositoryy() {
			return Mockito.mock(AirportsRepository.class);
		}

		@Bean
		public AircraftRepository aircraftRepositoryy() {
			return Mockito.mock(AircraftRepository.class);
		}

		@Bean
		public AirlineRepository airlineRepositoryy() {
			return Mockito.mock(AirlineRepository.class);
		}
	}	

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	public PersistenceService mockPersistenceService;
	
	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}
	
	@After
	public void reset() {
		Mockito.reset(this.mockPersistenceService);
	}

	@Test
	public void testGetArilines() throws Exception {
		final Airline british = new Airline(1355l, "British Airways", null, "BA", "BAW", "SPEEDBIRD", "United Kingdom", "Y");
		final Airline alitalia = new Airline(596l,"Alitalia", null, "AZ", "AZA", "ALITALIA", "Italy", "Y");
		
		final Page<Airline> page = new PageImpl<>(Arrays.asList(british, alitalia));
		
		Mockito.when(this.mockPersistenceService.getPaginatedAirlines(1)).thenReturn(page);
		
		this.mvc.perform(get("/airlines").with(httpBasic("user", "pwdUser123")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].name").value("British Airways"))
				.andExpect(jsonPath("$.content[1].name").value("Alitalia"))
				.andExpect(jsonPath("$.last").value("true"))
				.andExpect(jsonPath("$.totalPages").value("1"))
				.andExpect(jsonPath("$.totalElements").value("2"))
				.andExpect(jsonPath("$.size").value("0"))
				.andExpect(jsonPath("$.first").value("true"));
		
		Mockito.verify(this.mockPersistenceService, Mockito.times(1)).getPaginatedAirlines(1);
					
	}
	
	@Test
	public void testGetArilineById() throws Exception {
		final Airline alitalia = new Airline(596l,"Alitalia", null, "AZ", "AZA", "ALITALIA", "Italy", "Y");
		
		Mockito.when(this.mockPersistenceService.getAirlineById(596l)).thenReturn(alitalia);
		
		this.mvc.perform(get("/airlines/596").with(httpBasic("user", "pwdUser123")))
			.andExpect(status().isOk())
			.andExpect(jsonPath("name").value("Alitalia"))
			.andReturn();
		
		Mockito.verify(this.mockPersistenceService, Mockito.times(1)).getAirlineById(596l);		
	}

}
