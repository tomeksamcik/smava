package de.smava.jms;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import de.smava.data.Account;

/**
 * @author Tomek Samcik
 * 
 *         Sending Account data to JMS feed
 */
@Component
public class AccountSender {

	public static final String ACCOUNTS_QUEUE = "accounts.queue";

	private Logger log = Logger.getLogger(AccountSender.class);

	@Autowired
	private JmsTemplate jmsTemplate;

	public void send(Account account) {
		log.info("Sending an account: " + account);

		jmsTemplate.convertAndSend(ACCOUNTS_QUEUE, account);
	}

}
