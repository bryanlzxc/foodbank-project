package foodbank.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.inventory.entity.Barcode;
import foodbank.inventory.entity.FoodItem;

public interface BarcodeRepository extends JpaRepository<Barcode, Long> {

	FoodItem findByBarcode(String barcode);
	
}
