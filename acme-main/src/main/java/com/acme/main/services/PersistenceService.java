package com.acme.main.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.acme.main.api.to.FlightsMapperUtils;
import com.acme.main.api.to.request.AddFlightRequest;
import com.acme.main.api.to.request.BuyTicketRequest;
import com.acme.main.api.to.request.ChangePriceRequest;
import com.acme.main.exceptions.ResourceNotFoundException;
import com.acme.persistence.entity.Aircraft;
import com.acme.persistence.entity.Airline;
import com.acme.persistence.entity.Airport;
import com.acme.persistence.entity.Flight;
import com.acme.persistence.repositories.AircraftRepository;
import com.acme.persistence.repositories.AirlineRepository;
import com.acme.persistence.repositories.AirportsRepository;
import com.acme.persistence.repositories.FlightsRepository;

/**
 * 
 * @author Davide Martorana
 *
 */
public class PersistenceService {

	@Autowired
	private FlightsRepository flightsRepository;

	@Autowired
	private AirportsRepository airportsRepository;

	@Autowired
	private AircraftRepository aircraftRepository;

	@Autowired
	private AirlineRepository airlineRepository;

	private static final int ITEM_PER_PAGE = 10;

	/**
	 * Return ALL the flights in the database
	 * 
	 * @return a list of {@link Flight}, empty in case on flight is found.
	 */
	public List<Flight> getFlights() {
		return this.flightsRepository.findAll();
	}

	/**
	 * Returns a list of flights in the given data.
	 * 
	 * @param date
	 *            - given data to search for.
	 * @return a list of {@link Flight}, empty in case on flight is found.
	 */
	public List<Flight> getFlightsByDate(final String date) {
		return this.flightsRepository.findAllByDate(date);
	}

	/**
	 * Returns a list of destinations {@link Airport} for a given airline
	 * icaoCode
	 * 
	 * @param icaoCode
	 *            - the airline code to search for
	 * @param page
	 *            - the wanted page. This MUST be grater than 0.
	 * 
	 * @return a list of destinations {@link Airport}
	 */
	public Page<Airport> getDestinationsByAirlineCode(final Long id, final int page) {
		final int offset = (page - 1) * ITEM_PER_PAGE;
		final List<Airport> content = this.airportsRepository.findAllDestinationByAirlineCode(id, offset, ITEM_PER_PAGE);
		final int total = this.airportsRepository.countAllDestinationByAirlineCode(id);

		final Pageable pageable = new PageRequest(page - 1, ITEM_PER_PAGE);
		return new PageImpl<>(content, pageable, total);
	}

	/**
	 * Retrieves and returns a paginated list of Airlines by code
	 * 
	 * @param icaoCode
	 *            - airline ICAO code. It can be null or blank.In that case all
	 *            the airlines will be retrieved.s
	 * @param page 
	 *            - the wanted page. This MUST be grater than 0.
	 * @return a paginated list of {@link Airline}
	 */
	public Page<Airline> getPaginatedAirlines(final int page) {
		final Pageable pageable = new PageRequest(page, ITEM_PER_PAGE);
	
		return this.airlineRepository.findAll(pageable);
	}
	
	public Airline getAirlineById(final Long id) {
		return this.airlineRepository.findOne(id);
	}

	/**
	 * Buy a ticket for the flight with the given {@code flightCode} and
	 * {@code date}.
	 * 
	 * @param flightCode
	 *            - code of the wanted flight
	 * @param date
	 *            - flight date and time in format "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return the number of rows updated.
	 */
	public int buyTicket(final BuyTicketRequest request) {
		final LocalDateTime date = request.getDateTime();
		final String dateString = date.format(FlightsMapperUtils.DATE_TIME_FORMATTER);
		final String flightCode = request.getFlightCode();

		final int count = this.flightsRepository.buyTicket(flightCode, dateString);

		return count;
	}

	/**
	 * Update the price of the flight with the given {@code flightCode} and
	 * {@code date}.
	 * 
	 * @param flightCode
	 *            - code of the wanted flight
	 * @param date
	 *            - flight date and time in format "yyyy-MM-dd HH:mm:ss"
	 * @param price
	 *            - the new price you want to set to.
	 * @return the number of rows updated.
	 */
	public int updatePrice(final ChangePriceRequest request) {
		final LocalDateTime date = request.getDateTime();
		final String dateString = date.format(FlightsMapperUtils.DATE_TIME_FORMATTER);
		final String flightCode = request.getFlightCode();

		final BigDecimal price = request.getPrice();
		final int count = this.flightsRepository.updatePrice(flightCode, dateString, price);

		return count;
	}

	public Flight saveFlight(final AddFlightRequest flightRequest) {
		Aircraft aircraft = new Aircraft();
		aircraft.setIataCode(flightRequest.getAircraftIATACode());

		if (this.aircraftRepository.exists(Example.of(aircraft))) {
			aircraft = this.aircraftRepository.findOne(Example.of(aircraft));
		} else {
			throw new IllegalArgumentException("Given aircraft IATA code does not exist.");
		}

		Airline airline = new Airline();
		airline.setIataCode(flightRequest.getAirlineIATACode());
		if (this.airlineRepository.exists(Example.of(airline))) {
			airline = this.airlineRepository.findOne(Example.of(airline));
		} else {
			throw new IllegalArgumentException("Given Airline IATA code does not exist.");
		}

		Airport depAirport = new Airport();
		depAirport.setIataCode(flightRequest.getDepartureAirportIATACode());

		if (this.airportsRepository.exists(Example.of(depAirport))) {
			depAirport = this.airportsRepository.findOne(Example.of(depAirport));
		} else {
			throw new IllegalArgumentException("Given Departure Airport IATA code does not exist.");
		}

		Airport destAirport = new Airport();
		destAirport.setIataCode(flightRequest.getDestinationAirportIATACode());
		if (this.airportsRepository.exists(Example.of(destAirport))) {
			destAirport = this.airportsRepository.findOne(Example.of(destAirport));
		} else {
			throw new IllegalArgumentException("Given Destination Airport IATA code does not exist.");
		}

		final boolean exitsRoute = this.flightsRepository.existRoutesByAirlineAndSourceAirportAndDestinationAirport(airline.getId(), depAirport.getId(), destAirport.getId());
		if(!exitsRoute) {
			throw new ResourceNotFoundException("Given Route does not exist.");
		}
		
		final Date date = Date.from(flightRequest.getDateTime().atZone(ZoneId.systemDefault()).toInstant());
		
		final Flight checkFlight = new Flight();
		checkFlight.setFlightCode(flightRequest.getFlightCode());
		checkFlight.setDate(date);
		if (this.flightsRepository.exists(Example.of(checkFlight))) {
			throw new DataIntegrityViolationException("Flight already exist.");
		}

		final Flight flight = new Flight();
		flight.setAircraft(aircraft.getModel());
		flight.setDate(date);
		flight.setDeparture(depAirport.getIataCode());
		flight.setDestination(destAirport.getIataCode());
		flight.setAirlineName(airline.getName());
		flight.setSeats(flightRequest.getSeats());
		flight.setFlightCode(flightRequest.getFlightCode());
		flight.setPrice(flightRequest.getPrice());

		return this.flightsRepository.saveAndFlush(flight);
	}

}
