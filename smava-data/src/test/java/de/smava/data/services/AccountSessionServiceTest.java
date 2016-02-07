package de.smava.data.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.smava.Application;
import de.smava.data.Account;
import de.smava.data.AccountServiceEntityNotFoundException;
import de.smava.data.AccountServiceException;
import de.smava.data.AccountServiceNonUniqueException;

/**
 * @author Tomek Samcik
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class AccountSessionServiceTest {

	private Logger log = Logger.getLogger(AccountSessionServiceTest.class);

	@Autowired
	@Qualifier("accountSessionService")
	private AccountService accountSessionService;

	@InjectMocks
	MockHttpSession session = new MockHttpSession();

	@Before
	public void purge() throws AccountServiceException {
		log.info("session: " + session.getId());
		accountSessionService.deleteAll(session);
	}

	@Test
	public void getAccounts() throws AccountServiceException {
		List<Account> all = accountSessionService.getAccounts(session);
		log.info("all: " + all);
		assertNotNull(all);
	}

	@Test
	public void getAccount() throws AccountServiceException,
			AccountServiceNonUniqueException,
			AccountServiceEntityNotFoundException {
		Account added = accountSessionService.addAccount(session, new Account(
				"A", "1", null));
		Account returned = accountSessionService.getAccount(session,
				added.getId());
		log.info("returned: " + returned);
		assertEquals(added, returned);
	}

	@Test
	public void addAccount() throws AccountServiceException,
			AccountServiceNonUniqueException {
		accountSessionService.addAccount(session, new Account("A", "1", null));
		List<Account> all = accountSessionService.getAccounts(session);
		log.info("all: " + all);
		assertEquals(all.size(), 1);
	}

	@Test
	public void updateAccount() throws AccountServiceException,
			AccountServiceNonUniqueException,
			AccountServiceEntityNotFoundException {
		log.info("updateAccount");
		Account added = accountSessionService.addAccount(session, new Account(
				"A", "1", null));
		Account updated = accountSessionService.updateAccount(session,
				added.getId(), new Account("B", "1", null));
		List<Account> all = accountSessionService.getAccounts(session);
		log.info("added: " + added);
		log.info("updated: " + updated);
		log.info("all: " + all);
		assertEquals(added.getId(), updated.getId());
		assertEquals(added.getIban(), updated.getIban());
		assertEquals(added.getBic(), "A");
		assertEquals(updated.getBic(), "B");
	}

	@Test
	public void deleteAccount() throws AccountServiceException,
			AccountServiceNonUniqueException,
			AccountServiceEntityNotFoundException {
		Account added = accountSessionService.addAccount(session, new Account(
				"A", "1", null));
		List<Account> before = accountSessionService.getAccounts(session);
		accountSessionService.deleteAccount(session, added.getId());
		List<Account> after = accountSessionService.getAccounts(session);
		log.info("before: " + before);
		log.info("after: " + after);
		assertEquals(before.size(), 1);
		assertEquals(after.size(), 0);
	}

	@Test(expected = AccountServiceNonUniqueException.class)
	public void unique() throws AccountServiceException,
			AccountServiceNonUniqueException {
		accountSessionService.addAccount(session, new Account("A", "1", null));
		accountSessionService.addAccount(session, new Account("A", "1", null));
	}

	@Test(expected = AccountServiceEntityNotFoundException.class)
	public void updateNotFound() throws AccountServiceException,
			AccountServiceEntityNotFoundException,
			AccountServiceNonUniqueException {
		accountSessionService.updateAccount(session, 99l, new Account("A", "1",
				null));
	}

	@Test(expected = AccountServiceEntityNotFoundException.class)
	public void deleteNotFound() throws AccountServiceException,
			AccountServiceEntityNotFoundException {
		accountSessionService.deleteAccount(session, 99l);
	}

	@Test(expected = AccountServiceEntityNotFoundException.class)
	public void getNotFound() throws AccountServiceException,
			AccountServiceEntityNotFoundException {
		accountSessionService.getAccount(session, 99l);
	}

}