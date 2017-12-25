package foodbank.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@Configuration
@EnableScheduling
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
	
	// for quick reference of CSV file and header data
	private final String[] ADMIN = {"admin-data-backup.csv", "Window Status,Window Start DateTime,Window End DateTime,Decay Rate,Multiplier Rate"};
	private final String[] ALLOCATION = {"allocation-data-backup.csv", "Beneficiary,Allocated Items,Requested Amount,Inventory Amount"};
	private final String[] BENEFICIARY= {"beneficiary-data-backup.csv", "User,Username,Sector,Num Beneficiary,Address,Score,Membership Number,ACRA Registration Number,Member Type"};
	private final String[] HISTORY = {"historical-data-backup.csv", "Beneficiary,Username,Request Creation Date,Category,Classification,Description,Requested Quantity,Allocated Quantity"};
	private final String[] FOOD = {"inventory-data-backup.csv", "Food Category,Item Classification,Item Description,Quantity"};
	private final String[] PACKING = {"packing-data-backup.csv","Allocation,Packed Items"};
	private final String[] REQUEST = {"request-data-backup.csv","Beneficiary,Food Item,Inventory Quantity,Request Creation Date"};
	private final String[] USER = {"user-data-backup.csv","Username,Password,User Type,Name,Email"};
	
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
		CSVFileHeaderMap.put("admin-data-backup.csv", "Window Status,Window Start DateTime,Window End DateTime,Decay Rate,Multiplier Rate");
		CSVFileHeaderMap.put("allocation-data-backup.csv", "Beneficiary,Allocated Items,Requested Amount,Inventory Amount");
		CSVFileHeaderMap.put("beneficiary-data-backup.csv", "User,Username,Sector,Num Beneficiary,Address,Score,Membership Number,ACRA Registration Number,Member Type");
		CSVFileHeaderMap.put("historical-data-backup.csv", "Beneficiary,Username,Request Creation Date,Category,Classification,Description,Requested Quantity,Allocated Quantity");
		CSVFileHeaderMap.put("inventory-data-backup.csv", "Food Category,Item Classification,Item Description,Quantity");
		CSVFileHeaderMap.put("packing-data-backup.csv","Allocation,Packed Items");
		CSVFileHeaderMap.put("request-data-backup.csv","Beneficiary,Food Item,Inventory Quantity,Request Creation Date");
		CSVFileHeaderMap.put("user-data-backup.csv","Username,Password,User Type,Name,Email");
	}
	
	@Scheduled(fixedRate = 600000)
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
		try 
			( BufferedWriter out = new BufferedWriter(new FileWriter(file, false)); )
		{
			out.write(CSVFileHeaderMap.get(CSVFileName));
			out.newLine();
			
			switch(CSVFileName) {

				case("admin-data-backup.csv"): 
					AdminSettings adminSettings = adminService.getAdminSettings();
					out.write(adminSettings.toString());
					out.newLine();
					break;
					
				case("allocation-data-backup.csv"): 
					List<Allocation> allocationList = allocationService.retrieveAllAllocations();
					allocationList.forEach(allocation -> { 
							try {
								out.write(allocation.toString());
								out.newLine();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});			
					break;
				
				case("beneficiary-data-backup.csv"):
					List<Beneficiary> beneficiaryList = beneficiaryService.getAllBeneficiaries();
					beneficiaryList.forEach(beneficiary -> { 
						try {
							out.write(beneficiary.toString());
							out.newLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						});			
					break;
				
				// TODO: Calls RequestHistoryDTO, while DB stores RequestHistory
				// Need to rework this
				case("historical-data-backup.csv"):
					List<RequestHistoryDTO> historyList = historyService.retrieveAllPastRequest();
					historyList.forEach(history -> {
						try {
							out.write(history.toString());
							out.newLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					break;
					
				case("inventory-data-backup.csv"):
					List<FoodItem> foodItemList = foodService.retrieveAllFoodItems();
					foodItemList.forEach(foodItem -> {
						try {
							out.write(foodItem.toString());
							out.newLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					break;
				
				case("packing-data-backup.csv"):
					List<PackingList> packingList = packingService.retrieveAllPackingLists();
					packingList.forEach(packing -> {
						try {
							out.write(packing.toString());
							out.newLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					break;
					
				case("request-data-backup.csv"):
					List<Request> requestList = requestService.getAllRequests();
					requestList.forEach(request -> {
						try {
							out.write(request.toString());
							out.newLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					break;
					
				case("user-data-backup.csv"):
					List<User> userList = userService.getAllUsers();
					userList.forEach(user -> {
						try {
							out.write(user.toString());
							out.newLine();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					break;
			}
			
			// Upload to Amazon S3
			client.putObject(new PutObjectRequest(
	                bucketName, CSVFileName, file));
		} catch (IOException e) {
			// handle IOException
			e.printStackTrace();
			System.out.println("File Not Found!");
		}
	}
		
}
