package de.smava.data.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import de.smava.data.Account;
import de.smava.data.AccountRepository;
import de.smava.data.AccountServiceEntityNotFoundException;
import de.smava.data.AccountServiceException;
import de.smava.data.AccountServiceNonUniqueException;

/**
 * @author Tomek Samcik
 *
 * Service class providing functionality to manage Account entities
 * using persistent storage
 */
@Service
public class AccountPersistentService implements AccountService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	/* (non-Javadoc)
	 * @see de.smava.data.AccountService#getAccounts(javax.servlet.http.HttpSession)
	 */	
	public List<Account> getAccounts(HttpSession session) throws AccountServiceException {		
		try {
			Optional<HttpSession> sessionOrNull = Optional.ofNullable(session);
			return accountRepository.findAllBySessionId(
				sessionOrNull
					.map(s -> s.getId())
					.orElse(null));
		} catch (Exception e) {
			throw new AccountServiceException(e.getMessage());			
		}
	}
	
	/* (non-Javadoc)
	 * @see de.smava.data.services.AccountService#getAccount(javax.servlet.http.HttpSession, java.lang.Long)
	 */
	@Override
	public Account getAccount(HttpSession session, Long id)
			throws AccountServiceException {
		try {
			Optional<HttpSession> sessionOrNull = Optional.ofNullable(session);
			Account account = accountRepository.findByIdAndSessionId(id, 
				sessionOrNull
					.map(s -> s.getId())
					.orElse(null));
			if (account != null) {
				return account; 				
			} else {
				throw new AccountServiceEntityNotFoundException("Entity with the given id (" + id + ") not found");				
			}
		} catch (AccountServiceEntityNotFoundException e) {
			throw new AccountServiceEntityNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new AccountServiceException(e.getMessage());			
		}
	}		

	/* (non-Javadoc)
	 * @see de.smava.data.AccountService#addAccount(javax.servlet.http.HttpSession, de.smava.data.Account)
	 */
	public Account addAccount(HttpSession session, Account account) throws AccountServiceException {		
		try {
			Optional<HttpSession> sessionOrNull = Optional.ofNullable(session);
			account.setSessionId(
				sessionOrNull
					.map(s -> s.getId())
					.orElse(null));
			return accountRepository.save(account);
		} catch (DataIntegrityViolationException e) {
			throw new AccountServiceNonUniqueException("Non unique iban value (" + account.getIban() + ")");
		} catch (Exception e) {
			throw new AccountServiceException(e.getMessage());			
		}
	}

	/* (non-Javadoc)
	 * @see de.smava.data.AccountService#deleteAccount(javax.servlet.http.HttpSession, java.lang.Long)
	 */
	public void deleteAccount(HttpSession session, Long id) throws AccountServiceException {
		try {
			if (id != null) {
				Optional<HttpSession> sessionOrNull = Optional.ofNullable(session);
				Account toBeDeleted = accountRepository.findByIdAndSessionId(id, 
					sessionOrNull
						.map(s -> s.getId())
						.orElse(null));
				if (toBeDeleted != null) {
					accountRepository.delete(toBeDeleted);					
				} else {
					throw new AccountServiceEntityNotFoundException("Entity with the given id (" + id + ") not found");
				}
			} else {
				throw new AccountServiceException("Id has to be specified for delete operation");
			}			
		} catch (AccountServiceEntityNotFoundException e) {
			throw new AccountServiceEntityNotFoundException(e.getMessage());			
		} catch (Exception e) {
			throw new AccountServiceException(e.getMessage());			
		}
	}

	/* (non-Javadoc)
	 * @see de.smava.data.AccountService#updateAccount(javax.servlet.http.HttpSession, java.lang.Long, de.smava.data.Account)
	 */
	public Account updateAccount(HttpSession session, Long id, Account account) throws AccountServiceException {
		try {
			if (id != null ||
					account != null) {
				Optional<HttpSession> sessionOrNull = Optional.ofNullable(session);
				Account toBeUpdated = accountRepository.findByIdAndSessionId(id, 
					sessionOrNull
						.map(s -> s.getId())
						.orElse(null));
				if (toBeUpdated != null) {
					toBeUpdated.setBic(account.getBic());
					toBeUpdated.setIban(account.getIban());
					Account updated = accountRepository.save(toBeUpdated);
					return updated;								
				} else {
					throw new AccountServiceEntityNotFoundException("Entity with the given id (" + id + ") not found");
				}
			} else {
				throw new AccountServiceException("Id and account have to be specified for update operation");
			}			
		} catch (DataIntegrityViolationException e) {
			throw new AccountServiceNonUniqueException("Non unique iban value (" + account.getIban() + ")");
		} catch (AccountServiceEntityNotFoundException e) {
			throw new AccountServiceEntityNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new AccountServiceException(e.getMessage());			
		}
	}
	
	/* (non-Javadoc)
	 * @see de.smava.data.services.AccountService#deleteAll(javax.servlet.http.HttpSession)
	 */
	public void deleteAll(HttpSession session) throws AccountServiceException {
		try {
			accountRepository.deleteAll();
		} catch (Exception e) {
			throw new AccountServiceException(e.getMessage());			
		}
	}

}
