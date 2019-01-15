package net.dvt32.pdf;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.dvt32.pdf.query.PdfQuery;

@RestController
public class PdfServiceController {

	@Autowired
	JmsTemplate jmsTemplate;
	
	private static Logger logger = LoggerFactory.getLogger(PdfServiceController.class);
	
	@PostMapping(value ="/pdf-service")
	public void submitPdfQueryToJMS(@RequestBody String url) {
		logger.info("Received URL \"" + url + "\"");
		
		Date timeOfArrival = new Date();
		PdfQuery pdfQuery = new PdfQuery(timeOfArrival, url);
		String destinationQueue = "PDFQueryQueue";
		
		logger.info("Sending PDF query to JMS queue...");
		jmsTemplate.convertAndSend(
			destinationQueue,
			pdfQuery
		);
	}
	
}