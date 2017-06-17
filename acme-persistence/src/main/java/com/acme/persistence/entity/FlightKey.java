package com.acme.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 
 * @author Davide Martorana
 *
 */
public class FlightKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 485332249023970224L;

	@Id
	@Column(name ="Flight_Code", nullable = false)
	private String flightCode;
	
	@Id
	@Column(name ="Departure_Date", nullable = false)
	private Date date;

	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
