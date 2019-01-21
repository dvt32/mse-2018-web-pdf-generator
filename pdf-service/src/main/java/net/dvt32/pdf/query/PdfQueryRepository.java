package net.dvt32.pdf.query;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PdfQueryRepository extends JpaRepository<PdfQuery, Integer> {
	// Auto-implemented by Spring Boot
}