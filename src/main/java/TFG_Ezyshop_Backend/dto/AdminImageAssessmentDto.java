package TFG_Ezyshop_Backend.dto;

import TFG_Ezyshop_Backend.entities.ImageAssessment;
import lombok.Data;

@Data
public class AdminImageAssessmentDto {
	
private Long id;
	
	private String imageUrl;
	
	private String imageId;
	

	public AdminImageAssessmentDto(ImageAssessment imageAssessment) {
		this.id = imageAssessment.getId();
		this.imageUrl = imageAssessment.getImageUrl();
		this.imageId = imageAssessment.getImageId();
	}

}
