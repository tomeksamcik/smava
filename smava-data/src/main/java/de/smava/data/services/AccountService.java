package de.smava.data.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import de.smava.data.Account;
import de.smava.data.AccountServiceException;

/**
 * @author Tomek Samcik
 *
 */
public interface AccountService {

	/**
	 * Gets one entity for the given session and id
	 * 
	 * @param session
	 * @param id
	 * @return
	 * @throws AccountServiceException
	 */
	public Account getAccount(HttpSession session, Long id) throws AccountServiceException;
	
	/**
	 * Returns a list of all entities for the given session
	 * 
	 * @param session
	 * @return
	 * @throws AccountServiceException
	 */
	public List<Account> getAccounts(HttpSession session) throws AccountServiceException;

	/**
	 * Adds an account for the given session 
	 * 
	 * @param session
	 * @param account
	 * @return
	 * @throws AccountServiceException
	 */
	public Account addAccount(HttpSession session, Account account) throws AccountServiceException;
	
	/**
	 * Deletes an account for the given session
	 * 
	 * @param session
	 * @param id
	 * @throws AccountServiceException
	 */
	public void deleteAccount(HttpSession session, Long id) throws AccountServiceException;
	
	/**
	 * Deletes all accounts there are (regardless of session in services other then session-based)
	 * 
	 * @param session
	 * @throws AccountServiceException
	 */
	public void deleteAll(HttpSession session) throws AccountServiceException;

	/**
	 * Updares an account for the given session
	 * 
	 * @param session
	 * @param id
	 * @param account
	 * @return
	 * @throws AccountServiceException
	 */
	public Account updateAccount(HttpSession session, Long id, Account account) throws AccountServiceException;
	
}
