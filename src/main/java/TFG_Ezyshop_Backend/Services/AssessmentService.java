package TFG_Ezyshop_Backend.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import TFG_Ezyshop_Backend.Entity.Assessment;
import TFG_Ezyshop_Backend.Repository.AssessmentRepository;

@Service
public class AssessmentService {

	private final AssessmentRepository assessmentRepository;

	public AssessmentService(AssessmentRepository assessmentRepository) {
		this.assessmentRepository = assessmentRepository;
	}
	
	
	public List<Assessment> getAll(){
		return assessmentRepository.getAll();
	}
	
	public Assessment save(Assessment user) {
		return assessmentRepository.create(user);
	}
	
	public Boolean delete(Long assessmentId) {
		return getAssessment(assessmentId).map(assessment -> {
			assessmentRepository.delete(assessmentId);
		return true;
		}).orElse(false);
	}
	
	public Optional<Assessment> getAssessment(Long assessmentId){
		return assessmentRepository.getAssessment(assessmentId);
	}
}
