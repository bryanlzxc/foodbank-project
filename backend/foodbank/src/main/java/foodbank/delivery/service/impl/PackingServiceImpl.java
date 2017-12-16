package foodbank.delivery.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodbank.allocation.entity.Allocation;
import foodbank.allocation.repository.AllocationRepository;
import foodbank.delivery.dto.PackingListDTO;
import foodbank.delivery.entity.PackingList;
import foodbank.delivery.repository.PackingRepository;
import foodbank.delivery.service.PackingService;
import foodbank.inventory.entity.FoodItem;

@Service
public class PackingServiceImpl implements PackingService {

	@Autowired
	private PackingRepository packingRepository;
	
	@Autowired
	private AllocationRepository allocationRepository;
	
	@Override
	public List<PackingList> retrieveAllPackingLists() {
		return packingRepository.findAll();
	}
	
	@Override
	public PackingList findByBeneficiary(String beneficiary) {
		// TODO Auto-generated method stub
		List<PackingList> packingLists = packingRepository.findAll();
		PackingList dbPackingList = null;
		for(PackingList packingList : packingLists) {
			if(packingList.getAllocation().getBeneficiary().getUser().getName().equals(beneficiary)) {
				dbPackingList = packingList;
			}
		}
		return dbPackingList;
	}

	@Override
	public void generatePackingList() {
		// TODO Auto-generated method stub
		List<Allocation> allocations = allocationRepository.findAll();
		List<PackingList> packingLists = new ArrayList<PackingList>();
		for(Allocation allocation : allocations) {
			List<FoodItem> foodItems = allocation.getAllocatedItems();
			List<FoodItem> packedItems = new ArrayList<FoodItem>();
			foodItems.forEach(foodItem -> packedItems.add(new FoodItem(foodItem.getDescription(), 0)));
			packingLists.add(new PackingList(allocation, packedItems));
		}
	}
	
}
