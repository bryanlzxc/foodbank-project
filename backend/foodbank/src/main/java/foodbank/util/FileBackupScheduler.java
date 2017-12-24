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
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import foodbank.inventory.entity.FoodItem;
import foodbank.inventory.service.FoodService;

@Component
public class FileBackupScheduler implements CommandLineRunner {
	
	// TODO: Allow this class to do file writing on schedule
	
	private static final String BACKUP_BUCKET = "foodbank-backup";
	
	@Autowired
	private FoodService foodService;
	
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
					writeData(client, object, bucketName);
					break;
			}
		}
		
	}
	
	private void writeData(AmazonS3 s3client, S3Object object, String bucketName) {
		List<FoodItem> allFoodItems = foodService.retrieveAllFoodItems();
		File f = new File("inventory-data-backup.csv");
		try 
			( BufferedWriter out = new BufferedWriter(new FileWriter(f, false)); ) 
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
			s3client.putObject(new PutObjectRequest(
	                 bucketName, "inventory-data-backup.csv", f));
		} catch (IOException e) {
			// handle IOException
			e.printStackTrace();
			System.out.println("File Not Found!");
		}
	}
}
