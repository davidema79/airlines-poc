package com.acme.main.config.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Configuration of the Security
 * 
 * @author Davide Martorana
 *
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("pwdUser123").roles("USER").and()
	            .withUser("agent").password("pwdAgent123").roles("USER", "AGENT").and()
	            .withUser("admin").password("pwdAdmin123").roles("USER", "AGENT", "ADMIN");
	}
	@Override
	public void configure(final WebSecurity web) throws Exception {
		web
		 .ignoring()
		 .antMatchers("/resources/**", "/static/**")
		 .antMatchers("/webjars/springfox-swagger-ui/**", "/swagger-resources/**","swagger-ui.html", "swagger-ui.html#!/**");
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		//@formatter:off
		http
			.csrf()
				.disable()
			.cors()
			.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.authorizeRequests()
					.antMatchers(HttpMethod.GET, "/flights").anonymous()
					.antMatchers("/swagger-ui.html").anonymous()
					.anyRequest().fullyAuthenticated()
			.and()
				.httpBasic()
			;
		//@formatter:on
	}
}
