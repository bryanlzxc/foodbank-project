package foodbank.reporting.entity;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.packing.entity.PackedFoodItem;
import foodbank.util.DateParser;

/*
 * Remarks: This object should only be created ONCE when the PackingList has been fully packed and represents a Delivery Invoice
 * 			This object is not meant to be passed to client, use the InvoiceData instead!
 */
@Document(collection = "Invoices")
public class Invoice {
	
	// These values are hard-coded because they should not be changed unless the address have been changed
	public static final String COMPANY_NAME = "The Food Bank Singapore Ltd";
	public static final String ADDRESS_LINE_1 = "39 KEPPEL ROAD #01-02/04";
	public static final String ADDRESS_LINE_2 = "TANJONG PAGAR DISTRIPARK";
	public static final String ADDRESS_LINE_3 = "SINGAPORE 089065";
	public static final String COMPANY_WEBSITE = "www.foodbank.sg";
	public static final String CO_REG_NO = "Co Reg No: 201200654E";
	
	private static final String INVOICE_IDENTIFIER = "FBDO";
	private final String dateMonth = DateParser.displayMonthYearOnly(new Date());
	private final String generationDate = DateParser.displayDayMonthYearOnly(new Date());
	private static int invoiceNumber;
	private static final DecimalFormat decimalFormat = new DecimalFormat("0000");
	
	@Id
	private String id;
	
	private String deliveryDate = "";
	private String deliveryTime = "";
	private String issuedBy = "";
	private String comments = "";
	
	private boolean deliveryStatus;
	private boolean generationStatus;
	
	@DBRef
	private Beneficiary billingOrganization;
	
	@DBRef
	private Beneficiary receivingOrganization;
	
	/*
	private String organizationToBill;
	private String organizationToBillAddress;
	private String organizationToBillContactPerson;
	private String organizationToBillContactNumber;
	
	private String organizationForDelivery;
	private String organizationForDeliveryAddress;
	private String organizationForDeliveryContactPerson;
	private String organizationForDeliveryContactNumber;
	*/
	
	private List<PackedFoodItem> packedItems;

	public Invoice() {}
	
	public Invoice(Beneficiary billingOrganization, Beneficiary receivingOrganization, List<PackedFoodItem> packedItems) {
		invoiceNumber+=1;
		this.id = INVOICE_IDENTIFIER + "-" + dateMonth + "-" + decimalFormat.format(invoiceNumber);
		this.billingOrganization = billingOrganization;
		this.receivingOrganization = receivingOrganization;
		this.packedItems = packedItems;
		/*
		this.organizationToBill = billingOrganization.getUser().getName();
		this.organizationToBillAddress = billingOrganization.getAddress();
		this.organizationToBillContactPerson = billingOrganization.getContactPerson();
		this.organizationToBillContactNumber = billingOrganization.getContactNumber();
		this.organizationForDelivery = receivingOrganization.getUser().getName();
		this.organizationForDelivery = receivingOrganization.getAddress();
		this.organizationForDelivery = receivingOrganization.getContactPerson();
		this.organizationForDeliveryContactNumber = receivingOrganization.getContactNumber();
		*/
	}
	
	/*
	public Invoice(String organizationToBill, String organizationToBillAddress,
			String organizationToBillContactPerson, String organizationToBillContactNumber,
			String organizationForDelivery, String organizationForDeliveryAddress,
			String organizationForDeliveryContactPerson, String organizationForDeliveryContactNumber,
			List<PackedFoodItem> packedItems) {
		invoiceNumber+=1;
		this.id = INVOICE_IDENTIFIER + "-" + dateMonth + "-" + decimalFormat.format(invoiceNumber);
		this.organizationToBill = organizationToBill;
		this.organizationToBillAddress = organizationToBillAddress;
		this.organizationToBillContactPerson = organizationToBillContactPerson;
		this.organizationToBillContactNumber = organizationToBillContactNumber;
		this.organizationForDelivery = organizationForDelivery;
		this.organizationForDeliveryAddress = organizationForDeliveryAddress;
		this.organizationForDeliveryContactPerson = organizationForDeliveryContactPerson;
		this.organizationForDeliveryContactNumber = organizationForDeliveryContactNumber;
		this.packedItems = packedItems;
	}
	*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGenerationDate() {
		return generationDate;
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
	
	public boolean getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(boolean deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	
	public boolean getGenerationStatus() {
		return generationStatus;
	}

	public void setGenerationStatus(boolean generationStatus) {
		this.generationStatus = generationStatus;
	}
	
	/*
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

	public String getOrganizationForDelivery() {
		return organizationForDelivery;
	}

	public void setOrganizationForDelivery(String organizationForDelivery) {
		this.organizationForDelivery = organizationForDelivery;
	}

	public String getOrganizationForDeliveryAddress() {
		return organizationForDeliveryAddress;
	}

	public void setOrganizationForDeliveryAddress(String organizationForDeliveryAddress) {
		this.organizationForDeliveryAddress = organizationForDeliveryAddress;
	}

	public String getOrganizationForDeliveryContactPerson() {
		return organizationForDeliveryContactPerson;
	}

	public void setOrganizationForDeliveryContactPerson(String organizationForDeliveryContactPerson) {
		this.organizationForDeliveryContactPerson = organizationForDeliveryContactPerson;
	}

	public String getOrganizationForDeliveryContactNumber() {
		return organizationForDeliveryContactNumber;
	}

	public void setOrganizationForDeliveryContactNumber(String organizationForDeliveryContactNumber) {
		this.organizationForDeliveryContactNumber = organizationForDeliveryContactNumber;
	}
	*/

	public String getDateMonth() {
		return dateMonth;
	}

	public Beneficiary getBillingOrganization() {
		return billingOrganization;
	}

	public void setBillingOrganization(Beneficiary billingOrganization) {
		this.billingOrganization = billingOrganization;
	}

	public Beneficiary getReceivingOrganization() {
		return receivingOrganization;
	}

	public void setReceivingOrganization(Beneficiary receivingOrganization) {
		this.receivingOrganization = receivingOrganization;
	}
	
	public List<PackedFoodItem> getPackedItems() {
		return packedItems;
	}

	public void setPackedItems(List<PackedFoodItem> packedItems) {
		this.packedItems = packedItems;
	}
	
}
