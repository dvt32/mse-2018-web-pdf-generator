package net.dvt32.pdf.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PdfQueryReceiver {
	
	@Autowired
	private PdfQueryRepository pdfQueryRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private static Logger logger = LoggerFactory.getLogger(PdfQueryReceiver.class);

	@JmsListener(destination="PDFQueryQueue")
	public void receivePdfQuery(PdfQuery pdfQuery) {
		logger.info("Received PDF query: " + pdfQuery);
		
		logger.info("Now saving PDF query to database...");
		pdfQueryRepository.save(pdfQuery);
		
		logger.info("Sending PDF query's URL to pdf-generator-service...");
		restTemplate.getForObject(
			"http://localhost:8200/pdf-generator-service/generate-pdf?url=" + pdfQuery.getUrl(), 
			Void.class
		);
	}

}