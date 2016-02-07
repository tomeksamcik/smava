package de.smava.jms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.smava.Application;
import de.smava.data.Account;

/**
 * @author Tomek Samcik
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AccountReceiverTest {
	
    @Autowired
    private AccountReceiver accountReceiver;

    @Autowired
    private AccountSender accountSender;
    
    @Test
    public void sendSimpleMessage() {
    	accountSender.send(new Account("AAA", "111", null));
        
        Optional<Account> received = accountReceiver.getLastReceived();
        
        assertTrue(received.isPresent());
        assertEquals("111", received.get().getIban());
    }
    
}