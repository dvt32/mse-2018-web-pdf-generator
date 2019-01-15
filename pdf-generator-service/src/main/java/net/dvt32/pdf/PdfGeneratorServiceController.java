package net.dvt32.pdf;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.woo.htmltopdf.HtmlToPdf;
import io.woo.htmltopdf.HtmlToPdfObject;
import net.dvt32.pdf.websocket.PdfGeneratorServiceWebSocketClient;

@RestController
public class PdfGeneratorServiceController {
	
	@Autowired
	RestTemplate restTemplate;
	
	private static Logger logger = LoggerFactory.getLogger(PdfGeneratorServiceController.class);
	
	@RequestMapping("/pdf-generator-service/generate-pdf")
	public void generatePdfFromUrl(@RequestParam String url) 
		throws IOException, InterruptedException, ExecutionException 
	{
		logger.info("Generating PDF file from url \"" + url + "\"");
		InputStream inputStream = HtmlToPdf.create()
			.object( HtmlToPdfObject.forUrl(url) )
			.convert();
		
		logger.info("Sending PDF file to pdf-web...");
		byte[] pdfFileBytes = new byte[inputStream.available()];
	    inputStream.read(pdfFileBytes);
		HttpEntity<byte[]> entity = new HttpEntity<>(pdfFileBytes);
		restTemplate.postForLocation("http://localhost:8000/pdf-web/pdf-file", entity);
		
		PdfGeneratorServiceWebSocketClient client = new PdfGeneratorServiceWebSocketClient();
        ListenableFuture<StompSession> f = client.connect();
        StompSession stompSession = f.get();
        String message = "PDF file for URL \"" + url + "\" is ready...";
        client.send(message, stompSession);
	}
	
}