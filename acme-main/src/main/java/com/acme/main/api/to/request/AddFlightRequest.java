package com.acme.main.api.to.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import com.acme.main.api.to.FlightsMapperUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * @author Davide Martorana
 *
 */
@Validated
public class AddFlightRequest {

	@NotBlank(message = "Flight Code cannot be empty.")
	private String flightCode;

	@NotNull(message = "Flight Date cannot be empty.")
	@DateTimeFormat(pattern = FlightsMapperUtils.PATTERN_DATE_FORMAT)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FlightsMapperUtils.PATTERN_DATE_FORMAT)
	private LocalDateTime dateTime;

	@NotBlank(message = "Aircraft Type cannot be empty.")
	private String aircraftIATACode;

	@Min(value = 1, message = "Number of seats cannot be less than 1.")
	@Max(value = 500, message = "Number of seats cannot be more than 500.")
	private int seats;

	@NotNull(message = "Price cannot be empty.")
	@DecimalMin(value = "1.00", message = "Price must be greater than 1.00")
	private BigDecimal price;

	@NotBlank(message = "Airline ICAO code cannot be null.")
	private String airlineIATACode;

	@NotBlank(message = "Departure Airport ICAO code cannot be null.")
	private String departureAirportIATACode;

	@NotBlank(message = "Destination Airport ICAO code cannot be null.")
	private String destinationAirportIATACode;
	
	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime date) {
		this.dateTime = date;
	}

	public String getAircraftIATACode() {
		return aircraftIATACode;
	}

	public void setAircraftIATACode(String aircraftIATACode) {
		this.aircraftIATACode = aircraftIATACode;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getAirlineIATACode() {
		return airlineIATACode;
	}

	public void setAirlineIATACode(String airlineIATACode) {
		this.airlineIATACode = airlineIATACode;
	}

	public String getDepartureAirportIATACode() {
		return departureAirportIATACode;
	}

	public void setDepartureAirportIATACode(String departureAirportIATACode) {
		this.departureAirportIATACode = departureAirportIATACode;
	}

	public String getDestinationAirportIATACode() {
		return destinationAirportIATACode;
	}

	public void setDestinationAirportIATACode(String destinationAirportIATACode) {
		this.destinationAirportIATACode = destinationAirportIATACode;
	}

}
