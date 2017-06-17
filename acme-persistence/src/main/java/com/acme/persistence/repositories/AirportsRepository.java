package com.acme.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.acme.persistence.entity.Airport;

/**
 * Airports repository.
 * 
 * @author Davide Martorana
 *
 */
@Repository
public interface AirportsRepository extends JpaRepository<Airport, Long> {

	static final String NATIVE_QUERY_CONDITION =  "SELECT arp.* "
			+ " FROM airports arp           "
			+ " JOIN routes rts ON rts.Destination_Airport_ID = arp.Airport_ID  "
			+ " JOIN airlines arl ON  rts.Airline_ID = arl.Airline_ID           "
			+ " WHERE arl.Airline_ID = :id                                   "
			+ " GROUP BY arl.Airline_ID, rts.Destination_Airport_ID             "
			+ " ORDER BY Airport_ID                                             ";
	
	/**
	 * Returns a list of Airport by the the ICAO airline code
	 * 
	 * @param airlineCode
	 *            - ICAO code
	 * @return list of Airports
	 */
	@Query(nativeQuery = true, value =	NATIVE_QUERY_CONDITION +
					"LIMIT :limit OFFSET :offset")
	List<Airport> findAllDestinationByAirlineCode(@Param("id") final Long id, @Param("offset") final int offset, @Param("limit") final int limit);
	

	/**
	 * Returns a the number of Destination by the the ICAO airline code
	 * 
	 * @param airlineCode
	 *            - ICAO code
	 * @return the total number of  of Airport
	 */
	@Query(nativeQuery = true, value = "SELECT count(*) FROM (" + 
					NATIVE_QUERY_CONDITION + ") as t ")
	int countAllDestinationByAirlineCode(@Param("id") final Long id);

}
