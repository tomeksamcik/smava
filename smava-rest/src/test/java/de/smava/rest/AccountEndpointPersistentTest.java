package de.smava.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.smava.ApplicationPersistentTest;
import de.smava.data.Account;
import de.smava.data.AccountRepository;

/**
 * @author Tomek Samcik
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=ApplicationPersistentTest.class)
@WebIntegrationTest("server.port:8080")
public class AccountEndpointPersistentTest {
	
	private Logger log = Logger.getLogger(AccountEndpointPersistentTest.class);
	
	private final String URL = "http://localhost:8080/account";

	//Test RestTemplate to invoke the APIs.
	private RestTemplate restTemplate = new TestRestTemplate();
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Test
	public void get() throws JsonProcessingException{
		Object response = restTemplate.getForObject(URL, Object.class);
		log.info("get response: " + response);
		assertNotNull(response);
	}

	@Test
	public void put() throws JsonProcessingException{
		List<Account> all = accountRepository.findAll();
		Long toBeUpdated = all.get(all.size() - 1).getId() + 1;
		log.info("toBeUpdated: " + toBeUpdated);		
		
		HttpEntity<Account> requestEntity = new HttpEntity<Account>(new Account("AAA", "234", null));
		ResponseEntity<Object> responseEntity = 
				restTemplate.exchange(URL + "/" + toBeUpdated, HttpMethod.PUT, requestEntity, Object.class);

		HttpStatus status = responseEntity.getStatusCode();
		log.info("put status: " + status);
		log.info("put response: " + responseEntity.getBody());
		assertTrue(status.is2xxSuccessful());
	}

	@Test
	public void putNonExisting() throws JsonProcessingException{
		HttpEntity<Account> requestEntity = new HttpEntity<Account>(new Account("AAA", "235", null));
		ResponseEntity<Object> responseEntity = 
				restTemplate.exchange(URL + "/99", HttpMethod.PUT, requestEntity, Object.class);

		HttpStatus status = responseEntity.getStatusCode();
		log.info("putNonExisting status: " + status);
		log.info("putNonExisting response: " + responseEntity.getBody());
		assertEquals(status, HttpStatus.NOT_FOUND);
	}

	@Test
	public void putNonUnique() throws JsonProcessingException{
		HttpEntity<Account> requestEntity = new HttpEntity<Account>(new Account("AAA", "PL60 1020 1026 0000 0422 7020 1111", null));
		ResponseEntity<Object> responseEntity = 
				restTemplate.exchange(URL + "/2", HttpMethod.PUT, requestEntity, Object.class);

		HttpStatus status = responseEntity.getStatusCode();
		log.info("putNonUnique status: " + status);
		log.info("putNonUnique response: " + responseEntity.getBody());
		assertEquals(status, HttpStatus.CONFLICT);
	}
	
	@Test
	public void post() throws JsonProcessingException{
		HttpEntity<Account> requestEntity = new HttpEntity<Account>(new Account("BBB", "123", null));
		ResponseEntity<Object> responseEntity = 
				restTemplate.exchange(URL, HttpMethod.POST, requestEntity, Object.class);

		HttpStatus status = responseEntity.getStatusCode();
		log.info("post status: " + status);
		log.info("post response: " + responseEntity.getBody());
		assertTrue(status.is2xxSuccessful());
	}

	@Test
	public void postNonUnique() throws JsonProcessingException{
		HttpEntity<Account> requestEntity = new HttpEntity<Account>(new Account("AAA", "PL60 1020 1026 0000 0422 7020 1111", null));
		ResponseEntity<Object> responseEntity = 
				restTemplate.exchange(URL, HttpMethod.POST, requestEntity, Object.class);

		HttpStatus status = responseEntity.getStatusCode();
		log.info("postNonUnique status: " + status);
		log.info("postNonUnique response: " + responseEntity.getBody());
		assertEquals(status, HttpStatus.CONFLICT);
	}
	
	@Test	
	public void delete() throws JsonProcessingException{
		List<Account> all = accountRepository.findAll();
		Long toBeDeleted = all.get(all.size() - 1).getId() + 1;
		log.info("toBeDeleted: " + toBeDeleted);

		ResponseEntity<Object> responseEntity = 
				restTemplate.exchange(URL + "/" + toBeDeleted, HttpMethod.DELETE, null, Object.class);

		HttpStatus status = responseEntity.getStatusCode();
		log.info("delete status: " + status);
		log.info("delete response: " + responseEntity.getBody());
		assertTrue(status.is2xxSuccessful());
	}

	@Test
	public void deleteNonExisting() throws JsonProcessingException{
		ResponseEntity<Object> responseEntity = 
				restTemplate.exchange(URL + "/99", HttpMethod.DELETE, null, Object.class);

		HttpStatus status = responseEntity.getStatusCode();
		log.info("deleteNonExisting status: " + status);
		log.info("deleteNonExisting response: " + responseEntity.getBody());
		assertEquals(status, HttpStatus.NOT_FOUND);
	}
	
	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void putBlank() throws JsonProcessingException{
		List all = restTemplate.getForObject(URL, List.class);
		LinkedHashMap<String, Object> last = (LinkedHashMap<String, Object>)all.get(all.size() - 1);
		Integer toBeUpdated = (Integer)last.get("id") + 1;
		log.info("toBeUpdated: " + toBeUpdated);		
		
		HttpEntity<Account> requestEntity = new HttpEntity<Account>(new Account("", "234", null));
		ResponseEntity<Object> responseEntity = 
				restTemplate.exchange(URL + "/" + toBeUpdated, HttpMethod.PUT, requestEntity, Object.class);

		HttpStatus status = responseEntity.getStatusCode();
		log.info("put status: " + status);
		log.info("put response: " + responseEntity.getBody());
		assertTrue(status.is4xxClientError());
	}
	
	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void putNull() throws JsonProcessingException{
		List all = restTemplate.getForObject(URL, List.class);
		LinkedHashMap<String, Object> last = (LinkedHashMap<String, Object>)all.get(all.size() - 1);
		Integer toBeUpdated = (Integer)last.get("id") + 1;
		log.info("toBeUpdated: " + toBeUpdated);		
		
		HttpEntity<Account> requestEntity = new HttpEntity<Account>(new Account(null, "234", null));
		ResponseEntity<Object> responseEntity = 
				restTemplate.exchange(URL + "/" + toBeUpdated, HttpMethod.PUT, requestEntity, Object.class);

		HttpStatus status = responseEntity.getStatusCode();
		log.info("put status: " + status);
		log.info("put response: " + responseEntity.getBody());
		assertTrue(status.is4xxClientError());
	}	

	@Test
	public void postBlank() throws JsonProcessingException{
		HttpEntity<Account> requestEntity = new HttpEntity<Account>(new Account("BBB", "", null));
		ResponseEntity<Object> responseEntity = 
				restTemplate.exchange(URL, HttpMethod.POST, requestEntity, Object.class);

		HttpStatus status = responseEntity.getStatusCode();
		log.info("post status: " + status);
		log.info("post response: " + responseEntity.getBody());
		assertTrue(status.is4xxClientError());
	}

	@Test
	public void postNull() throws JsonProcessingException{
		HttpEntity<Account> requestEntity = new HttpEntity<Account>(new Account("BBB", null, null));
		ResponseEntity<Object> responseEntity = 
				restTemplate.exchange(URL, HttpMethod.POST, requestEntity, Object.class);

		HttpStatus status = responseEntity.getStatusCode();
		log.info("post status: " + status);
		log.info("post response: " + responseEntity.getBody());
		assertTrue(status.is4xxClientError());
	}	
	
}