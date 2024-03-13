package TFG_Ezyshop_Backend.Dto;

import java.util.Date;

import TFG_Ezyshop_Backend.Entity.Assessment;
import lombok.Data;

@Data
public class AssessmentDto {
	
	private String title;
	
	private String description;
	
	private Date date;
	
	private UserDto user;
	
	private ProductDto product;
	
	public AssessmentDto(Assessment assessment) {
		this.title = assessment.getTitle();
		this.description = assessment.getDescription();
		this.date = assessment.getDate();
		this.user = new UserDto(assessment.getUserAssessment());
		this.product = new ProductDto(assessment.getProductAssessment());
	}

}
