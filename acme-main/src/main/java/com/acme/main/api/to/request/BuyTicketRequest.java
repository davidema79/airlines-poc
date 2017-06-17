package com.acme.main.api.to.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

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
public class BuyTicketRequest {
	
	@NotNull(message="Flight Code cannot be empty.")
	private String flightCode;
	
	@NotNull(message="Flight Date cannot be empty.")
	@DateTimeFormat(pattern=FlightsMapperUtils.PATTERN_DATE_FORMAT)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=FlightsMapperUtils.PATTERN_DATE_FORMAT)
	private LocalDateTime dateTime;

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}
	
	
	
}
