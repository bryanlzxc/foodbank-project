package foodbank.reporting.service;

import java.util.List;
import java.util.Map;

import foodbank.packing.entity.PackingList;
import foodbank.reporting.dto.InvoiceDTO;
import foodbank.reporting.entity.InvoiceData;

public interface ReportService {

	List<InvoiceDTO> retrieveAllInvoices();
	
	InvoiceData retrieveInvoiceData(final String invoiceId);
	
	void updateInvoiceData(final Map<String, String> details);
	
	void generateDbInvoice(final PackingList packingList);
	
	void generateInvoicePDF(final String invoiceId);
	
}
