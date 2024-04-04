package TFG_Ezyshop_Backend.dto;

import TFG_Ezyshop_Backend.entities.ImageProduct;
import lombok.Data;

@Data
public class ImageProductDto {
	
	private String imageUrl;

	public ImageProductDto(ImageProduct imageProduct) {
		this.imageUrl = imageProduct.getImageUrl();
	}
	
	

}
