package foodbank.history.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import foodbank.history.entity.RequestHistory;

@Repository
public interface HistoryRepository extends MongoRepository<RequestHistory, String> {
	
	List<RequestHistory> findByUsername(final String username);

}
