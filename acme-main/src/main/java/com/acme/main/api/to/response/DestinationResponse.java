package com.acme.main.api.to.response;

/**
 *  Subset of Destination Data Exposed by Rest API
 *  
 * @author Davide Martorana
 *
 */
public class DestinationResponse {

	private String country;
	
	private String city;
	
	private String airportName;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
		
}
