package foodbank.request.db.seeder;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.inventory.entity.FoodItem;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;

@Component
public class RequestDbSeeder implements CommandLineRunner {
	
	private RequestRepository requestRepository;
	
	public RequestDbSeeder(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		Request testRequest1 = new Request(new Beneficiary("Ren Ci","Elderly",200,"52 Bukit Batok Street 31, 659443",1337), new FoodItem("JellyCrystals-150g-Halal",1));
		Request testRequest2 = new Request(new Beneficiary("Ren Ci","Elderly",200,"52 Bukit Batok Street 31, 659443",1337), new FoodItem("BabyCereals-250g-Halal",1));
		Request testRequest3 = new Request(new Beneficiary("K4K AMK","Kids",200,"52 Bukit Batok Street 31, 987654",901), new FoodItem("BabyCereals-250g-Halal",1));
		Request testRequest4 = new Request(new Beneficiary("Shirong Palace","Youth",404,"55 Bukit Batok Street 31, 123456",9002), new FoodItem("Cereal-150g-Halal",1));
		//Dropping entire repository to ensure no duplicates entries crash the app
		this.requestRepository.deleteAll();
		//Bootstrapping the repository with the requests defined
		List<Request> requests = Arrays.asList(testRequest1, testRequest2, testRequest3, testRequest4);
		this.requestRepository.save(requests);
	}

}
