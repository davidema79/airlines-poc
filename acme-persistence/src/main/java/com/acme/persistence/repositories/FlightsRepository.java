package com.acme.persistence.repositories;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.acme.persistence.entity.Flight;
import com.acme.persistence.entity.FlightKey;

/**
 * 
 * @author Davide Martorana
 *
 */
@Repository
@Transactional
public interface FlightsRepository extends JpaRepository<Flight, FlightKey> {

	@Query(nativeQuery = true, value = "SELECT * FROM flights_d_martorana f "
			+ " WHERE DATE(f.Departure_Date) = :dateToSearch")
	List<Flight> findAllByDate(@Param("dateToSearch") final String date);

	@Modifying
	@Query(nativeQuery = true, value = "UPDATE flights_d_martorana f "
			+ " SET f.Seat_Availability = (f.Seat_Availability -1) " + " WHERE f.Flight_Code = :flightCode "
			+ " AND f.Departure_Date = :dateFlight ")
	int buyTicket(@Param("flightCode") final String flightCode, @Param("dateFlight") final String date);

	@Modifying
	@Query(nativeQuery = true, value = "UPDATE flights_d_martorana f " + " SET f.Price = :newPrice "
			+ " WHERE f.Flight_Code = :flightCode " + " AND f.Departure_Date = :dateFlight ")
	int updatePrice(@Param("flightCode") final String flightCode, @Param("dateFlight") final String date,
			@Param("newPrice") final BigDecimal price);

	@Query(nativeQuery = true, value = "SELECT count(*) > 0 " 
			+ " FROM routes r                                      "
			+ " WHERE Airline_ID = :airlineId                      "
			+ " AND Source_Airport_ID = :sourceAirportId           "
			+ " AND Destination_Airport_ID = :destinationAirportId ")
	boolean existRoutesByAirlineAndSourceAirportAndDestinationAirport(@Param("airlineId") final Long airlineId,
			@Param("sourceAirportId") final Long sourceAirportId,
			@Param("destinationAirportId") final Long destinationAirportId);
}
