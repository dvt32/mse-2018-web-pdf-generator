package net.dvt32.pdf;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PdfGeneratorServiceApplicationTests {
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	
	@Test
	public void shouldSendPdfFileToPdfWeb() {
		// Post URL to pdf-service
		restTemplate.postForEntity(
			"http://localhost:8100/pdf-service", 
			"abv.bg",
			null
		);
		
		// Check if pdf-generator-service has received & handled the URL 
		// and if pdf-web has received the file
		ResponseEntity<byte[]> pdfWebResponse  = restTemplate.getForEntity(
			"http://localhost:8000/pdf-web/pdf-file", 
			byte[].class
		);

		assertTrue( pdfWebResponse.getStatusCode() == HttpStatus.OK );
	}

}