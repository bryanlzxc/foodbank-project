package foodbank.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import foodbank.admin.entity.AdminSettings;
import foodbank.admin.entity.AdminSettings.WindowStatus;
import foodbank.admin.repository.AdminRepository;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;
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
		
	private static final String INVENTORY_BUCKET = "foodbank-inventory-data";
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
		for (String csvFilename : csvFileList) {
			S3Object object = client.getObject(BACKUP_BUCKET, csvFilename);
			loadData(object, csvFilename);
		}
		
		
		//	if(!client.doesBucketExistV2(INVENTORY_BUCKET)) {
		//		client.createBucket(INVENTORY_BUCKET);
		//	}
		//	List<Bucket> buckets = client.listBuckets();
		//	for(Bucket bucket : buckets) {
		//		String bucketName = bucket.getName();
		//		if(bucketName.equals("aws-website-visualanalyticstax-ed-k71jr")) { continue; }
		//		switch(bucketName) {
		//			case(INVENTORY_BUCKET):
		//				S3Object object = client.getObject(INVENTORY_BUCKET, "inventory-data.csv");
		//				loadData(object, bucketName);
		//				break;
		//		}
		//	}
	}
	
	private void loadData(S3Object object, String csvFilename) {
		S3ObjectInputStream stream = object.getObjectContent();
		List<String> inputData = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.toList());
		List<String[]> inputDataArray = inputData.stream().skip(1).map(currentLine -> currentLine.split(",")).collect(Collectors.toList());
		switch (csvFilename) {
			case ("inventory-data.csv"):
				List<FoodItem> foodItems = new ArrayList<FoodItem>();
				inputDataArray.forEach(entry -> foodItems.add(new FoodItem(entry[0], entry[1], entry[2], Integer.parseInt(entry[3]))));
				foodRepository.deleteAll();
				foodRepository.insert(foodItems);
				break;
			case ("user-data.csv"):
				List<User> users = new ArrayList<User>();
				inputDataArray.forEach(entry -> users.add(new User(entry[0], entry[1], entry[2], entry[3], entry[4], entry[5]))); // skip entry[0] for id
				// drop Beneficiary repository
				beneficiaryRepository.deleteAll();
				userRepository.deleteAll();
				userRepository.insert(users);
				/* ------------- DEBUG STATEMENTS START ------------------ 
				for (String[] strAry : inputDataArray) { 
					for (int i = 0; i < strAry.length; i++) {
						System.out.print(strAry[i] + " ");
					}
					System.out.println(); 
				}
				System.out.println("======================NEXT======================");
				for (User user : users) { System.out.println("User: " + user); }
				// ------------- DEBUG STATEMENTS END ------------------ */
				break;
			case ("admin-data.csv"):
				List<AdminSettings> adminSettingsList = new ArrayList<AdminSettings>();
				SimpleDateFormat format = new SimpleDateFormat("dow mon dd hh:mm:ss zzz yyyy"); // date format written in CSV
				inputDataArray.forEach(entry -> adminSettingsList.add(
						new AdminSettings(entry[0], entry[1], format.parse(entry[2], new ParsePosition(0)), format.parse(entry[3], new ParsePosition(0)), 
								Double.parseDouble(entry[4]), Double.parseDouble(entry[5]) )));
				adminRepository.deleteAll();
				adminRepository.insert(adminSettingsList);
				break;
		}
	}
	
	//	private void loadData(S3Object object, String bucketName) {
	//		S3ObjectInputStream stream = object.getObjectContent();
	//		if(bucketName.equals(INVENTORY_BUCKET)) {
	//			List<String> inventoryData = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.toList());
	//			List<String[]> inventoryDataArray = inventoryData.stream().skip(1).map(currentLine -> currentLine.split(",")).collect(Collectors.toList());
	//			List<FoodItem> foodItems = new ArrayList<FoodItem>();
	//			inventoryDataArray.forEach(entry -> foodItems.add(new FoodItem(entry[0], entry[1], entry[2], Integer.parseInt(entry[3]))));
	//			foodRepository.deleteAll();
	//			foodRepository.insert(foodItems);
	//		}
	//	}
	
}
