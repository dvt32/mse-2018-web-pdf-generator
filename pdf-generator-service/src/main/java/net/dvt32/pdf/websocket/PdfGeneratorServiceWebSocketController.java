package net.dvt32.pdf.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PdfGeneratorServiceWebSocketController {
	
	private static Logger logger = LoggerFactory.getLogger(PdfGeneratorServiceWebSocketController.class);
	
	@MessageMapping("/pdf-message")
    @SendTo("/pdf-messages")
    public String pdfMessage(String message) throws Exception {
		logger.info("Sending PDF message...");
        Thread.sleep(3000); // simulated delay
        return message;
    }
	
}