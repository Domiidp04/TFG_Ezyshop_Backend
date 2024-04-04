package TFG_Ezyshop_Backend.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import TFG_Ezyshop_Backend.entities.Assessment;
import lombok.Data;

@Data
public class AdminAssessmentDto {

	private Long id;
	
	private String title;
	
	private String description;
	
	private Date date;
	
	private Boolean disabled;
	
	private AdminUserDto user;
	
	private List<AdminImageAssessmentDto>imagesAssessment;

	public AdminAssessmentDto(Assessment assessment) {
		this.id = assessment.getId();
		this.title = assessment.getTitle();
		this.description = assessment.getDescription();
		this.date = assessment.getDate();
		this.disabled = assessment.getDisabled();
		this.user = new AdminUserDto(assessment.getUserAssessment());
		this.imagesAssessment = assessment.getImages().stream()
		        .map(AdminImageAssessmentDto::new)
		        .collect(Collectors.toList());
	}
	
	
}
