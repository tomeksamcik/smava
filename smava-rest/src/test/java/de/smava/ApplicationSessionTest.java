package de.smava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.smava.data.services.AccountService;
import de.smava.data.services.AccountSessionService;

/**
 * @author Tomek Samcik
 *
 */
@SpringBootApplication
public class ApplicationSessionTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationSessionTest.class, args);
    }
    
    @Bean
    public AccountService accountService() {
		return new AccountSessionService();
    }    
    
}