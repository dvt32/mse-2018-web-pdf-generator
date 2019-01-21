package net.dvt32.pdf;

import static org.junit.Assert.assertTrue;

import org.apache.activemq.broker.BrokerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PdfServiceApplicationTests {
	
	@MockBean
	BrokerService broker;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	
	@Test
	public void shouldReturnOKStatusCode() {
		ResponseEntity response = restTemplate.postForEntity(
			"http://localhost:8100/pdf-service", 
			"google.bg",
			null
		);
		assertTrue( response.getStatusCode() == HttpStatus.OK );
	}
	
}