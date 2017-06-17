package com.acme.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.acme.main.config.ApplicationConfig;

/**
 * Application Launcher - Starter point
 * 
 * @author Davide Martorana
 *
 */
@SpringBootApplication
@Import({ApplicationConfig.class})
public class ApplicationLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationLauncher.class);	
	
	
	public static void main(String[] args) {
		
		LOGGER.info("************************************************************************");
		LOGGER.info("*************      STARTING APPLICATION ACME - TRAVEL...      **********");
		LOGGER.info("************************************************************************");
		
		try {
			
			SpringApplication.run(ApplicationLauncher.class, args);
			
		} catch(final Exception e ) {
			LOGGER.info("************************************************************************");
			LOGGER.info("*************     ...APPLICATION STARTING FAILED           *************");
			LOGGER.info("************************************************************************");
			System.exit(-1);
		}
		
		LOGGER.info("************************************************************************");
		LOGGER.info("*************      ... APPLICATION ACME - TRAVEL STARTED      **********");
		LOGGER.info("************************************************************************");
	}
	
	
}
