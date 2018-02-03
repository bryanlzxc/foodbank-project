package foodbank.reporting.entity;

import java.util.ArrayList;
import java.util.List;

import foodbank.packing.entity.PackedFoodItem;

public class InvoiceData {
	
	private String invoiceNumber;
	private String packedDate;
	
	private String deliveryDate;
	private String deliveryTime;
	private String issuedBy;
	private String comments;
	
	private boolean deliveryRequired;
	private boolean reportGenerated;

	private String organizationToBill;
	private String organizationToBillAddress;
	private String organizationToBillContactPerson;
	private String organizationToBillContactNumber;
	
	private String receivingOrganization;
	private String receivingOrganizationAddress;
	private String receivingOrganizationContactPerson;
	private String receivingOrganizationContactNumber;
	
	private List<InvoiceLineItem> invoiceLineItem;
	
	private double combinedTotalValue = 0;
	
	public InvoiceData() {}
	
	public InvoiceData(Invoice invoice) {
		this.invoiceNumber = invoice.getId();
		this.packedDate = invoice.getGenerationDate();
		this.deliveryDate = invoice.getDeliveryDate();
		this.deliveryTime = invoice.getDeliveryTime();
		this.issuedBy = invoice.getIssuedBy();
		this.comments = invoice.getComments();
		this.deliveryRequired = invoice.getDeliveryStatus();
		this.reportGenerated = invoice.getGenerationStatus();
		this.organizationToBill = invoice.getBillingOrganization().getUser().getName();
		this.organizationToBillAddress = invoice.getBillingOrganization().getAddress();
		this.organizationToBillContactPerson = invoice.getBillingOrganization().getContactPerson();
		this.organizationToBillContactNumber = invoice.getBillingOrganization().getContactNumber();
		this.receivingOrganization = invoice.getReceivingOrganization().getUser().getName();
		this.receivingOrganizationAddress = invoice.getReceivingOrganization().getAddress();
		this.receivingOrganizationContactPerson = invoice.getReceivingOrganization().getContactPerson();
		this.receivingOrganizationContactNumber = invoice.getReceivingOrganization().getContactNumber();
		this.invoiceLineItem =  generateInvoiceLineItemData(invoice.getPackedItems());
		calculateInvoiceTotalValue(invoiceLineItem);
	}

	public boolean isDeliveryRequired() {
		return deliveryRequired;
	}

	public void setDeliveryRequired(boolean deliveryRequired) {
		this.deliveryRequired = deliveryRequired;
	}

	public boolean isReportGenerated() {
		return reportGenerated;
	}

	public void setReportGenerated(boolean reportGenerated) {
		this.reportGenerated = reportGenerated;
	}

	public String getInvoiceId() {
		return invoiceNumber;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceNumber = invoiceId;
	}
	
	public String getPackedDate() {
		return packedDate;
	}

	public void setPackedDate(String packedDate) {
		this.packedDate = packedDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getOrganizationToBill() {
		return organizationToBill;
	}

	public void setOrganizationToBill(String organizationToBill) {
		this.organizationToBill = organizationToBill;
	}

	public String getOrganizationToBillAddress() {
		return organizationToBillAddress;
	}

	public void setOrganizationToBillAddress(String organizationToBillAddress) {
		this.organizationToBillAddress = organizationToBillAddress;
	}

	public String getOrganizationToBillContactPerson() {
		return organizationToBillContactPerson;
	}

	public void setOrganizationToBillContactPerson(String organizationToBillContactPerson) {
		this.organizationToBillContactPerson = organizationToBillContactPerson;
	}

	public String getOrganizationToBillContactNumber() {
		return organizationToBillContactNumber;
	}

	public void setOrganizationToBillContactNumber(String organizationToBillContactNumber) {
		this.organizationToBillContactNumber = organizationToBillContactNumber;
	}

	public String getReceivingOrganization() {
		return receivingOrganization;
	}

	public void setReceivingOrganization(String receivingOrganization) {
		this.receivingOrganization = receivingOrganization;
	}

	public String getReceivingOrganizationAddress() {
		return receivingOrganizationAddress;
	}

	public void setReceivingOrganizationAddress(String receivingOrganizationAddress) {
		this.receivingOrganizationAddress = receivingOrganizationAddress;
	}

	public String getReceivingOrganizationContactPerson() {
		return receivingOrganizationContactPerson;
	}

	public void setReceivingOrganizationContactPerson(String receivingOrganizationContactPerson) {
		this.receivingOrganizationContactPerson = receivingOrganizationContactPerson;
	}

	public String getReceivingOrganizationContactNumber() {
		return receivingOrganizationContactNumber;
	}

	public void setReceivingOrganizationContactNumber(String receivingOrganizationContactNumber) {
		this.receivingOrganizationContactNumber = receivingOrganizationContactNumber;
	}
	
	private List<InvoiceLineItem> generateInvoiceLineItemData(List<PackedFoodItem> packedItems) {
		List<InvoiceLineItem> lineItems = new ArrayList<InvoiceLineItem>();
		packedItems.forEach(item -> lineItems.add(new InvoiceLineItem(item.getCategory(), item.getClassification(), item.getDescription(), item.getQuantity())));
		return lineItems;
	}

	public List<InvoiceLineItem> getInvoiceLineItem() {
		return invoiceLineItem;
	}

	public void setInvoiceLineItem(List<InvoiceLineItem> invoiceLineItem) {
		this.invoiceLineItem = invoiceLineItem;
	}
	
	private void calculateInvoiceTotalValue(List<InvoiceLineItem> invoiceLineItems) {
		double combinedTotalValue = 0;
		for(InvoiceLineItem item : invoiceLineItems) {
			combinedTotalValue += item.getTotalValue();
		}
		this.setCombinedTotalValue(combinedTotalValue);
	}

	public double getCombinedTotalValue() {
		return combinedTotalValue;
	}

	public void setCombinedTotalValue(double combinedTotalValue) {
		this.combinedTotalValue = combinedTotalValue;
	}
	
}