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
public class AccountPersistentServiceNullSessionTest {

	private Logger log = Logger
			.getLogger(AccountPersistentServiceNullSessionTest.class);

	@InjectMocks
	MockHttpSession session = new MockHttpSession();

	@Autowired
	@Qualifier("accountPersistentService")
	private AccountService accountPersistentService;

	@Before
	public void purge() throws AccountServiceException {
		accountPersistentService.deleteAll(null);
	}

	@Test
	public void getAccounts() throws AccountServiceException {
		List<Account> all = accountPersistentService.getAccounts(null);
		log.info("all: " + all);
		assertNotNull(all);
	}

	@Test
	public void getAccount() throws AccountServiceException,
			AccountServiceNonUniqueException,
			AccountServiceEntityNotFoundException {
		Account added = accountPersistentService.addAccount(null, new Account(
				"A", "1", null));
		Account returned = accountPersistentService.getAccount(null,
				added.getId());
		log.info("returned: " + returned);
		assertEquals(added, returned);
	}

	@Test
	public void addAccount() throws AccountServiceException,
			AccountServiceNonUniqueException {
		accountPersistentService.addAccount(null, new Account("A", "1", null));
		List<Account> all = accountPersistentService.getAccounts(null);
		log.info("all: " + all);
		assertEquals(all.size(), 1);
	}

	@Test
	public void updateAccount() throws AccountServiceException,
			AccountServiceNonUniqueException,
			AccountServiceEntityNotFoundException {
		Account added = accountPersistentService.addAccount(null, new Account(
				"A", "1", null));
		Account updated = accountPersistentService.updateAccount(null,
				added.getId(), new Account("B", "1", null));
		List<Account> all = accountPersistentService.getAccounts(null);
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
		log.info("delete: ");
		Account added = accountPersistentService.addAccount(null, new Account(
				"A", "1", null));
		List<Account> before = accountPersistentService.getAccounts(null);
		accountPersistentService.deleteAccount(null, added.getId());
		List<Account> after = accountPersistentService.getAccounts(null);
		log.info("before: " + before);
		log.info("after: " + after);
		assertEquals(before.size(), 1);
		assertEquals(after.size(), 0);
	}

	@Test(expected = AccountServiceNonUniqueException.class)
	public void unique() throws AccountServiceException,
			AccountServiceNonUniqueException {
		accountPersistentService.addAccount(session,
				new Account("A", "1", null));
		accountPersistentService.addAccount(session,
				new Account("A", "1", null));
	}

	@Test(expected = AccountServiceEntityNotFoundException.class)
	public void updateNotFound() throws AccountServiceException,
			AccountServiceEntityNotFoundException,
			AccountServiceNonUniqueException {
		accountPersistentService.updateAccount(null, 99l, new Account("A", "1",
				null));
	}

	@Test(expected = AccountServiceEntityNotFoundException.class)
	public void deleteNotFound() throws AccountServiceException,
			AccountServiceEntityNotFoundException {
		accountPersistentService.deleteAccount(null, 99l);
	}

	@Test(expected = AccountServiceEntityNotFoundException.class)
	public void getNotFound() throws AccountServiceException,
			AccountServiceEntityNotFoundException {
		accountPersistentService.getAccount(null, 99l);
	}

}