package com.acme.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acme.persistence.entity.Airline;

/**
 * 
 * @author Davide Martorana
 *
 */
@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {

}
