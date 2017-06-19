package it.acme.persistence;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.acme.persistence.entity.Airline;
import com.acme.persistence.repositories.AircraftRepository;
import com.acme.persistence.repositories.AirlineRepository;
import com.acme.persistence.repositories.AirportsRepository;
import com.acme.persistence.repositories.FlightsRepository;

/**
 * 
 * @author Davide Martorana
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@ContextConfiguration(classes={PersistenceIT.Config.class})
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class PersistenceIT {
	
	@EnableJpaRepositories(basePackageClasses = { AirlineRepository.class, AircraftRepository.class, AirportsRepository.class, FlightsRepository.class})
	@EntityScan(basePackageClasses = {Airline.class})
	static class Config {
		
	}	
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private AirlineRepository repository;
	

	@Test
	public void testGIVEN_authinticatedUser_WHEN_makeCall_for_getArilines_THEN_isSuccessful() throws Exception {
		final Airline british = new Airline(1355l, "British Airways", null, "BA", "BAW", "SPEEDBIRD", "United Kingdom", "Y");
		final Airline alitalia = new Airline(596l,"Alitalia", null, "AZ", "AZA", "ALITALIA", "Italy", "Y");
				
		this.entityManager.persist(alitalia);
		this.entityManager.persist(british);
		
		
		final List<Airline> list = repository.findAll();
		Assertions.assertThat(list).isNotEmpty();
		Assertions.assertThat(list.size()).isEqualTo(2);
	}
	
}
