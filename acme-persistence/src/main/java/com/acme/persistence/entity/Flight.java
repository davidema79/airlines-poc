package com.acme.persistence.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * 
 * @author Davide Martorana
 *
 */
@Entity
@Table(name="flights_d_martorana")
@IdClass(FlightKey.class)
public class Flight {
	
	@Id
	@Column(name ="Flight_Code")
	private String flightCode;
	
	@Column(name ="Airline_Name")
	private String airlineName;
	
	@Column(name ="Departure_Airport")
	private String departure;
	
	@Column(name ="Destination_Airport")
	private String destination;
		
	@Id
	@Column(name ="Departure_Date")
	private Date date;
	
	@Column(name ="Aircraft_Type")
	private String aircraft;
	
	@Column(name ="Seat_Availability")
	private Integer seats;
	
	@Column(name ="Price")
	private BigDecimal price;

	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAircraft() {
		return aircraft;
	}

	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	
}
