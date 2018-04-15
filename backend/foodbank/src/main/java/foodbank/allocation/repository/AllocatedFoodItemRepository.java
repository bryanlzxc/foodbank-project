package foodbank.allocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import foodbank.allocation.entity.AllocatedFoodItem;

/**
 * 
 * @author Bryan Lau <bryan.lau.2015@sis.smu.edu.sg>
 * @version 1.0
 *
 */
public interface AllocatedFoodItemRepository extends JpaRepository<AllocatedFoodItem, Long> {

}
