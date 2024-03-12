package TFG_Ezyshop_Backend.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TFG_Ezyshop_Backend.Entity.Assessment;

public interface AssessmentRepository extends JpaRepository<Assessment, Long>{

	public List<Assessment> getAll();
	public Optional<Assessment> getUser(Long assessmentId);
	public Assessment create(Assessment assessment); 
	public void delete(Long assessmentId);
}
