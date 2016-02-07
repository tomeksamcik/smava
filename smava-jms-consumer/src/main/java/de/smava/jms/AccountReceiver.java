package de.smava.jms;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import de.smava.data.Account;

/**
 * @author Tomek Samcik
 *
 * JMS receiver class consuming messages
 * from JMS feed
 */
@Component
public class AccountReceiver {
	
	public static final String ACCOUNTS_QUEUE = "accounts.queue";
	
	private Logger log = Logger.getLogger(AccountReceiver.class);
	
	private Account lastReceived;

    @JmsListener(destination = ACCOUNTS_QUEUE)
    public void receiveAccount(Account account) {
    	log.info("Received: " + account);
    	
    	lastReceived = account;
    }

	public Optional<Account> getLastReceived() {
		return Optional.ofNullable(lastReceived);
	}

}