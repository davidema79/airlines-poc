package com.acme.main.api.to.response;

/**
 * Airline data exposed 
 * 
 * @author Davide Martorana
 *
 */
public class AirlineResponse {

	private Long id;

	private String name;

	private String iataCode;
	
	private String icaoCode;
	
	private String country;

	private String active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getIcaoCode() {
		return icaoCode;
	}

	public void setIcaoCode(String icaoCode) {
		this.icaoCode = icaoCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	public String get_selfLink() {
		return String.format("/airlines/%s", this.id);
	}
	
}
