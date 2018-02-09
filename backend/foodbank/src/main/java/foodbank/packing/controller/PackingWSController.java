package foodbank.packing.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import foodbank.packing.dto.PackingListDTO;
import foodbank.packing.dto.PackingUpdateDTO;
import foodbank.packing.entity.PackedFoodItem;
import foodbank.packing.entity.PackingList;
import foodbank.packing.service.PackingService;

@Controller
public class PackingWSController {
	
	@Autowired
	private PackingService packingService;
	
	private static final Map<String, PackingListDTO> activeLists = Collections.synchronizedMap(new HashMap<String, PackingListDTO>());
	
	@MessageMapping("/server/packing/{listId}")
	@SendTo("/client/packing/{listId}")
	public PackingListDTO dynamicListUpdate(@DestinationVariable String listId, PackingUpdateDTO packingUpdate) throws Exception {
        PackingListDTO activeList = activeLists.get(listId);
		if(activeList == null || activeList.getPackedItems().isEmpty()) {
        	PackingList dbList = packingService.findById(listId);
        	activeList = new PackingListDTO();
        	activeList.setId(listId);
        	activeList.setBeneficiary(dbList.getBeneficiary().getUser().getUsername());
        	List<PackedFoodItem> dbPackedItems = dbList.getPackedItems();
        	List<Map<String, Object>> packedItems = new ArrayList<Map<String, Object>>();
        	for(PackedFoodItem dbPackedItem : dbPackedItems) {
        		Map<String, Object> dbPackedItemMap = new HashMap<String, Object>();
        		dbPackedItemMap.put("category", dbPackedItem.getCategory());
        		dbPackedItemMap.put("classification", dbPackedItem.getClassification());
        		dbPackedItemMap.put("description", dbPackedItem.getDescription());
        		dbPackedItemMap.put("packedQuantity", dbPackedItem.getQuantity());
        		dbPackedItemMap.put("itemPackingStatus", false);
        		packedItems.add(dbPackedItemMap);
        	}
        	activeList.setPackedItems(packedItems);
			activeLists.put(listId, activeList);
        	return activeList;
        }
		int itemIndex = packingUpdate.getItemIndex();
		if(itemIndex == -1) { return activeList; }
		int packedQuantity = packingUpdate.getPackedQuantity();
		boolean itemPackingStatus = packingUpdate.getItemPackingStatus();
		List<Map<String, Object>> packedItemsList = activeList.getPackedItems();
		Map<String, Object> mapToEdit = packedItemsList.get(itemIndex);
		mapToEdit.replace("packedQuantity", packedQuantity);
		mapToEdit.replace("itemPackingStatus", itemPackingStatus);
		activeList.setPackedItems(packedItemsList);
		return activeList;
    }
	
}
