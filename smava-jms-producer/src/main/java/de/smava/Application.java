package de.smava;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

import de.smava.data.services.AccountPersistentService;
import de.smava.data.services.AccountService;

/**
 * @author Tomek Samcik
 *
 */
@SpringBootApplication
@EnableJms
public class Application {
	
    @Bean
    public ConnectionFactory connectionFactory() {
    	ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
    	factory.setTrustAllPackages(true);
        return factory;
    }
    
    @Bean
    public AccountService accountService() {
		return new AccountPersistentService();
    }        

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}