package net.dvt32.pdf.query;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PdfQuery {
	
	/*
	 * Fields
	 */
	@GeneratedValue
	@Id
	private int id;
	
	private Date timeOfArrival;
	private String url;
	
	/*
	 * Constructors
	 */
	public PdfQuery() {}
	
	public PdfQuery(Date timeOfArrival, String url) {
		this.timeOfArrival = timeOfArrival;
		this.url = url;
	}
	
	/*
	 * Getters & setters
	 */
	public Date getTimeOfArrival() {
		return timeOfArrival;
	}

	public void setTimeOfArrival(Date timeOfArrival) {
		this.timeOfArrival = timeOfArrival;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * Helper methods
	 */
	@Override
	public String toString() {
		return String.format("PdfQuery [timeOfArrival=%s, url=%s]", timeOfArrival, url);
	}
	
}