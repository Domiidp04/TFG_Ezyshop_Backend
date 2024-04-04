package TFG_Ezyshop_Backend.dto;

import TFG_Ezyshop_Backend.entities.ImageAssessment;
import lombok.Data;

@Data
public class ImageAssessmentDto {
	
	private String imageUrl;

	public ImageAssessmentDto(ImageAssessment imageAssessment) {
		this.imageUrl = imageAssessment.getImageUrl();
	}

}
