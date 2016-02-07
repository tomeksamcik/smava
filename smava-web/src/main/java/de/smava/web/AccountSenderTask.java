package de.smava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import de.smava.data.Account;
import de.smava.data.AccountRepository;
import de.smava.jms.AccountSender;

/**
 * @author Tomek Samcik
 *
 * A scheduler class executing flush accounts task
 */
@Component
public class AccountSenderTask {
	
    @Autowired
    private AccountSender accountSender;

    @Autowired
    private AccountRepository accountRepository;
    
    /**
     * FLushing accounts every 10 seconds
     */
    @Scheduled(fixedRate = 10000)
    public void flush() {
    	for (Account account : accountRepository.findAll()) {
        	accountSender.send(account);    		
    	}
    }
    
}