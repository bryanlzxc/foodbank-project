package foodbank.reporting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import foodbank.reporting.entity.Invoice;

public interface InvoiceRepository extends MongoRepository<Invoice, String> {
	
	Invoice findById(String id);

}
