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
	 *            session to fetch account from
	 * @param id
	 *            id of account to fetch
	 * @return an account
	 * @throws AccountServiceException
	 *             a generic service layer exception
	 */
	public Account getAccount(HttpSession session, Long id)
			throws AccountServiceException;

	/**
	 * Returns a list of all entities for the given session
	 * 
	 * @param session
	 *            a session to get accounts from
	 * @return a list of accounts
	 * @throws AccountServiceException
	 *             a generic service layer exception
	 */
	public List<Account> getAccounts(HttpSession session)
			throws AccountServiceException;

	/**
	 * Adds an account for the given session
	 * 
	 * @param session
	 *            a session to add account to
	 * @param account
	 *            an account to add
	 * @return an added account
	 * @throws AccountServiceException
	 *             a service layer exception
	 */
	public Account addAccount(HttpSession session, Account account)
			throws AccountServiceException;

	/**
	 * Deletes an account for the given session
	 * 
	 * @param session
	 *            a session to delete accounts from
	 * @param id
	 *            an id of the account to delete
	 * @throws AccountServiceException
	 *             a generic service layer exception
	 */
	public void deleteAccount(HttpSession session, Long id)
			throws AccountServiceException;

	/**
	 * Deletes all accounts there are (regardless of session in services other
	 * then session-based)
	 * 
	 * @param session
	 *            a session from which delete all accounts
	 * @throws AccountServiceException
	 *             a generic service layer exception
	 */
	public void deleteAll(HttpSession session) throws AccountServiceException;

	/**
	 * Updares an account for the given session
	 * 
	 * @param session
	 *            a session to update account for
	 * @param id
	 *            an id of the account to update
	 * @param account
	 *            an account to update
	 * @return an updated account
	 * @throws AccountServiceException
	 *             a generic service layer exception
	 */
	public Account updateAccount(HttpSession session, Long id, Account account)
			throws AccountServiceException;

}
