package com.acme.main.api.to.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
public class ChangePriceRequest {

	@NotNull(message = "Flight Code cannot be empty.")
	private String flightCode;

	@NotNull(message = "Flight Date cannot be empty.")
	@DateTimeFormat(pattern = FlightsMapperUtils.PATTERN_DATE_FORMAT)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = FlightsMapperUtils.PATTERN_DATE_FORMAT)
	private LocalDateTime dateTime;

	@NotNull(message = "Price cannot be null")
	@DecimalMin(value = "1.00", message = "Price must be greater than 1.00")
	@DecimalMax(value = "9999.99", message ="Price cannot be greater than 9999.99" )
	private BigDecimal price;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	
	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
