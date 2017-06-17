package com.acme.main.api.to;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.stream.Collectors;

import com.acme.main.api.to.response.AirlineResponse;
import com.acme.main.api.to.response.DestinationResponse;
import com.acme.main.api.to.response.FlightResponse;
import com.acme.persistence.entity.Airline;
import com.acme.persistence.entity.Airport;
import com.acme.persistence.entity.Flight;

/**
 * Collections of utils to map data from the internal domain to the external
 * world, and vice-versa.
 * 
 * @author Davide Martorana
 *
 */
public final class FlightsMapperUtils {

	public static final String PATTERN_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(PATTERN_DATE_FORMAT);

	public static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
			.appendPattern(FlightsMapperUtils.PATTERN_DATE_FORMAT).toFormatter();

	private FlightsMapperUtils() {
		throw new IllegalAccessError("This class cannot be instanciated at Runtime.");
	}

	/**
	 * Converts an instance of {@link Flight} to an instance of
	 * {@link FlightResponse}.
	 * 
	 * @param flight
	 *            - give instance of {@link Flight} to be converted
	 * 
	 * @return a list of {@link FlightResponse}
	 */
	public static final FlightResponse fromEntityToResponse(final Flight flight) {
		final FlightResponse response = new FlightResponse();
		response.setAirlineName(flight.getAirlineName());
		response.setCode(flight.getFlightCode());
		response.setDate(DATE_FORMATTER.format(flight.getDate()));
		response.setDepartureAirport(flight.getDeparture());
		response.setDestinationAirport(flight.getDestination());

		return response;
	}

	/**
	 * Converts a list of {@link Flight} to a list of {@link FlightResponse}.
	 * 
	 * @param flights
	 *            - give list of {@link Flight} to be converted
	 * 
	 * @return a list of {@link FlightResponse}
	 */
	public static final List<FlightResponse> fromEntityToResponse(final List<Flight> flights) {
		return flights.stream().map(flight -> FlightsMapperUtils.fromEntityToResponse(flight))
				.collect(Collectors.toList());
	}

	public static final List<DestinationResponse> fromEntityAirportToDestinationResponse(final List<Airport> airports) {
		return airports.stream().map(airport -> FlightsMapperUtils.fromEntityAirportToDestinationResponse(airport))
				.collect(Collectors.toList());
	}

	/**
	 * Mapping from {@link Airport} to {@link DestinationResponse}
	 * 
	 * @param airport
	 *            - given entity to be mapped
	 * @return an new instance of {@link DestinationResponse} with the data in the given
	 *         {@link Airport}
	 */
	public static final DestinationResponse fromEntityAirportToDestinationResponse(final Airport airport) {
		final DestinationResponse response = new DestinationResponse();
		response.setAirportName(airport.getName());
		response.setCity(airport.getCity());
		response.setCountry(airport.getCountry());

		return response;
	}

	/**
	 * Mapping from {@link Airline} to {@link AirlineResponse}
	 * 
	 * @param airline
	 *            - given entity to be mapped
	 * @return an new instance of {@link AirlineResponse} with the data in the given
	 *         {@link Airline}
	 */
	public static final AirlineResponse fromEntityAirlineToAirlineResponse(final Airline airline) {
		final AirlineResponse response = new AirlineResponse();
		response.setActive(airline.getActive());
		response.setCountry(airline.getCountry());
		response.setIataCode(airline.getIataCode());
		response.setIcaoCode(airline.getIcaoCode());
		response.setId(airline.getId());
		response.setName(airline.getName());

		return response;
	}
}
