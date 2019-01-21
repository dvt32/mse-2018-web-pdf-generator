package net.dvt32.pdf;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.dvt32.pdf.query.PdfQuery;
import net.dvt32.pdf.query.PdfQueryRepository;

@RestController
public class PdfServiceController {

	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	PdfQueryRepository pdfQueryRepository;
	
	private static Logger logger = LoggerFactory.getLogger(PdfServiceController.class);
	
	@PostMapping(value ="/pdf-service")
	public ResponseEntity submitPdfQueryToDatabaseAndSendUrlToJmsQueue(@RequestBody String url) {
		logger.info("Received URL \"" + url + "\"");
		
		Date timeOfArrival = new Date();
		PdfQuery pdfQuery = new PdfQuery(timeOfArrival, url);
		logger.info("Now saving PDF query to database...");
		pdfQueryRepository.save(pdfQuery);
		
		String destinationQueueName = "PDFQueryQueue";
		logger.info("Sending PDF query's URL to JMS queue...");
		jmsTemplate.convertAndSend(
			destinationQueueName,
			url
		);
		
		return ResponseEntity.ok().build();
	}
	
}