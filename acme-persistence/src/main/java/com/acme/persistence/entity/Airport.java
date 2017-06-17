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
@Table(name="airports")
public class Airport {
	
	@Id
	@Column(name="Airport_ID")
	private Long id;

	@Column(name="Name")
	private String name;

	@Column(name="City")
	private String city;

	@Column(name="Country")
	private String country;

	@Column(name="IATA")
	private String iataCode;

	@Column(name="ICAO")
	private String icaoCode;

	@Column(name="Latitude")
	private Integer latitude;

	@Column(name="Longitude")
	private Integer longitude;

	@Column(name="Altitude")
	private Integer altitude;

	@Column(name="Timezone")
	private Integer timezone;

	@Column(name="DST")
	private String dst;

	@Column(name="Tz_Timezone")
	private String tzTimeZone;

	@Column(name="Type")
	private String type;

	@Column(name="Source")
	private String source;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public Integer getLatitude() {
		return latitude;
	}

	public void setLatitude(Integer latitude) {
		this.latitude = latitude;
	}

	public Integer getLongitude() {
		return longitude;
	}

	public void setLongitude(Integer longitude) {
		this.longitude = longitude;
	}

	public Integer getAltitude() {
		return altitude;
	}

	public void setAltitude(Integer altitude) {
		this.altitude = altitude;
	}

	public Integer getTimezone() {
		return timezone;
	}

	public void setTimezone(Integer timezone) {
		this.timezone = timezone;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getTzTimeZone() {
		return tzTimeZone;
	}

	public void setTzTimeZone(String tzTimeZone) {
		this.tzTimeZone = tzTimeZone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	
}
