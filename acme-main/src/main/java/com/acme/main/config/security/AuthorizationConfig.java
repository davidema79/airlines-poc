package com.acme.main.config.security;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * 
 * @author Davide Martorana
 *
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationConfig extends GlobalMethodSecurityConfiguration {

}
