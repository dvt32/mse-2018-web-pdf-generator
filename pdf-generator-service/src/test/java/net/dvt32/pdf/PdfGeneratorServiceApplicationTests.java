package net.dvt32.pdf;

import static org.assertj.core.api.Assertions.assertThat;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PdfGeneratorServiceApplicationTests {

	TestRestTemplate restTemplate = new TestRestTemplate();
	static JmsTemplate jmsTemplate;
	
	@BeforeClass
	public static void setUp() {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
		MessageConverter messageConverter = new SimpleMessageConverter();
		jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(new SingleConnectionFactory(connectionFactory));
		jmsTemplate.setMessageConverter(messageConverter);
		jmsTemplate.setReceiveTimeout(1000);
	}
	
	/*
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
	*/
	
	@Test
	public void shouldSendMessageToJMSQueue() 
		throws InterruptedException 
	{
		jmsTemplate.convertAndSend("PDFQueryQueue", "abv.bg");
		assertThat( jmsTemplate.receiveAndConvert("PDFQueryQueue") ).isEqualTo("abv.bg");
	}
	
}
