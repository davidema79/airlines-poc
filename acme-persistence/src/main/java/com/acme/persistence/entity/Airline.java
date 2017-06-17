package com.acme.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Davide Martorana
 *
 */
@Entity
@Table(name = "airlines")
public class Airline {

	@Id
	@Column(name = "Airline_ID")
	private Long id;

	@Column(name = "Name")
	private String name;

	@Column(name = "Alias")
	private String alias;
	
	@Column(name = "IATA")
	private String iataCode;
	
	@Column(name = "ICAO")
	private String icaoCode;
	
	@Column(name = "Callsign")
	private String callsign;
	
	@Column(name = "Country")
	private String country;

	@Column(name = "Active")
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

	public String getCallsign() {
		return callsign;
	}

	public void setCallsign(String callsign) {
		this.callsign = callsign;
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

	
	
}
