package com.acme.main.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.main.api.to.response.AirlineResponse;
import com.acme.main.api.to.response.DestinationResponse;

/**
 * This tests set make sure the security layer is working fine, and the interaction with the database is fine.
 * 
 * @author Davide Martorana
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class ApplicationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void GIVEN_anAuthenticated_User_WHEN_anAirlineIsRequest_THEN_succesfull() {
		final ResponseEntity<AirlineResponse> response = this.restTemplate.withBasicAuth("user", "pwdUser123").getForEntity("/airlines/596", AirlineResponse.class);
		final int statusCode = response.getStatusCodeValue();
		final AirlineResponse body = response.getBody();
		assertThat(statusCode).isEqualTo(200);
		assertThat(body.getName()).isEqualTo("Alitalia");
	}
	
	@Test
	public void GIVEN_NOT_Authenticated_User_WHEN_anAirlineIsRequest_THEN_401() {
		final ResponseEntity<AirlineResponse> response = this.restTemplate.getForEntity("/airlines/596", AirlineResponse.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(401);
	}
	
	@Test
	public void GIVEN_anAuthenticated_User_WHEN_airlineDestinationsIsRequest_THEN_403() {
		final ResponseEntity<DestinationResponse> response = this.restTemplate.withBasicAuth("user", "pwdUser123").getForEntity("/airlines/596/routes/destinations", DestinationResponse.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(403);
	}
	
	@Test
	public void GIVEN_anAuthenticated_Agent_WHEN_airlineDestinationsIsRequest_THEN_successfull() {
		final ResponseEntity<DestinationResponse> response = this.restTemplate.withBasicAuth("agent", "pwdAgent123").getForEntity("/airlines/596/routes/destinations", DestinationResponse.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
}
