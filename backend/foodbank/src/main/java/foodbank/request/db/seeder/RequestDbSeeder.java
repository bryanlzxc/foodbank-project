/*package foodbank.request.db.seeder;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import foodbank.beneficiary.entity.Beneficiary;
import foodbank.beneficiary.repository.BeneficiaryRepository;
import foodbank.inventory.entity.FoodItem;
import foodbank.request.entity.Request;
import foodbank.request.repository.RequestRepository;

@Component

public class RequestDbSeeder implements CommandLineRunner {
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		Request testRequest1 = new Request(beneficiaryRepository.findByName("Ren Ci"), "Baking_Needs", "Other_Baking_Needs", new FoodItem("JellyCrystals-150g-Halal",1));
		Request testRequest2 = new Request(beneficiaryRepository.findByName("Ren Ci"), "Baby_Food", "Baby_Cereals", new FoodItem("BabyCereals-250g-Halal",1));
		Request testRequest3 = new Request(beneficiaryRepository.findByName("Jaren's Castle"), "Baby_Food", "Baby_Cereals", new FoodItem("BabyCereals-250g-Halal",1));
		Request testRequest4 = new Request(beneficiaryRepository.findByName("Shirong's Castle"), "Staples", "Instant_Meals", new FoodItem("Cereal-150g-Halal",1));
		//Dropping entire repository to ensure no duplicates entries crash the app
		requestRepository.deleteAll();
		//Bootstrapping the repository with the requests defined
		List<Request> requests = Arrays.asList(testRequest1, testRequest2, testRequest3, testRequest4);
		requestRepository.save(requests);
	}

}*/