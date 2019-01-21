package net.dvt32.pdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

@Controller
public class PdfWebController {
	
	/*
	 * Fields
	 */
	@Autowired 
	private RestTemplate restTemplate;
	
	private static byte[] pdfFileBytes = null;
	private static Logger logger = LoggerFactory.getLogger(PdfWebController.class);
	
	/*
	 * Methods
	 */
	@GetMapping("/pdf-web")
	public String index() {
		return "index";
	}
	
	@PostMapping("/pdf-web")
	public String submitUrlToPdfService(String url) {
		logger.info("Sending URL to pdf-service...");
		restTemplate.postForLocation("http://localhost:8100/pdf-service", url);
		return "get-pdf";
	}

	@GetMapping("/pdf-web/pdf-file")
	public ResponseEntity<byte[]> savePdfFile() {
		String fileName = "file.pdf";

	    HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("charset", "utf-8");
	    responseHeaders.setContentType(MediaType.APPLICATION_PDF);
	    responseHeaders.setContentLength(pdfFileBytes.length);
	    responseHeaders.set("Content-disposition", "attachment; filename=" + fileName);
	    
	    ResponseEntity<byte[]> pdfFile = new ResponseEntity<byte[]>(pdfFileBytes, responseHeaders, HttpStatus.OK);
	    
	    return pdfFile;
	}
	
	@PostMapping("/pdf-web/pdf-file")
	@ResponseStatus(value = HttpStatus.OK)
	public void setPdfFileBytes(HttpEntity<byte[]> entity) {
		logger.info("Received entity: " + entity);
		pdfFileBytes = entity.getBody();
	}
	
}