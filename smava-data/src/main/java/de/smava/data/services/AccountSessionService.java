package de.smava.data.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.smava.data.Account;
import de.smava.data.AccountServiceEntityNotFoundException;
import de.smava.data.AccountServiceException;
import de.smava.data.AccountServiceNonUniqueException;

/**
 * @author Tomek Samcik
 *
 * Service class providing functionality to manage Account entities 
 * using http session object
 */
@Service
public class AccountSessionService implements AccountService {
	
	private Logger log = Logger.getLogger(AccountSessionService.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private AtomicLong counter = new AtomicLong();
	
	/**
	 * Deserializes list of accounts from session
	 * 
	 * @param session					session to get accounts from
	 * @return							a list of accounts
	 * @throws IOException				deserialization error
	 */
	private List<Account> getFromSession(HttpSession session) throws IOException {
		if (session.getAttribute("accounts") == null) {
			session.setAttribute("accounts", "[]");
		}
		String deserialized = (String)session.getAttribute("accounts");
		JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Account.class);
		List<Account> accounts = mapper.readValue(deserialized, type);
		return accounts;
	}

	/**
	 * Serializes list of accounts and puts in session
	 * 
	 * @param session		sessopm tp push account to
	 * @param accounts		a list of accounts
	 * @throws IOException	serialization exception
	 */
	private void putInSession(HttpSession session, List<Account> accounts) throws IOException {
		if (session.getAttribute("accounts") == null) {
			session.setAttribute("accounts", "[]");
		}
		String serlialized = mapper.writeValueAsString(accounts);
		session.setAttribute("accounts", serlialized);
	}

	/* (non-Javadoc)
	 * @see de.smava.data.services.AccountService#getAccount(javax.servlet.http.HttpSession, java.lang.Long)
	 */
	@Override
	public Account getAccount(HttpSession session, Long id)
			throws AccountServiceException {
		try {
			List<Account> accounts = getFromSession(session); 
			List<Account> filtered = 
				accounts.stream()
				.filter(a -> a.getId().equals(id))
				.collect(Collectors.toList());
			if (!filtered.isEmpty()) {
				if (filtered.size() == 1) {
					Account account = filtered.get(0);
					log.info("account: " + account);
					return account;
				} else {
					throw new AccountServiceException("More then one entity found with the given id (" + id + ")");
				}
			} else {
				throw new AccountServiceEntityNotFoundException("Entity with the given id (" + id + ") not found");	
			}
		} catch (AccountServiceEntityNotFoundException e) {
			throw new AccountServiceEntityNotFoundException(e.getMessage());
		} catch (IOException e) {
			throw new AccountServiceException("Error serializing/deserializing accounts: " + e.getMessage());
		} catch (Exception e) {
			throw new AccountServiceException(e.getMessage());
		}
	}		
	
	/* (non-Javadoc)
	 * @see de.smava.data.AccountService#getAccounts(javax.servlet.http.HttpSession)
	 */
	public List<Account> getAccounts(HttpSession session) throws AccountServiceException {
		try {
			List<Account> accounts = getFromSession(session); 
			log.info("accounts: " + accounts);
			return accounts;
		} catch (IOException e) {
			throw new AccountServiceException("Error serializing/deserializing accounts: " + e.getMessage());
		} catch (Exception e) {
			throw new AccountServiceException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see de.smava.data.AccountService#addAccount(javax.servlet.http.HttpSession, de.smava.data.Account)
	 */
	public Account addAccount(HttpSession session, Account account) throws AccountServiceException {
		try {			
			account.setSessionId(session.getId());
			account.setId(counter.incrementAndGet());
			List<Account> accounts = getFromSession(session);
			List<Account> filtered = 
					accounts.stream()
					.filter(a -> a.getIban().equals(account.getIban()))
					.collect(Collectors.toList());
			System.out.println("filtered: " + filtered);
			if (filtered.isEmpty()) {
				accounts.add(account);
				log.info("added: " + accounts);
				putInSession(session, accounts);
				return account;				
			} else {
				throw new AccountServiceNonUniqueException("Non unique iban value (" + account.getIban() + ")");
			}
		} catch (AccountServiceNonUniqueException e) {
			throw new AccountServiceNonUniqueException(e.getMessage());
		} catch (IOException e) {
			throw new AccountServiceException("Error serializing/deserializing accounts: " + e.getMessage());
		} catch (Exception e) {
			throw new AccountServiceException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see de.smava.data.AccountService#deleteAccount(javax.servlet.http.HttpSession, java.lang.Long)
	 */
	public void deleteAccount(HttpSession session, Long id) throws AccountServiceException {
		try {
			List<Account> accounts = getFromSession(session);
			List<Account> filtered = 
				accounts.stream()
				.filter(a -> a.getId().equals(id))
				.collect(Collectors.toList());
			if (!filtered.isEmpty()) {
				if (filtered.size() == 1) {
					Account toBeDeleted = filtered.get(0);
					log.info("toBeDeleted: " + toBeDeleted);
					accounts.remove(toBeDeleted);
					putInSession(session, accounts);
				} else {
					throw new AccountServiceException("More then one entity found with the given id (" + id + ")");
				}
			} else {
				throw new AccountServiceEntityNotFoundException("Entity with the given id (" + id + ") not found");	
			}
		} catch (AccountServiceEntityNotFoundException e) {
			throw new AccountServiceEntityNotFoundException(e.getMessage());
		} catch (IOException e) {
			throw new AccountServiceException("Error serializing/deserializing accounts: " + e.getMessage());
		} catch (Exception e) {
			throw new AccountServiceException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see de.smava.data.AccountService#updateAccount(javax.servlet.http.HttpSession, java.lang.Long, de.smava.data.Account)
	 */
	public Account updateAccount(HttpSession session, Long id, Account account) throws AccountServiceException {
		try {
			List<Account> accounts = getFromSession(session);
			List<Account> filtered = 
				accounts.stream()
				.filter(a -> a.getId().equals(id))
				.collect(Collectors.toList());			
			if (!filtered.isEmpty()) {
				if (filtered.size() == 1) {
					Account toBeUpdated = filtered.get(0);
					accounts.remove(toBeUpdated);
					List<Account> filteredIban = 
						accounts.stream()
						.filter(a -> a.getIban().equals(account.getIban()))
						.collect(Collectors.toList());
					log.info("filteredIban: " + filteredIban);
					if (filteredIban.isEmpty()) {
						toBeUpdated.setBic(account.getBic());
						toBeUpdated.setIban(account.getIban());					
						log.info("toBeUpdated: " + toBeUpdated);
						putInSession(session, accounts);
						return toBeUpdated;
					} else {
						throw new AccountServiceNonUniqueException("Non unique iban value (" + account.getIban() + ")");
					}				
				} else {
					throw new AccountServiceException("More then one entity found with the given id (" + id + ")");
				}
			} else {
				throw new AccountServiceEntityNotFoundException("Entity with the given id (" + id + ") not found");
			}
		} catch (AccountServiceEntityNotFoundException e) {
			throw new AccountServiceEntityNotFoundException(e.getMessage());
		} catch (AccountServiceNonUniqueException e) {
			throw new AccountServiceNonUniqueException(e.getMessage());
		} catch (IOException e) {
			throw new AccountServiceException("Error serializing/deserializing accounts: " + e.getMessage());
		} catch (Exception e) {
			throw new AccountServiceException(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see de.smava.data.services.AccountService#deleteAll(javax.servlet.http.HttpSession)
	 */
	@Override
	public void deleteAll(HttpSession session) throws AccountServiceException {
		try {
			putInSession(session, new ArrayList<Account>());
		} catch (IOException e) {
			throw new AccountServiceException("Error serializing accounts");
		} catch (Exception e) {
			throw new AccountServiceException(e.getMessage());
		}
	}
	
}
