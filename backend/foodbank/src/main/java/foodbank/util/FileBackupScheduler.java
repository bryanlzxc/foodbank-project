package foodbank.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import foodbank.admin.entity.AdminSettings;
import foodbank.admin.service.impl.AdminServiceImpl;
import foodbank.allocation.entity.Allocation;
import foodbank.allocation.service.AllocationService;
import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.service.BeneficiaryService;
import foodbank.history.dto.RequestHistoryDTO;
import foodbank.history.service.HistoryService;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.service.FoodService;
import foodbank.packing.entity.PackingList;
import foodbank.packing.service.PackingService;
import foodbank.request.entity.Request;
import foodbank.request.service.impl.RequestServiceImpl;
import foodbank.user.entity.User;
import foodbank.user.service.impl.UserServiceImpl;

@Component
public class FileBackupScheduler {
	
	@Autowired
	private AdminServiceImpl adminService;
	@Autowired
	private AllocationService allocationService;
	@Autowired
	private BeneficiaryService beneficiaryService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private FoodService foodService;
	@Autowired
	private PackingService packingService;
	@Autowired
	private RequestServiceImpl requestService;
	@Autowired
	private UserServiceImpl userService;
	
	private static final String BACKUP_BUCKET = "foodbank-backup";
	private AWSCredentials credentials;
	private AmazonS3 client;
	private HashMap<String, String> CSVFileHeaderMap; 
	
	// Configuration of AWS S3
	private void init() {
		credentials = new BasicAWSCredentials("AKIAI46ALB2WZXMFSD2Q", "4Rd0PDfxu0+cI+QBKLMufcI4hZ5iPxe+U9X1h80s");
		client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.US_EAST_2)
				.build();
		// Create buckets if they do not exist
		if(!client.doesBucketExistV2(BACKUP_BUCKET)) {
			client.createBucket(BACKUP_BUCKET);
		}
		// Setup the HashMap
		CSVFileHeaderMap = new HashMap<>();
		CSVFileHeaderMap.put("admin-data.csv", "Window Status,Window Start DateTime,Window End DateTime,Decay Rate,Multiplier Rate");
		CSVFileHeaderMap.put("allocation-data.csv", "Beneficiary,Allocated Items,Requested Amount,Inventory Amount");
		CSVFileHeaderMap.put("beneficiary-data.csv", "User,Username,Sector,Num Beneficiary,Address,Score,Membership Number,ACRA Registration Number,Member Type");
		CSVFileHeaderMap.put("historical-data.csv", "Beneficiary,Username,Request Creation Date,Category,Classification,Description,Requested Quantity,Allocated Quantity");
		CSVFileHeaderMap.put("inventory-data.csv", "Food Category,Item Classification,Item Description,Quantity");
		CSVFileHeaderMap.put("packing-data.csv","Allocation,Packed Items");
		CSVFileHeaderMap.put("request-data.csv","Beneficiary,Food Item,Inventory Quantity,Request Creation Date");
		CSVFileHeaderMap.put("user-data.csv","Username,Password,User Type,Name,Email");
	}
	
	@Scheduled(fixedRate = 86,400,000)	// daily interval
	public void scheduledBackup() {
		init();
			
		List<Bucket> buckets = client.listBuckets();
		for(Bucket bucket : buckets) {
			String bucketName = bucket.getName();
			if(bucketName.equals("aws-website-visualanalyticstax-ed-k71jr")) { continue; }
			switch(bucketName) {
				case(BACKUP_BUCKET):
					Set<String> keySet = CSVFileHeaderMap.keySet();
					for (String CSVFileName : keySet) {
						S3Object s3Object = client.getObject(BACKUP_BUCKET, CSVFileName);
						writeData(s3Object, bucketName, CSVFileName);
					}
					break;
			}
		}
	}
	
	private void writeData(S3Object s3Object, String bucketName, String CSVFileName) {
		File file = new File(CSVFileName);
		try (PrintStream out = 
				new PrintStream(new FileOutputStream(file, false))) {
			out.println(CSVFileHeaderMap.get(CSVFileName));
			
			switch(CSVFileName) {

				case("admin-data.csv"): 
					AdminSettings adminSettings = adminService.getAdminSettings();
					out.println(adminSettings.toString());
					break;
					
				case("allocation-data.csv"): 
					List<Allocation> allocationList = allocationService.retrieveAllAllocations();
					for (Allocation a : allocationList) { out.println(a.toString()); }
					break;
				
				case("beneficiary-data.csv"):
					List<Beneficiary> beneficiaryList = beneficiaryService.getAllBeneficiaries();
					for (Beneficiary b : beneficiaryList) { out.println(b.toString()); }
					break;
				
				// TODO: Calls RequestHistoryDTO, while DB stores RequestHistory
				// Need to rework this
				case("historical-data.csv"):
					List<RequestHistoryDTO> historyList = historyService.retrieveAllPastRequest();
					for (RequestHistoryDTO rh : historyList) { out.println(rh.toString()); }
					break;
					
				case("inventory-data.csv"):
					List<FoodItem> foodItemList = foodService.retrieveAllFoodItems();
					for (FoodItem fi : foodItemList) { out.println(fi.toString()); }
					break;
				
				case("packing-data.csv"):
					List<PackingList> packingList = packingService.retrieveAllPackingLists();
					for (PackingList pl : packingList) { out.println(pl.toString()); }
					break;
					
				case("request-data.csv"):
					List<Request> requestList = requestService.getAllRequests();
					for (Request r : requestList) { out.println(r.toString()); }
					break;
					
				case("user-data.csv"):
					List<User> userList = userService.getAllUsers();
					for (User u : userList) { out.println(u.toString()); }
					break;
					
			}
			
			// Upload to Amazon S3
			client.putObject(new PutObjectRequest(bucketName, CSVFileName, file));

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File Not Found!");
		}
	}
		
}
