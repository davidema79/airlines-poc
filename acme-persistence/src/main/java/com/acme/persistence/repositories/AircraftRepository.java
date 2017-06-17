package com.acme.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acme.persistence.entity.Aircraft;

/**
 * 
 * @author Davide Martorana
 *
 */
@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, String> {

}
