package de.smava.data.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import de.smava.ApplicationPersistentTest;
import de.smava.data.Account;
import de.smava.data.AccountServiceException;

/**
 * @author Tomek Samcik
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationPersistentTest.class)
@WebAppConfiguration
public class AccountLoaderServicePersistentTest {

	private Logger log = Logger
			.getLogger(AccountLoaderServicePersistentTest.class);

	@Autowired
	private AccountLoaderService accountLoaderService;

	@Autowired
	private AccountService accountService;

	@InjectMocks
	MockHttpSession session = new MockHttpSession();

	@Test
	public void load() throws AccountServiceException {
		accountLoaderService.load(session);
		List<Account> all = accountService.getAccounts(session);
		log.info("all: " + all);
		assertNotNull(all);
		assertEquals(all.size(), 6);
	}

}