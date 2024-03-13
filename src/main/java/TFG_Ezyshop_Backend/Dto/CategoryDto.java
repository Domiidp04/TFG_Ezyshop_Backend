package TFG_Ezyshop_Backend.Dto;

import TFG_Ezyshop_Backend.Entity.Category;
import lombok.Data;

@Data
public class CategoryDto {

	private String title;
	
	private String description;

	public CategoryDto(Category category) {
		this.title = category.getTitle();
		this.description = category.getDescription();
	}
	
	
}
