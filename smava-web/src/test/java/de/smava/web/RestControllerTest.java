package de.smava.web;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.smava.Application;

/**
 * Basic integration tests for JSP application.
 *
 * @author Phillip Webb
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class RestControllerTest {

	private TestRestTemplate rest = new TestRestTemplate();

	@Test
	public void testRest() throws Exception {
		ResponseEntity<String> entity = rest.getForEntity(
				"http://localhost:8080/account", String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}

}
