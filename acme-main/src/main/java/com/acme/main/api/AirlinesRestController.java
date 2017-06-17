package com.acme.main.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.main.api.to.FlightsMapperUtils;
import com.acme.main.api.to.response.AirlineResponse;
import com.acme.main.api.to.response.DestinationResponse;
import com.acme.main.exceptions.ResourceNotFoundException;
import com.acme.main.services.PersistenceService;
import com.acme.persistence.entity.Airline;
import com.acme.persistence.entity.Airport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author Davide Martorana
 *
 */
@Api("Airlines")
@RestController
@RequestMapping(path = "/airlines", produces = MediaType.APPLICATION_JSON_VALUE)
public class AirlinesRestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AirlinesRestController.class);
	
	@Autowired
	private PersistenceService persistenceService; 

	
	@ApiOperation(notes = "Lists all the airlines per page.", value = "")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message= "Internal Error" ),
			@ApiResponse(code = 200, message = "") })	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping({""})
	public Page<AirlineResponse> getAirlines(@RequestParam(name="page", required=false, defaultValue="1") final int page) {
		
		final int pageReturned = Math.max(page, 1); 
		
		final Page<Airline> pageOfAirlines = this.persistenceService.getPaginatedAirlines(pageReturned);
		final Page<AirlineResponse> airlinesRespose = pageOfAirlines.map(airline -> FlightsMapperUtils.fromEntityAirlineToAirlineResponse(airline) );
		LOGGER.info("Retrived {} items", pageOfAirlines.getContent().size());
		
		return airlinesRespose;		
	}	
	
	@ApiOperation(notes = "Display the airline details with the given id.", value = "")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "BAD REQUEST: One or more of the given values is not valid."),
			@ApiResponse(code = 404, message = "NOT FOUND: Airline not found."),
			@ApiResponse(code = 500, message= "Internal Error" ),
			@ApiResponse(code = 200, message = "") })	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping({"{id}"})
	public AirlineResponse getAirlinesById(@PathVariable(name="id") final Long id) {
		
		final Airline airline = this.persistenceService.getAirlineById(id);
		if(airline == null) {
			throw new ResourceNotFoundException("Airline not found.");
		}
		LOGGER.info("Retrived item {}", airline);
		
		return FlightsMapperUtils.fromEntityAirlineToAirlineResponse(airline);		
	}
	
	@ApiOperation(notes = "Lists all the routes destionations for a the airline id.", value = "")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "NOT FOUND: Airline not found."),
			@ApiResponse(code = 500, message= "Internal Error" ),
			@ApiResponse(code = 200, message = "") })
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@GetMapping("{id}/routes/destinations")
	public Page<DestinationResponse> getDestinations(@PathVariable(name="id") final Long id, @RequestParam(name="page", required=false, defaultValue="1") final int page) {
		
		final Airline airline = this.persistenceService.getAirlineById(id);
		if(airline == null) {
			throw new ResourceNotFoundException("Airline not found.");
		}
		LOGGER.info("Retrived item {}", airline);
		
		final int pageReturned = Math.max(page, 1); 
		
		final Page<Airport> pageOfairports = this.persistenceService.getDestinationsByAirlineCode(id, pageReturned);
		final Page<DestinationResponse> destinations = pageOfairports.map(airport -> FlightsMapperUtils.fromEntityAirportToDestinationResponse(airport) );

		LOGGER.info("Retrived {} items", pageOfairports.getContent().size());
		
		return destinations;		
	}

}
