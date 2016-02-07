package de.smava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import de.smava.data.services.AccountPersistentService;
import de.smava.data.services.AccountService;

/**
 * @author Tomek Samcik
 *
 */
@Configuration 
@EnableAutoConfiguration 
@ComponentScan("de.smava.*")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public AccountService accountService() {
		return new AccountPersistentService();
    }    
    
}