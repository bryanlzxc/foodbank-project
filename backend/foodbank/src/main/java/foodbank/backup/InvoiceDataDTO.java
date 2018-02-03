package foodbank.backup;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import foodbank.reporting.entity.InvoiceLineItem;

public class InvoiceDataDTO {

	@JsonProperty("deliveryDate")
	private String deliveryDate;

	@JsonProperty("deliveryTime")
	private String deliveryTime;
	
	@JsonProperty("issuedBy")
	private String issuedBy;
	
	@JsonProperty("comments")
	private String comments;

	@JsonProperty("organizationToBill")
	private String organizationToBill;
	
	@JsonProperty("organizationToBillAddress")
	private String organizationToBillAddress;
	
	@JsonProperty("organizationToBillContactPerson")
	private String organizationToBillContactPerson;
	
	@JsonProperty("organizationToBillContactNumber")
	private String organizationToBillContactNumber;
	
	@JsonProperty("receivingOrganization")
	private String receivingOrganization;
	
	@JsonProperty("receivingOrganizationAddress")
	private String receivingOrganizationAddress;
	
	@JsonProperty("receivingOrganizationContactPerson")
	private String receivingOrganizationContactPerson;
	
	@JsonProperty("receivingOrganizationContactNumber")
	private String receivingOrganizationContactNumber;
	
	private List<InvoiceLineItem> invoiceLineItem;

	public InvoiceDataDTO(String deliveryDate, String deliveryTime, String issuedBy, String comments,
			String organizationToBill, String organizationToBillAddress, String organizationToBillContactPerson,
			String organizationToBillContactNumber, String receivingOrganization, String receivingOrganizationAddress,
			String receivingOrganizationContactPerson, String receivingOrganizationContactNumber,
			List<InvoiceLineItem> invoiceLineItem) {
		this.deliveryDate = deliveryDate;
		this.deliveryTime = deliveryTime;
		this.issuedBy = issuedBy;
		this.comments = comments;
		this.organizationToBill = organizationToBill;
		this.organizationToBillAddress = organizationToBillAddress;
		this.organizationToBillContactPerson = organizationToBillContactPerson;
		this.organizationToBillContactNumber = organizationToBillContactNumber;
		this.receivingOrganization = receivingOrganization;
		this.receivingOrganizationAddress = receivingOrganizationAddress;
		this.receivingOrganizationContactPerson = receivingOrganizationContactPerson;
		this.receivingOrganizationContactNumber = receivingOrganizationContactNumber;
		this.invoiceLineItem = invoiceLineItem;
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

	public List<InvoiceLineItem> getInvoiceLineItem() {
		return invoiceLineItem;
	}

	public void setInvoiceLineItem(List<InvoiceLineItem> invoiceLineItem) {
		this.invoiceLineItem = invoiceLineItem;
	}
	
}
