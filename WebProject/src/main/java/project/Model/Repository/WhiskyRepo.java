package project.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.Model.Entity.Whisky;

@Repository
public interface WhiskyRepo extends JpaRepository<Whisky, Long>{

	List<Whisky> findByNameContainingIgnoreCase(String search);

	@Query(value = " SELECT w.* , AVG(o.smell + o.taste + o.finish) AS totalPoints " + 
			" FROM WHISKY w " + 
			" LEFT JOIN OPINION o ON ( w.id = o.whisky_Id )"+
			" GROUP BY w.id " + 
			" ORDER BY totalPoints DESC ", nativeQuery = true)
	List<Whisky> findAllByOrderByTopOverallRanking();
	
	
	List<Whisky> findAllByTypeIgnoreCase(String type);
	
	List<Whisky> findAllByOrderByNameAsc();
	
	@Query(value = "SELECT w.* , count(o.ID) AS numberOfOpinions " + 
			" FROM WHISKY w " +
			" LEFT JOIN OPINION o ON ( w.id = o.whisky_Id )"+
			" GROUP BY w.id " +  
			" ORDER BY numberOfOpinions  DESC ", nativeQuery = true)
	List<Whisky> findAllByOrderByMostReviewed();
}
