package de.smava.data.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.smava.data.Account;

/**
 * @author Tomek Samcik
 *
 * Service loading initial data for the given 
 * session
 */
@Service
public class AccountLoaderService {
	
	private Logger log = Logger.getLogger(AccountLoaderService.class);
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * Performing initial load for the given session
	 * 
	 * @param session http session
	 */
	public void load(HttpSession session) {
		try {
			accountService.addAccount(session, new Account("BREXPLPWKRA", "PL60 1020 1026 0000 0422 7020 1111", null));
			accountService.addAccount(session, new Account("BREXPLPWKRA", "PL61 1021 1026 0000 0422 7020 2222", null));
			accountService.addAccount(session, new Account("LOYDGB21006", "GB29 RBOS 6016 1331 9268 19", null));
			accountService.addAccount(session, new Account("LOYDGB21006", "GB30 RBOS 6016 1331 9268 20", null));
			accountService.addAccount(session, new Account("DEUTDEFFXXX", "DE89 3704 0044 0532 0130 00", null));
			accountService.addAccount(session, new Account("DEUTDEFFXXX", "DE90 3704 0044 0532 0130 01", null));
			
			List<Account> accounts = accountService.getAccounts(session);
			log.info("loaded: " + accounts);					
		} catch (Exception e) {
			log.error("Error occured during initial data load for session: " + session.getId());
		}
	}

}
