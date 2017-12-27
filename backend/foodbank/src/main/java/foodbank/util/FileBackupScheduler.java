package foodbank.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListBucketsRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import foodbank.admin.entity.AdminSettings;
import foodbank.admin.repository.AdminRepository;
import foodbank.admin.service.impl.AdminServiceImpl;
import foodbank.allocation.entity.Allocation;
import foodbank.allocation.repository.AllocationRepository;
import foodbank.allocation.service.AllocationService;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.beneficiary.service.BeneficiaryService;
import foodbank.history.dto.RequestHistoryDTO;
import foodbank.history.entity.RequestHistory;
import foodbank.history.repository.HistoryRepository;
import foodbank.history.service.HistoryService;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;
import foodbank.inventory.service.FoodService;
import foodbank.packing.entity.PackingList;
import foodbank.packing.repository.PackingRepository;
import foodbank.packing.service.PackingService;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;
import foodbank.request.service.impl.RequestServiceImpl;
import foodbank.user.entity.User;
import foodbank.user.repository.UserRepository;
import foodbank.user.service.impl.UserServiceImpl;

@Component
public class FileBackupScheduler {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private AllocationRepository allocationRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private PackingRepository packingRepository;
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final String ADMIN_ID = "59f4a3316f9d43370468907b";
	private static final String BACKUP_BUCKET = "foodbank-backup-data";
	private AWSCredentials credentials;
	private AmazonS3 client;
	//private HashMap<String, String> csvFileHeaderMap;
	private List<String> csvFileList = new ArrayList<String>();
	
	private void init() {
		//credentials = new BasicAWSCredentials("AKIAI46ALB2WZXMFSD2Q", "4Rd0PDfxu0+cI+QBKLMufcI4hZ5iPxe+U9X1h80s");
		credentials = new BasicAWSCredentials("AKIAJTE7X7RCY7DWSJJQ", "GWj6GgrCOhm0uVdC4aM+bA7UZCEsT289hMrHmwDm");
		client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.US_EAST_2)
				.build();
		if(!client.doesBucketExistV2(BACKUP_BUCKET)) {
			client.createBucket(BACKUP_BUCKET);
		}
		//csvFileHeaderMap = new HashMap<String, String>();
		//csvFileHeaderMap.put("admin-data.csv", "Window Status,Window Start DateTime,Window End DateTime,Decay Rate,Multiplier Rate");
		//csvFileHeaderMap.put("allocation-data.csv", "Beneficiary,Allocated Items,Requested Amount,Inventory Amount");
		//csvFileHeaderMap.put("beneficiary-data.csv", "User,Username,Sector,Num Beneficiary,Address,Score,Membership Number,ACRA Registration Number,Member Type");
		//csvFileHeaderMap.put("historical-data.csv", "Beneficiary,Username,Request Creation Date,Category,Classification,Description,Requested Quantity,Allocated Quantity");
		//csvFileHeaderMap.put("inventory-data.csv", "Food Category,Item Classification,Item Description,Quantity");
		//csvFileHeaderMap.put("packing-data.csv","Allocation,Packed Items");
		//csvFileHeaderMap.put("request-data.csv","Beneficiary,Food Item,Inventory Quantity,Request Creation Date");
		//csvFileHeaderMap.put("user-data.csv","Username,Password,User Type,Name,Email");
		csvFileList.add("user-data.csv,User Id,Username,Password,Usertype,Name,Email");
		csvFileList.add("beneficiary-data.csv,Beneficiary Id,User Id,Sector,Number of Beneficiaries,Address,Postal Code,Score,Membership Number,"
				+ "ACRA Registration Number,Member Type");
		csvFileList.add("admin-data.csv,Settings Id,Window Status, Window Start DateTime, Window End DateTime, Decay Rate, Multiplier Rate");
		csvFileList.add("inventory-data.csv,Item Id,Food Category,Item Classification,Item Description,Quantity");
		csvFileList.add("request-data.csv,Request Id,Food Category,Item Classification,Item Description,Quantity,Request Creation Date,Beneficiary Id");
		csvFileList.add("allocation-data.csv,Allocation Id,Allocated Items,Beneficiary Id");
		csvFileList.add("historical-data.csv,Historical Request Id,Beneficiary Id,Request Creation Date,Food Category,Item Classification,"
				+ "Item Description,Requested Quantity,Allocated Quantity");
	}
	
	@Scheduled(fixedRate = 86400000)	// daily interval
	public void scheduledBackup() {
		init();
		List<Bucket> buckets = client.listBuckets();
		for(Bucket bucket : buckets) {
			String bucketName = bucket.getName();
			if(bucketName.equals("aws-website-visualanalyticstax-ed-k71jr")) { continue; }
			if(bucketName.equals(BACKUP_BUCKET)) {
				for(int i = 0; i < csvFileList.size(); i++) {
					String file = csvFileList.get(i);
					String[] fileArray = file.split(",");
					String fileName = fileArray[0];
					S3Object s3Object = client.getObject(BACKUP_BUCKET, fileName);
					backupFile(s3Object, BACKUP_BUCKET, fileName, i);
				}
			}
		}
	}
	
	private void backupFile(S3Object s3Object, String bucketName, String fileName, int fileIndex) {
		File file = new File(fileName);
		try (PrintStream out = new PrintStream(new FileOutputStream(file, false))) {
			String fileDetails = csvFileList.get(fileIndex);
			String[] fileDetailsArray = fileDetails.split(",");
			String header = "";
			for(int i = 1; i < fileDetailsArray.length; i++) {
				header += fileDetailsArray[i] + ",";
			}
			header = header.substring(0, header.length() - 2);
			out.println(header);
			//out.println(csvFileHeaderMap.get(CSVFileName));
			switch(fileName) {
				case("admin-data.csv"): 
					AdminSettings adminSettings = adminRepository.findOne(ADMIN_ID);
					out.println(adminSettings.toString());
					break;
				case("allocation-data.csv"): 
					List<Allocation> allocationList = allocationRepository.findAll();
					for (Allocation allocation : allocationList) { out.println(allocation.toString()); }
					break;
				case("beneficiary-data.csv"):
					List<Beneficiary> beneficiaryList = beneficiaryRepository.findAll();
					for (Beneficiary beneficiary : beneficiaryList) { out.println(beneficiary.toString()); }
					break;
				case("historical-data.csv"):
					List<RequestHistory> historyList = historyRepository.findAll();
					for (RequestHistory requestHistory : historyList) { out.println(requestHistory.toString()); }
					break;
				case("inventory-data.csv"):
					List<FoodItem> foodItemList = foodRepository.findAll();
					for (FoodItem foodItem : foodItemList) { out.println(foodItem.toString()); }
					break;
				case("packing-data.csv"):
					List<PackingList> packingLists = packingRepository.findAll();
					for (PackingList packingList : packingLists) { out.println(packingList.toString()); }
					break;
				case("request-data.csv"):
					List<Request> requestList = requestRepository.findAll();
					for (Request request : requestList) { out.println(request.toString()); }
					break;
				case("user-data.csv"):
					List<User> userList = userRepository.findAll();
					for (User user : userList) { out.println(user.toString()); }
					break;
			}
			client.putObject(new PutObjectRequest(bucketName, fileName, file));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File Not Found!");
		}
	}
		
}
