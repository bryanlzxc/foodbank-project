package foodbank.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

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
	private HistoryRepository historyRepository;
		
	//private static final String INVENTORY_BUCKET = "foodbank-inventory-data";
	private static final String BACKUP_BUCKET = "foodbank-backup-data";
	private List<String> csvFileList = new ArrayList<String>();
	
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		//	AWSCredentials credentials = new BasicAWSCredentials("AKIAI46ALB2WZXMFSD2Q", "4Rd0PDfxu0+cI+QBKLMufcI4hZ5iPxe+U9X1h80s");
		AWSCredentials credentials = new BasicAWSCredentials("AKIAJTE7X7RCY7DWSJJQ", "GWj6GgrCOhm0uVdC4aM+bA7UZCEsT289hMrHmwDm");
		AmazonS3 client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.US_EAST_2)
				.build();
		csvFileList.add("user-data.csv"); 
		csvFileList.add("beneficiary-data.csv");
		csvFileList.add("admin-data.csv"); 
		csvFileList.add("inventory-data.csv"); 
		csvFileList.add("request-data.csv");
		csvFileList.add("allocation-data.csv");
		csvFileList.add("historical-data.csv");
		//	String bucketName = client.listBuckets().get(0).getName();
		dropExistingData();
		for (String csvFilename : csvFileList) {
			System.out.println("Trying to read file: " + csvFilename);
			S3Object s3Object = client.getObject(BACKUP_BUCKET, csvFilename);
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
		historyRepository.deleteAll();
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
			case("historical-data-csv"):
				loadHistoricalData(inputDataArray);
		}
	}
	
	private void loadInventoryData(List<String[]> data) {
		List<FoodItem> foodItems = new ArrayList<FoodItem>();
		data.forEach(entry -> foodItems.add(new FoodItem(entry[0], entry[1], entry[2], entry[3], Integer.parseInt(entry[4]))));
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
					entry[2], Integer.parseInt(entry[3]), (entry[4] + ", " + entry[5]), Double.parseDouble(entry[6]), Long.parseLong(entry[7]), 
					Long.parseLong(entry[8]), entry[9])));
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
			String beneficiaryId = input.substring(allocationEndIndex+2);
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
			allocations.add(new Allocation(id, beneficiaryRepository.findById(beneficiaryId), allocatedItems));
		}
		allocationRepository.insert(allocations);
	}
	
	private void loadHistoricalData(List<String[]> data) {
		List<RequestHistory> requestHistoryList = new ArrayList<RequestHistory>();
		data.forEach(entry -> requestHistoryList.add(new RequestHistory(entry[0], beneficiaryRepository.findById(entry[1]), DateParser.convertToDate(entry[2]), entry[3], entry[4],
				entry[5], Integer.parseInt(entry[6]), Integer.parseInt(entry[7]))));
		historyRepository.insert(requestHistoryList);
	}
	
}
