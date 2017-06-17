package com.acme.main.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.main.api.to.FlightsMapperUtils;
import com.acme.main.api.to.request.AddFlightRequest;
import com.acme.main.api.to.request.BuyTicketRequest;
import com.acme.main.api.to.request.ChangePriceRequest;
import com.acme.main.api.to.response.FlightResponse;
import com.acme.main.exceptions.ResourceNotFoundException;
import com.acme.main.services.PersistenceService;
import com.acme.persistence.entity.Flight;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author Davide Martorana
 *
 */
@Api("Flights")
@RestController
@RequestMapping(path = "/flights", produces = MediaType.APPLICATION_JSON_VALUE)
public class FlightsRestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FlightsRestController.class);
	
	private static final String VALIDATE_PATTERN_DATE_FORMAT = "yyyy-MM-dd";
	private static final SimpleDateFormat VALIDATE_DATE_FORMATTER = new SimpleDateFormat(VALIDATE_PATTERN_DATE_FORMAT);
	
	@Autowired
	private PersistenceService persistenceService; 

	@ApiOperation(notes = "Lists all the flights with a given departure date.", value = "")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Given date is not valid. Please insert a valid date in the form of: " + VALIDATE_PATTERN_DATE_FORMAT),
			@ApiResponse(code = 500, message = "INTERNAL_SERVER_ERROR: Failure within our services."),
			@ApiResponse(code = 200, message = "") })
	@GetMapping({""})
	public List<FlightResponse> getFlights(@RequestParam(name="date", required=true) final String date) {
		
		try {
			VALIDATE_DATE_FORMATTER.parse(date);
		} catch (final ParseException e) {
			throw new IllegalArgumentException("Given date is not valid. Please insert a valid date in the form of: " + VALIDATE_PATTERN_DATE_FORMAT, e);
		}
		
		final List<Flight> list = this.persistenceService.getFlightsByDate(date);
		
		LOGGER.debug("Retrieved {} flights", list.size());
		
		return FlightsMapperUtils.fromEntityToResponse(list);
	}
	
	@ApiOperation(notes = "Add a new flight availability for a given route. Airline, aircraft and departure and destination airports are identified by the IATA code.", value = "")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "BAD REQUEST: One or more of the given codes is not valid."),
			@ApiResponse(code = 404, message = "NOT FOUND: Given route does not exist."),
			@ApiResponse(code = 500, message= "Internal Error" ),
			@ApiResponse(code = 200, message = "") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping({""})
	public FlightResponse addFlight(@RequestBody @Valid final AddFlightRequest flightToInsert) {
		final Flight flight = this.persistenceService.saveFlight(flightToInsert);	
		LOGGER.debug("Content saved: {}", flight);
		
		return FlightsMapperUtils.fromEntityToResponse(flight);
	}
	
	@ApiOperation(notes = "Buy a ticket of a given flight.", value = "")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "BAD REQUEST: One or more of the given values is not valid."),
			@ApiResponse(code = 404, message = "NOT FOUND: Flight not found."),
			@ApiResponse(code = 500, message= "Internal Error" ),
			@ApiResponse(code = 200, message = "") })
	@PreAuthorize("hasRole('ROLE_AGENT')")	
	@PostMapping({"/buyticket"})
	public void buyTicket(@RequestBody @Valid final BuyTicketRequest request) {
		
		final int count = this.persistenceService.buyTicket(request);
		if(count == 0) {
			LOGGER.debug("No ticket found for the request: ", request);
			throw new ResourceNotFoundException("Flight not found.");
		}
	}
	
	@ApiOperation(notes = "Change the price ticket of a given flight.", value = "")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "BAD REQUEST: One or more of the given values is not valid."),
			@ApiResponse(code = 404, message = "NOT FOUND: Flight not found."),
			@ApiResponse(code = 500, message= "Internal Error" ),
			@ApiResponse(code = 200, message = "") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")	
	@PutMapping({"/price"})
	public void setFlightPrice(@RequestBody @Valid final ChangePriceRequest request) {		
		final int count = this.persistenceService.updatePrice(request);
		
		if(count == 0) {
			LOGGER.debug("No flight found for the request: ", request);
			throw new ResourceNotFoundException("Flight not found");
		}
	}
}
