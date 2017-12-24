package foodbank.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import foodbank.inventory.entity.FoodItem;

@Component
public class FileBackupScheduler implements CommandLineRunner {
	
	private static final String BACKUP_BUCKET = "foodbank-backup";
	
	@Override
	public void run(String... arg0) throws Exception {
		
		AWSCredentials credentials = new BasicAWSCredentials("AKIAI46ALB2WZXMFSD2Q", "4Rd0PDfxu0+cI+QBKLMufcI4hZ5iPxe+U9X1h80s");
		AmazonS3 client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(Regions.US_EAST_2)
				.build();
		
		// Create buckets if they do not exist
		if(!client.doesBucketExistV2(BACKUP_BUCKET)) {
			client.createBucket(BACKUP_BUCKET);
		}
		List<Bucket> buckets = client.listBuckets();
		for(Bucket bucket : buckets) {
			String bucketName = bucket.getName();
			if(bucketName.equals("aws-website-visualanalyticstax-ed-k71jr")) { continue; }
			switch(bucketName) {
				case(BACKUP_BUCKET):
					S3Object object = client.getObject(BACKUP_BUCKET, "inventory-data-backup.csv");
					writeData(object, bucketName);
					break;
			}
		}
		
	}
	
	private void writeData(S3Object object, String bucketName) {
		List<FoodItem> allFoodItems = new ArrayList<FoodItem>();
		allFoodItems.add(new FoodItem("a", "b", "c", 1));
		allFoodItems.add(new FoodItem("d", "e", "f", 2));
		System.out.println(allFoodItems);
		try 
			( BufferedWriter out = new BufferedWriter(new FileWriter(new File("inventory-data-backup.csv"), false)); ) 
		{
			// Write the header
			out.write("Food Category,Item Classification,Item Description,Quantity");
			out.newLine();
			for (FoodItem foodItem : allFoodItems) {
				String toWrite = foodItem.getCategory() + "," 
								+ foodItem.getClassification() + ","
								+ foodItem.getDescription() + ","
								+ foodItem.getQuantity();
				out.write(toWrite);
				out.newLine();
			}
		} catch (IOException e) {
			// handle IOException
			e.printStackTrace();
			System.out.println("File Not Found!");
		}
	}
}
