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
@Table(name = "aircraft_type")
public class Aircraft {

	@Id
	@Column(name = "ICAO_Code")
	private String icaoCode;

	@Column(name = "IATA_Code")
	private String iataCode;

	@Column(name = "Model")
	private String model;

	public String getIcaoCode() {
		return icaoCode;
	}

	public void setIcaoCode(String icaoCode) {
		this.icaoCode = icaoCode;
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
}
