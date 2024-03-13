package TFG_Ezyshop_Backend.Dto;

import java.util.Date;

import TFG_Ezyshop_Backend.Entity.Assessment;
import lombok.Data;

@Data
public class AdminAssessmentDto {

	private Long id;
	
	private String title;
	
	private String description;
	
	private Date date;
	
	private Boolean disabled;
	
	private AdminUserDto user;
	
	private AdminProductDto product;

	public AdminAssessmentDto(Assessment assessment) {
		this.id = assessment.getId();
		this.title = assessment.getTitle();
		this.description = assessment.getDescription();
		this.date = assessment.getDate();
		this.disabled = assessment.getDisabled();
		this.user = new AdminUserDto(assessment.getUserAssessment());
		this.product = new AdminProductDto(assessment.getProductAssessment());
	}
	
	
}
