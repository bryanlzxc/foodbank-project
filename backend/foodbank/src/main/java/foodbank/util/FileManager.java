package foodbank.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import ch.qos.logback.core.net.server.Client;
import foodbank.MainApp;
import foodbank.admin.entity.AdminSettings;
import foodbank.admin.repository.AdminRepository;
import foodbank.allocation.entity.AllocatedFoodItems;
import foodbank.allocation.entity.Allocation;
import foodbank.allocation.repository.AllocationRepository;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.history.entity.RequestHistory;
import foodbank.history.repository.HistoryRepository;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;
import foodbank.packing.entity.PackedFoodItem;
import foodbank.packing.entity.PackingList;
import foodbank.packing.repository.PackingRepository;
import foodbank.reporting.repository.InvoiceRepository;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;
import foodbank.user.entity.User;
import foodbank.user.repository.UserRepository;

@Component
public class FileManager implements CommandLineRunner {
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private AllocationRepository allocationRepository;
	
	@Autowired
	private PackingRepository packingRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository; 
		
	//private static final String DEVELOPMENT_BACKUP_BUCKET = "foodbank-backup";
	//private static final String DEPLOYMENT_BACKUP_BUCKET = "foodbank-backup-data";
	private List<String> csvFileList = new ArrayList<String>();
	private static AmazonS3 client;
	
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		AWSCredentials credentials;
		if(MainApp.deploymentStatus) {
			// Deployment Server
			credentials = new BasicAWSCredentials("AKIAJTE7X7RCY7DWSJJQ", "GWj6GgrCOhm0uVdC4aM+bA7UZCEsT289hMrHmwDm");
		} else {
			// Development Server
			credentials = new BasicAWSCredentials("AKIAI46ALB2WZXMFSD2Q", "4Rd0PDfxu0+cI+QBKLMufcI4hZ5iPxe+U9X1h80s");
		}
		// AWSCredentials credentials = new BasicAWSCredentials("AKIAJTE7X7RCY7DWSJJQ", "GWj6GgrCOhm0uVdC4aM+bA7UZCEsT289hMrHmwDm");
		client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.US_EAST_2)
				.build();
		csvFileList.add("user-data.csv"); 
		csvFileList.add("beneficiary-data.csv");
		csvFileList.add("admin-data.csv"); 
		csvFileList.add("inventory-data.csv"); 
		csvFileList.add("request-data.csv");
		csvFileList.add("allocation-data.csv");
		csvFileList.add("packing-data.csv");
		csvFileList.add("historical-data.csv");
		//	String bucketName = client.listBuckets().get(0).getName();
		dropExistingData();
		for (String csvFilename : csvFileList) {
			System.out.println("Trying to read file: " + csvFilename);
			S3Object s3Object = client.getObject(MainApp.bucket, csvFilename);
			loadData(s3Object, csvFilename);
		}
	}
	
	private void dropExistingData() {
		foodRepository.deleteAll();
		userRepository.deleteAll();
		beneficiaryRepository.deleteAll();
		adminRepository.deleteAll();
		requestRepository.deleteAll();
		allocationRepository.deleteAll();
		packingRepository.deleteAll();
		historyRepository.deleteAll();
		invoiceRepository.deleteAll();
	}
	
	private void loadData(S3Object object, String csvFilename) {
		S3ObjectInputStream stream = object.getObjectContent();
		List<String> inputData = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.toList());
		List<String[]> inputDataArray = inputData.stream().skip(1).map(currentLine -> currentLine.split(",")).collect(Collectors.toList());
		switch(csvFilename) {
			case("inventory-data.csv"):
				loadInventoryData(inputDataArray);
				break;
			case("user-data.csv"):
				loadUserData(inputDataArray);
				break;
			case("admin-data.csv"):
				loadAdminData(inputDataArray);
				break;
			case("beneficiary-data.csv"):
				loadBeneficiaryData(inputDataArray);
				break;
			case("request-data.csv"):
				loadRequestData(inputDataArray);
				break;
			case("allocation-data.csv"):
				loadAllocationData(inputData);
				break;
			case("packing-data.csv"):
				loadPackingListData(inputData);
				break;
			case("historical-data-csv"):
				loadHistoricalData(inputDataArray);
				break;
		}
	}
	
	private void loadInventoryData(List<String[]> data) {
		List<FoodItem> foodItems = new ArrayList<FoodItem>();
		data.forEach(entry -> foodItems.add(new FoodItem(entry[0], entry[1], entry[2], entry[3], Integer.parseInt(entry[4]), Double.parseDouble(entry[5]))));
		foodRepository.insert(foodItems);
	}
	
	private void loadUserData(List<String[]> data) {
		List<User> users = new ArrayList<User>();
		data.forEach(entry -> users.add(new User(entry[0], entry[1], entry[2], entry[3], entry[4], entry[5]))); // skip entry[0] for id
		userRepository.insert(users);
	}
	
	private void loadAdminData(List<String[]> data) {
		List<AdminSettings> adminSettingsList = new ArrayList<AdminSettings>();
		Date windowStartDate = data.get(0)[2].equals("null") ? null : DateParser.convertToDBDate(data.get(0)[2]);
		Date windowEndDate = data.get(0)[3].equals("null") ? null : DateParser.convertToDBDate(data.get(0)[3]);
		data.forEach(entry -> adminSettingsList.add(
				new AdminSettings(entry[0], entry[1], windowStartDate, windowEndDate, 
						Double.parseDouble(entry[4]), Double.parseDouble(entry[5]))));
		adminRepository.insert(adminSettingsList);
	}
	
	private void loadBeneficiaryData(List<String[]> data) {
		List<Beneficiary> beneficiaries = new ArrayList<Beneficiary>();
		data.forEach(entry -> beneficiaries.add(new Beneficiary(entry[0], userRepository.findById(entry[1]), 
					Integer.parseInt(entry[2]), (entry[3] + ", " + entry[4]), Double.parseDouble(entry[5]), entry[6], 
					entry[7], entry[8], Boolean.valueOf(entry[9]))));
		beneficiaryRepository.insert(beneficiaries);
	}
	
	private void loadRequestData(List<String[]> data) {
		List<Request> requests = new ArrayList<Request>();
		data.forEach(entry -> requests.add(new Request(entry[0], beneficiaryRepository.findById(entry[6]), 
				new FoodItem(entry[1], entry[2], entry[3], Integer.parseInt(entry[4])), entry[5])));
		requestRepository.insert(requests);
	}
	
	private void loadAllocationData(List<String> dataList) {
		List<Allocation> allocations = new ArrayList<Allocation>();
		for(int i = 1; i < dataList.size(); i++) {
			String input = dataList.get(i);
			int allocationStartIndex = input.indexOf("[");
			int allocationEndIndex = input.indexOf("]");
			String id = input.substring(0, allocationStartIndex-1);
			String allocatedItemString = input.substring(allocationStartIndex+1, allocationEndIndex);
			String[] beneficiaryIdAndStatusArray = input.substring(allocationEndIndex+2).split(",");
			String beneficiaryId = beneficiaryIdAndStatusArray[0];
			Boolean approvalStatus = Boolean.valueOf(beneficiaryIdAndStatusArray[1]);
			ArrayList<AllocatedFoodItems> allocatedItems = new ArrayList<AllocatedFoodItems>();
			String[] allocatedItemsData = allocatedItemString.trim().split("\\{|\\}");
			List<String> data = Arrays.stream(allocatedItemsData).filter(entry -> !entry.isEmpty()).collect(Collectors.toList());
			for(int j = 0; j < data.size(); j++) {
				String[] dataArray = data.get(j).split("\\+");
				String category = dataArray[0];
				String classification = dataArray[1];
				String description = dataArray[2];
				Integer allocatedQuantity = Integer.parseInt(dataArray[3]);
				Integer requestedQuantity = Integer.parseInt(dataArray[4]);
				allocatedItems.add(new AllocatedFoodItems(category, classification, description, allocatedQuantity, requestedQuantity, 
						InventorySerializer.retrieveQuantityOfItem(category, classification, description)));
			}
			allocations.add(new Allocation(id, beneficiaryRepository.findById(beneficiaryId), allocatedItems, approvalStatus));
		}
		allocationRepository.insert(allocations);
	}
	
	private void loadPackingListData(List<String> dataList) {
		List<PackingList> packingLists = new ArrayList<PackingList>();
		for(int i = 1; i < dataList.size(); i++) {
			String input = dataList.get(i);
			int packedItemsStartIndex = input.indexOf("[");
			int packedItemsEndIndex = input.indexOf("]");
			String id = input.substring(0, packedItemsStartIndex-1);
			String packedItemString = input.substring(packedItemsStartIndex+1, packedItemsEndIndex);
			String[] beneficiaryIdAndStatusArray = input.substring(packedItemsEndIndex+2).split(",");
			String beneficiaryId = beneficiaryIdAndStatusArray[0];
			Boolean packingStatus = Boolean.valueOf(beneficiaryIdAndStatusArray[1]);
			ArrayList<PackedFoodItem> packedItems = new ArrayList<PackedFoodItem>();
			String[] packedItemsData = packedItemString.trim().split("\\{|\\}");
			List<String> data = Arrays.stream(packedItemsData).filter(entry -> !entry.isEmpty()).collect(Collectors.toList());
			for(int j = 0; j < data.size(); j++) {
				String[] dataArray = data.get(j).split("\\+");
				String category = dataArray[0];
				String classification = dataArray[1];
				String description = dataArray[2];
				Integer packedQuantity = Integer.parseInt(dataArray[3]);
				Integer allocatedQuantity = Integer.parseInt(dataArray[4]);
				packedItems.add(new PackedFoodItem(category, classification, description, packedQuantity, allocatedQuantity));
			}
			packingLists.add(new PackingList(id, beneficiaryRepository.findById(beneficiaryId), packedItems, packingStatus));
		}
		packingRepository.insert(packingLists);
	}
	
	private void loadHistoricalData(List<String[]> data) {
		List<RequestHistory> requestHistoryList = new ArrayList<RequestHistory>();
		data.forEach(entry -> requestHistoryList.add(new RequestHistory(entry[0], beneficiaryRepository.findById(entry[1]), DateParser.convertToDate(entry[2]), entry[3], entry[4],
				entry[5], Integer.parseInt(entry[6]), Integer.parseInt(entry[7]))));
		historyRepository.insert(requestHistoryList);
	}
	
	public static Map<String, String[]> generateBarcodeMap() {
		Map<String, String[]> barcodeMap = new HashMap<String, String[]>();
		S3Object s3Object = client.getObject(MainApp.bucket, "barcode-data.csv");
		S3ObjectInputStream stream = s3Object.getObjectContent();
		List<String> inputData = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.toList());
		List<String[]> inputDataArray = inputData.stream().skip(1).map(currentLine -> currentLine.split(",")).collect(Collectors.toList());
		for(String[] data : inputDataArray) {
			String[] itemData = {data[1], data[2], data[3]};
			barcodeMap.put(data[0], itemData);
		}
		return barcodeMap;
	}
	
	public static void generatePDFPageCounts(String filename) {
		client.putObject(new PutObjectRequest(MainApp.bucket, filename, new File(filename)));
		S3Object pdf = client.getObject(MainApp.bucket, filename);
		S3ObjectInputStream stream = pdf.getObjectContent();
		try {
			PdfReader reader = new PdfReader(stream);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(filename));
			int numPages = reader.getNumberOfPages();
			for(int i = 1; i <= numPages; i++) {
				String footer = "Page " + i + " of " + numPages;
				ColumnText.showTextAligned(stamper.getOverContent(i), Element.ALIGN_RIGHT, new Phrase(footer, FontFactory.getFont(FontFactory.HELVETICA, 9)), 
						reader.getPageSize(i).getRight(36), reader.getPageSize(i).getBottom(16), 0);
			}
			stamper.close();
			reader.close();
			client.putObject(new PutObjectRequest(MainApp.bucket, filename, new File(filename)));
		} catch (IOException | DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static URL retrieveInvoiceURL(String invoiceNumber) {
		return client.generatePresignedUrl(new GeneratePresignedUrlRequest(MainApp.bucket, invoiceNumber + ".pdf"));
	}
	
}
