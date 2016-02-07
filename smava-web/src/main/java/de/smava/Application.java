package de.smava;

import static springfox.documentation.builders.PathSelectors.regex;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import de.smava.data.services.AccountPersistentService;
import de.smava.data.services.AccountService;

/**
 * @author Tomek Samcik
 *
 */
@EnableJms
@EnableSwagger2
@EnableScheduling
@SpringBootApplication
@ComponentScan({"de.smava.*"})
public class Application extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
    /**
     * Configuring proper service to handle CRUD
     * operations
     * 
     * @return AccountService to work with the endpoint
     */
    @Bean
    public AccountService accountService() {
		return new AccountPersistentService();
    }
    
    @Bean
    public ConnectionFactory connectionFactory() {
    	ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
    	factory.setTrustAllPackages(true);
        return factory;
    }	    
    
    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("account")
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/account.*"))
                .build();
    }
     
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring REST Sample with Swagger")
                .description("Spring REST Sample with Swagger")
                .contact("Tomasz Samcik")
                .version("1.0")
                .build();
    }    

}
