package foodbank.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.repository.FoodRepository;

@Component
public class FileManager implements CommandLineRunner {
	
	@Autowired
	private FoodRepository foodRepository;
		
	private static final String INVENTORY_BUCKET = "foodbank-inventory-data";
	
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		AWSCredentials credentials = new BasicAWSCredentials("AKIAI46ALB2WZXMFSD2Q", "4Rd0PDfxu0+cI+QBKLMufcI4hZ5iPxe+U9X1h80s");
		AmazonS3 client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.US_EAST_2)
				.build();
		if(!client.doesBucketExistV2(INVENTORY_BUCKET)) {
			client.createBucket(INVENTORY_BUCKET);
		}
		List<Bucket> buckets = client.listBuckets();
		for(Bucket bucket : buckets) {
			String bucketName = bucket.getName();
			if(bucketName.equals("aws-website-visualanalyticstax-ed-k71jr")) { continue; }
			switch(bucketName) {
				case(INVENTORY_BUCKET):
					S3Object object = client.getObject(INVENTORY_BUCKET, "inventory-data.csv");
					loadData(object, bucketName);
					break;
			}
		}
	}
	
	private void loadData(S3Object object, String bucketName) {
		S3ObjectInputStream stream = object.getObjectContent();
		if(bucketName.equals(INVENTORY_BUCKET)) {
			List<String> inventoryData = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.toList());
			List<String[]> inventoryDataArray = inventoryData.stream().skip(1).map(currentLine -> currentLine.split(",")).collect(Collectors.toList());
			List<FoodItem> foodItems = new ArrayList<FoodItem>();
			inventoryDataArray.forEach(entry -> foodItems.add(new FoodItem(entry[0], entry[1], entry[2], Integer.parseInt(entry[3]))));
			foodRepository.deleteAll();
			foodRepository.insert(foodItems);
		}
	}
	
}
