package de.smava.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.smava.data.Account;
import de.smava.data.AccountServiceException;
import de.smava.data.services.AccountLoaderService;
import de.smava.data.services.AccountService;

/**
 * @author Tomek Samcik
 *
 * Accounts controller class, exposes bank accounts CRUD operations via RESTful API
 */
@Controller
@Api(value = "/account")
@RequestMapping("/account")
public class AccountEndpoint {
	
	private Logger log = Logger.getLogger(AccountEndpoint.class);
	
	private ConcurrentHashMap<String, HttpSession> sessions = new ConcurrentHashMap<String, HttpSession>();

	@Autowired
	private AccountLoaderService accountLoaderService;		
	
	@Autowired
	private AccountService accountService;

	/**
	 * Initializing data storage with accounts for the given session
	 * 
	 * @param session	session to initialize with
	 */
	private void init(HttpSession session) {
		log.info("sessions: " + sessions.keySet());
		if (sessions.get(session.getId()) == null) {
			accountLoaderService.load(session);
			sessions.put(session.getId(), session);
		}
	}		
	
	/**
     * Returns list of bank accounts
     * 
	 * @param session					a session to retreive accounts for
     * @return							a list of accounts
     * @throws AccountServiceException 	a generic service layer exception
	 */
	@ApiOperation(httpMethod = "GET", 
		value = "Finds Accounts in session",
	    notes = "Session is populated with initial data on the first call",
	    response = Account.class,
	    responseContainer = "List")    
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Unexpected server error"),
	})	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody List<Account> get(HttpSession session) throws AccountServiceException {
		init(session);
    	log.info("get session: " + session.getId());
		List<Account> all = accountService.getAccounts(session);
    	return all;
    }

	/**
     * Creates a new bank account entity
     * 
	 * @param entity					entity to create
	 * @param session					session to create the entity for
	 * @return							created entity
	 * @throws AccountServiceException	a generic service layer exception
	 */
	@ApiOperation(httpMethod = "POST", 
			value = "Creates a new Account in session",
		    notes = "No duplicated IBAN numers are allowed for a session",
		    response = Account.class)    
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 409, message = "Account already exists"),
			@ApiResponse(code = 500, message = "Unexpected server error")
	})	
	@RequestMapping(method=RequestMethod.POST)
    public @ResponseBody Account post(
    		@Valid @ApiParam(value = "Account to be added", required = true) @RequestBody Account entity, 
			HttpSession session) throws AccountServiceException {
		init(session);
    	log.info("post entity: " + entity);
    	log.info("post session: " + session.getId());
    	Account added = accountService.addAccount(session, entity);
        return added;
    }

	/**
     * Updates an existing bank account entity
     * 
	 * @param id						id of the entity to update
	 * @param entity					entity to update with
	 * @param session					a session to update entity for
	 * @return							an updated entity
	 * @throws AccountServiceException	a generic service layer exception
	 */
	@ApiOperation(httpMethod = "PUT", 
			value = "Updates an Account in session",
		    notes = "No duplicated IBAN numers are allowed for a session",
		    response = Account.class)    
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Account not found"),
			@ApiResponse(code = 409, message = "Account already exists"),
			@ApiResponse(code = 500, message = "Unexpected server error")
	})	
	@RequestMapping(value = "/{id}", method=RequestMethod.PUT)
    public @ResponseBody Account put(
    		@ApiParam(value = "Id of Account to be updated", required = true) @PathVariable("id") Long id,
    		@Valid @ApiParam(value = "Account to update with", required = true) @RequestBody Account entity, 
			HttpSession session) throws AccountServiceException {
		init(session);
    	log.info("put id: " + id);
    	log.info("put session: " + session.getId());
    	log.info("put entity: " + entity);
    	Account updated = accountService.updateAccount(session, id, entity);    		
        return updated;
    }

	/**
     * Deletes an existing bank account entity
     * 
	 * @param id						id of the entity to delete
	 * @param session					a session to delete from
	 * @throws AccountServiceException	a generic service layer exception
	 */
	@ApiOperation(httpMethod = "DELETE",
			value = "Deletes an Account in session")    
	@ApiResponses(value = { 
		@ApiResponse(code = 204, message = "Success"),
		@ApiResponse(code = 404, message = "Account not found"),
		@ApiResponse(code = 500, message = "Unexpected server error")
	})	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
    public void delete(
    		@ApiParam(value = "Id of account to be deleted", required = true) @PathVariable("id") Long id, 
			HttpSession session) throws AccountServiceException {
		init(session);
    	log.info("delete id: " + id);
    	log.info("delete session: " + session.getId());
		accountService.deleteAccount(session, id);
   }
    
}