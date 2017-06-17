package com.acme.main.api;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.acme.main.exceptions.ResourceNotFoundException;

/**
 * Global Error handler
 * 
 * @author Davide Martorana
 *
 */
@RestControllerAdvice
@ResponseBody
public class GlobalErrorHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalErrorHandler.class);

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handlerForIllegalArgument(final IllegalArgumentException ex) {
		return new ErrorMessage(ex.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorMessage handlerForIllegalArgument(final MethodArgumentNotValidException ex) {
		final List<String> listErrors = ex.getBindingResult()
				.getFieldErrors().stream()
				.map(item -> item.getDefaultMessage())
				.collect(Collectors.toList());
		
		return new ErrorMessage(StringUtils.join(listErrors, ", "));
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handlerForResourceNotFoundException(final ResourceNotFoundException ex) {
		return new ErrorMessage(ex.getMessage());
	}
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ErrorMessage handlerForDataIntegrityViolationException(final DataIntegrityViolationException ex) {
		return new ErrorMessage(ex.getMessage());
	}
	
	@ExceptionHandler({AccessDeniedException.class, ServletException.class})	
	public void handlerAccessDeniedException(final Exception ex) throws Exception{
		LOGGER.info("Error {} to be not filtered.", ex.getMessage(), ex);
		throw ex;
	}
	
	

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handlerForGenericRuntimeException(final RuntimeException ex) {
		LOGGER.error("GenericRuntimeException:", ex);
		return new ErrorMessage("Internal Error");
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handlerForGenericException(final Exception ex) {
		LOGGER.error("Exception:", ex);
		return new ErrorMessage("Internal Error");
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, ?> handle(ConstraintViolationException exception) {
		final List<String> messagesList = exception.getConstraintViolations().stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());

		return this.error(messagesList);
	}

	private Map<String, ?> error(Object message) {
		return Collections.singletonMap("error", message);
	}
}
