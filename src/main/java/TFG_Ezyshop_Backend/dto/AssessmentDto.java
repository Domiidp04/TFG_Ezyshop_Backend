package TFG_Ezyshop_Backend.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import TFG_Ezyshop_Backend.entities.Assessment;
import lombok.Data;

@Data
public class AssessmentDto {
	
	private String title;
	
	private String description;
	
	private Date date;
	
	private UserDto user;
	
	private List<ImageAssessmentDto>imageAssessment;
	
	public AssessmentDto(Assessment assessment) {
		this.title = assessment.getTitle();
		this.description = assessment.getDescription();
		this.date = assessment.getDate();
		this.user = new UserDto(assessment.getUserAssessment());
		this.imageAssessment = assessment.getImages().stream()
		        .map(ImageAssessmentDto::new)
		        .collect(Collectors.toList());
	}

}
