package TFG_Ezyshop_Backend.dto;

import TFG_Ezyshop_Backend.entities.ImageProduct;
import lombok.Data;

@Data
public class AdminImageProductDto {
	
	private Long id;
	
	private String imageUrl;
	
	private String imageId;
	

	public AdminImageProductDto(ImageProduct imageProduct) {
		this.id = imageProduct.getId();
		this.imageUrl = imageProduct.getImageUrl();
		this.imageId = imageProduct.getImageId();
	}
	
	

}
