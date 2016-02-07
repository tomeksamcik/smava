package de.smava.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.smava.data.AccountServiceEntityNotFoundException;
import de.smava.data.AccountServiceException;
import de.smava.data.AccountServiceNonUniqueException;

/**
 * @author Tomek Samcik
 *
 * A class handling REST endpoint exceptions
 */
@ControllerAdvice
class GlobalControllerExceptionHandler {
	
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(AccountServiceNonUniqueException.class)
    public ResponseEntity<Error> handleAccountServiceNonUniqueException(HttpServletRequest req, Exception e) {
    	Error error = new Error(409, "Constraint violation: " + e.getMessage());
    	return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
    }
    
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccountServiceEntityNotFoundException.class)
    public ResponseEntity<Error> handleAccountServiceEntityNotFoundException(HttpServletRequest req, Exception e) {
    	Error error = new Error(404, "Entity not found: " + e.getMessage());
    	return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(AccountServiceException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleAccountServiceException(HttpServletRequest req, Exception e) {
    	System.out.println(e.getClass().getCanonicalName());
    	Error error = new Error(500, "Data layer error: " + e.getMessage());
    	return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleException(HttpServletRequest req, Exception e) {    
    	Error error = new Error(500, "Error: " + e.getMessage());
    	return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }    

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(HttpServletRequest req, Exception e) {
    	Error error = new Error(500, "Invalid input: " + e.getMessage());
    	return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
    }    
    
}