package de.smava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.smava.data.services.AccountPersistentService;
import de.smava.data.services.AccountService;

/**
 * @author Tomek Samcik
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public AccountService accountService() {
		return new AccountPersistentService();
	}

}