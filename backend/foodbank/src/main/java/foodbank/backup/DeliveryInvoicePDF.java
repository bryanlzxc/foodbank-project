package foodbank.backup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import foodbank.reporting.entity.Invoice;

public class DeliveryInvoicePDF {
	
	/*
	private static final String DEVELOPMENT_BACKUP_BUCKET = "foodbank-backup";
	private static AmazonS3 client;
	
	public static void main(String[] args) {
		
		Document document = new Document(PageSize.A4);
		document.addAuthor("Food Bank Singapore");
		document.addTitle("Demo for Invoice Generation");
		try {
			PdfWriter.getInstance(document, new FileOutputStream("SampleInvoice.pdf"));
			document.open();
			PdfPTable headerTable = new PdfPTable(2);
			headerTable.setWidthPercentage(100);
			headerTable.setWidths(new int[] {1, 2});
			headerTable.addCell(createHeaderImageCell("logo.jpg"));
			headerTable.addCell(createHeaderTextCell(Invoice.COMPANY_NAME + 
					"\n" + Invoice.ADDRESS_LINE_1 + 
					"\n" + Invoice.ADDRESS_LINE_2 +
					"\n" + Invoice.ADDRESS_LINE_3 + 
					"\n" + Invoice.COMPANY_WEBSITE + 
					"\n" + Invoice.CO_REG_NO));
			document.add(headerTable);
			document.add(new Paragraph());
			document.add(new Chunk(new LineSeparator()));
			Paragraph spacingParagraph = new Paragraph(8);
			spacingParagraph.add("\n");
			BaseFont base = BaseFont.createFont("C:\\Windows\\fonts\\wingding.ttf", BaseFont.IDENTITY_H, false);
			Font font = new Font(base, 14f, Font.NORMAL);
			char checked ='\u00FE';
			char unchecked ='\u00A8';
			PdfPTable invoiceDetailTable = new PdfPTable(2);
			invoiceDetailTable.setWidthPercentage(100);
			invoiceDetailTable.setWidths(new int[] {4, 3});
			PdfPCell leftCell = new PdfPCell();
			leftCell.addElement(new Paragraph("INVOICE (Donation)", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 28)));
			Phrase deliveryStatus = new Phrase();
			boolean deliveryRequired = true;
			char deliveryChar = checked;
			char collectionChar = unchecked;
			if(!deliveryRequired) { 
				deliveryChar = unchecked; 
				collectionChar = checked; 
			}
			deliveryStatus.add(new Chunk(String.valueOf(deliveryChar), font));
			deliveryStatus.add(" Deliver ");
			deliveryStatus.add(new Chunk(String.valueOf(collectionChar), font));
			deliveryStatus.add(" Self-Collect");
			leftCell.addElement(deliveryStatus);
			leftCell.setUseAscender(true);
			leftCell.setHorizontalAlignment(Element.ALIGN_TOP);
			leftCell.setBorder(Rectangle.NO_BORDER);
			invoiceDetailTable.addCell(leftCell);
			PdfPTable innerTable = new PdfPTable(2);
			innerTable.setWidthPercentage(100);
			innerTable.setWidths(new int[] {2, 3});
			innerTable.addCell(createInvoiceDetailTextCell("Invoice No.", true));
			innerTable.addCell(createInvoiceDetailTextCell("FBDO-2017-12-6204", false));
			innerTable.addCell(createInvoiceDetailTextCell("Invoice Date", true));
			innerTable.addCell(createInvoiceDetailTextCell("03/01/2018", false));
			innerTable.addCell(createInvoiceDetailTextCell("Purchase Order No.", true));
			innerTable.addCell(createInvoiceDetailTextCell("", false));
			invoiceDetailTable.addCell(new PdfPCell(innerTable));
			document.add(invoiceDetailTable);
			document.add(spacingParagraph);
			PdfPTable deliveryInfoTable = new PdfPTable(4);
			deliveryInfoTable.setWidthPercentage(100);
			deliveryInfoTable.setWidths(new int[] {1, 1, 1, 1});
			deliveryInfoTable.addCell(createInvoiceDetailTextCell("Delivery Date", true));
			deliveryInfoTable.addCell(createInvoiceDetailTextCell("Delivery Time", true));
			deliveryInfoTable.addCell(createInvoiceDetailTextCell("Issued By", true));
			deliveryInfoTable.addCell(createInvoiceDetailTextCell("Comments", true));
			deliveryInfoTable.addCell(createInvoiceDetailTextCell("28/12/2017", false));
			deliveryInfoTable.addCell(createInvoiceDetailTextCell("19:37", false));
			deliveryInfoTable.addCell(createInvoiceDetailTextCell("FB Admin", false));
			deliveryInfoTable.addCell(createInvoiceDetailTextCell("15th Dec Packing List", false));
			document.add(deliveryInfoTable);
			document.add(spacingParagraph);
			PdfPTable organizationHeaderTable = new PdfPTable(3);
			organizationHeaderTable.setWidthPercentage(100);
			organizationHeaderTable.setWidths(new int[] {30, 1, 30});
			organizationHeaderTable.addCell(createInvoiceDetailTextCell("Bill To:", true));
			PdfPCell spacingCell = new PdfPCell();
			spacingCell.setBorder(Rectangle.NO_BORDER);
			organizationHeaderTable.addCell(spacingCell);
			organizationHeaderTable.addCell(createInvoiceDetailTextCell("Deliver To:", true));
			document.add(organizationHeaderTable);
			PdfPTable organizationDetailTable = new PdfPTable(5);
			organizationDetailTable.setWidthPercentage(100);
			organizationDetailTable.setWidths(new int[] {9, 22, 1, 9, 22});
			organizationDetailTable.addCell(createInvoiceDetailTextCell("Organization:", false));
			organizationDetailTable.addCell(createInvoiceDetailTextCell("Infant Jesus Homes and Children's Centre", false));
			organizationDetailTable.addCell(spacingCell);
			organizationDetailTable.addCell(createInvoiceDetailTextCell("Organization:", false));
			organizationDetailTable.addCell(createInvoiceDetailTextCell("Infant Jesus Homes and Children's Centre", false));
			organizationDetailTable.addCell(createInvoiceDetailTextCell("Address:", false));
			organizationDetailTable.addCell(createInvoiceDetailTextCell("503 Ang Mo Kio St 13 Singapore 569406", false));
			organizationDetailTable.addCell(spacingCell);
			organizationDetailTable.addCell(createInvoiceDetailTextCell("Address:", false));
			organizationDetailTable.addCell(createInvoiceDetailTextCell("503 Ang Mo Kio St 13 Singapore 569406", false));
			organizationDetailTable.addCell(createInvoiceDetailTextCell("Contact Person:", false));
			organizationDetailTable.addCell(createInvoiceDetailTextCell("Adeline", false));
			organizationDetailTable.addCell(spacingCell);
			organizationDetailTable.addCell(createInvoiceDetailTextCell("Contact Person:", false));
			organizationDetailTable.addCell(createInvoiceDetailTextCell("Adeline", false));
			organizationDetailTable.addCell(createInvoiceDetailTextCell("Contact No", false));
			organizationDetailTable.addCell(createInvoiceDetailTextCell("64594801", false));
			organizationDetailTable.addCell(spacingCell);
			organizationDetailTable.addCell(createInvoiceDetailTextCell("Contact No", false));
			organizationDetailTable.addCell(createInvoiceDetailTextCell("64594801", false));
			document.add(organizationDetailTable);
			document.add(spacingParagraph);
			PdfPTable itemDetailTable = new PdfPTable(8);
			itemDetailTable.setWidthPercentage(100);
			itemDetailTable.setWidths(new int[] {2, 10, 10, 10, 10, 3, 4, 4});
			itemDetailTable.addCell(createInvoiceDetailTextCell("No", true));
			itemDetailTable.addCell(createInvoiceDetailTextCell("Item No.", true));
			itemDetailTable.addCell(createInvoiceDetailTextCell("Item Category", true));
			itemDetailTable.addCell(createInvoiceDetailTextCell("Item Classification", true));
			itemDetailTable.addCell(createInvoiceDetailTextCell("Item Description", true));
			itemDetailTable.addCell(createInvoiceDetailTextCell("Qty", true));
			itemDetailTable.addCell(createInvoiceDetailTextCell("Value", true));
			itemDetailTable.addCell(createInvoiceDetailTextCell("Total", true));
			for(int i = 1; i <= 40; i++) {
				itemDetailTable.addCell(createInvoiceDetailTextCell(String.valueOf(i), false));
				itemDetailTable.addCell(createInvoiceDetailTextCell("DOI-2017-12-39697", false));
				itemDetailTable.addCell(createInvoiceDetailTextCell("Item Category " + i, false));
				itemDetailTable.addCell(createInvoiceDetailTextCell("Item Classification " + i, false));
				itemDetailTable.addCell(createInvoiceDetailTextCell("Item Description " + i, false));
				itemDetailTable.addCell(createInvoiceDetailTextCell("2", false));
				itemDetailTable.addCell(createInvoiceDetailTextCell("$5.00", false));
				itemDetailTable.addCell(createInvoiceDetailTextCell("$10.00", false));
			}
			document.add(itemDetailTable);
			PdfPTable itemRemarksTable = new PdfPTable(3);
			itemRemarksTable.setWidthPercentage(100);
			itemRemarksTable.setWidths(new int[] {46, 6, 6});
			itemRemarksTable.addCell(createInvoiceDetailTextCell("Remarks:", false));
			itemRemarksTable.addCell(createInvoiceDetailTextCell("TOTAL:", true));
			itemRemarksTable.addCell(createInvoiceDetailTextCell("$322.00", true));
			document.add(itemRemarksTable);
			document.add(spacingParagraph);
			Paragraph acknowledgementParagraph = new Paragraph();
			Chunk acknowledgementChunk = new Chunk("WE ACKNOWLEDGE THAT THE ITEMS ARE RECEIVED IN GOOD CONDITION.", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9));
			acknowledgementParagraph.add(acknowledgementChunk);
			acknowledgementParagraph.setAlignment(Element.ALIGN_CENTER);
			document.add(acknowledgementParagraph);
			Paragraph endingParagraphSpacing = new Paragraph(46);
			document.add(endingParagraphSpacing);
			document.add(new Chunk(new LineSeparator(1, 25, BaseColor.BLACK, Element.ALIGN_LEFT, 0)));
			Font signatureFont = FontFactory.getFont(FontFactory.HELVETICA, 9);
			document.add(new Paragraph("ACKNOWLEDGED BY", signatureFont));
			document.add(spacingParagraph);
			//document.add(new Paragraph("NAME:", signatureFont));
			document.add(new Paragraph("DATE:", signatureFont));
		} catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			document.close();
			// These code should be removed and replaced with FileManager.generatePDFPageCounts(filename);
			AWSCredentials credentials = new BasicAWSCredentials("AKIAI46ALB2WZXMFSD2Q", "4Rd0PDfxu0+cI+QBKLMufcI4hZ5iPxe+U9X1h80s");
			// AWSCredentials credentials = new BasicAWSCredentials("AKIAJTE7X7RCY7DWSJJQ", "GWj6GgrCOhm0uVdC4aM+bA7UZCEsT289hMrHmwDm");
			client = AmazonS3ClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(Regions.US_EAST_2)
					.build();
			client.putObject(new PutObjectRequest(DEVELOPMENT_BACKUP_BUCKET, "SampleInvoice.pdf", new File("SampleInvoice.pdf")));
			S3Object pdf = client.getObject(DEVELOPMENT_BACKUP_BUCKET, "SampleInvoice.pdf");
			S3ObjectInputStream stream = pdf.getObjectContent();
			try {
				PdfReader reader = new PdfReader(stream);
				PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("SampleInvoice.pdf"));
				int numPages = reader.getNumberOfPages();
				for(int i = 1; i <= numPages; i++) {
					String footer = "Page " + i + " of " + numPages;
					ColumnText.showTextAligned(stamper.getOverContent(i), Element.ALIGN_RIGHT, new Phrase(footer, FontFactory.getFont(FontFactory.HELVETICA, 9)), 
							reader.getPageSize(i).getRight(36), reader.getPageSize(i).getBottom(16), 0);
				}
				stamper.close();
				reader.close();
				client.putObject(new PutObjectRequest(DEVELOPMENT_BACKUP_BUCKET, "SampleInvoice.pdf", new File("SampleInvoice.pdf")));
			} catch (IOException | DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static PdfPCell createHeaderImageCell(String path) throws DocumentException, IOException {
		Image image = Image.getInstance(path);
		PdfPCell cell = new PdfPCell(image, true);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	
	public static PdfPCell createHeaderTextCell(String text) throws DocumentException, IOException {
		PdfPCell cell = new PdfPCell();
		String[] textData = text.split("\n");
		Paragraph paragraph = new Paragraph(12);
		for(int i = 0; i < textData.length; i++) {
			String lineData = textData[i];
			if(lineData.contains("www")) {
				Chunk chunk = new Chunk(lineData + "\n", FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.BLUE));
				chunk.setUnderline(0.5f, -2);
				paragraph.add(chunk);
			} else {
				paragraph.add(new Chunk(lineData + "\n", FontFactory.getFont(FontFactory.HELVETICA, 9)));
			}
		}
		paragraph.setAlignment(Element.ALIGN_RIGHT);
		paragraph.setSpacingAfter(0);
		paragraph.setSpacingBefore(0);
		cell.addElement(paragraph);
		cell.setVerticalAlignment(Element.ALIGN_TOP);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	
	public static PdfPCell createInvoiceDetailTextCell(String text, boolean background) throws DocumentException, IOException {
		PdfPCell cell = new PdfPCell();
		BaseColor color = new BaseColor(214, 227, 188);
		if(background) {
			cell.setBackgroundColor(color);
		}
		cell.addElement(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA, 9)));
		cell.setUseAscender(true);
		cell.setPadding(4);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		return cell;
	}
	*/
	
}
