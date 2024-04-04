package TFG_Ezyshop_Backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import TFG_Ezyshop_Backend.dto.AdminAssessmentDto;
import TFG_Ezyshop_Backend.entities.Assessment;
import TFG_Ezyshop_Backend.services.AssessmentService;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {
	
	private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }
    
    @GetMapping
    public ResponseEntity<List<AdminAssessmentDto>> getAllAssessments() {
        List<AdminAssessmentDto> assessments = assessmentService.getAll();
        return ResponseEntity.ok(assessments);
    }
    
    @GetMapping("/{assessmentId}")
    public ResponseEntity<?> getAssessment(@PathVariable Long assessmentId) {
        Optional<?> optionalDto = assessmentService.getAssessment(assessmentId);

        if (!optionalDto.isPresent()) {
            return new ResponseEntity<>("Assessment not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalDto.get(), HttpStatus.OK);
    }
    
    @PostMapping("/{productId}")
    public ResponseEntity<Assessment> createAssessment(@PathVariable Long productId, @RequestBody Assessment assessment) {
        Assessment createdAssessment = assessmentService.createAssessment(productId, assessment);
        return new ResponseEntity<>(createdAssessment, HttpStatus.CREATED);
    }



}
